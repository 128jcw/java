package edu.java.project.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.java.project.controller.BankDaoImpl;
import edu.java.project.model.Member;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangePasswordFrame extends JFrame {

    private JPanel contentPane;
    private JTextField textPassword;
    private JTextField textNewPassword;
    private JTextField textNewPasswordConfirm;
    
    private String memberId;
    private BankDaoImpl dao;

    /**
     * Launch the application.
     */
    public static void newChangePasswordFrame(String memberId) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ChangePasswordFrame frame = new ChangePasswordFrame(memberId);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    
    public ChangePasswordFrame(String memberId) {
        this.memberId=memberId;
        this.dao=BankDaoImpl.getInstance();
        initialize();
        initializeTable();
    }
    
    private void initializeTable() {
        Member m=dao.selectMemberById(memberId);
        textPassword.setText(m.getPassword());
    }
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 451, 444);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblPassword = new JLabel("기존 비밀번호");
        lblPassword.setFont(new Font("굴림", Font.PLAIN, 17));
        lblPassword.setBounds(12, 10, 138, 61);
        contentPane.add(lblPassword);
        
        textPassword = new JTextField();
        textPassword.setEditable(false);
        textPassword.setFont(new Font("굴림", Font.PLAIN, 17));
        textPassword.setBounds(162, 10, 261, 61);
        contentPane.add(textPassword);
        textPassword.setColumns(10);
        
        JLabel lblNewPassword = new JLabel("새 비밀번호");
        lblNewPassword.setFont(new Font("굴림", Font.PLAIN, 17));
        lblNewPassword.setBounds(12, 93, 138, 61);
        contentPane.add(lblNewPassword);
        
        textNewPassword = new JTextField();
        textNewPassword.setFont(new Font("굴림", Font.PLAIN, 17));
        textNewPassword.setColumns(10);
        textNewPassword.setBounds(162, 93, 261, 61);
        contentPane.add(textNewPassword);
        
        JLabel lblNewPasswordConfirm = new JLabel("새 비밀번호 확인");
        lblNewPasswordConfirm.setFont(new Font("굴림", Font.PLAIN, 17));
        lblNewPasswordConfirm.setBounds(12, 170, 138, 61);
        contentPane.add(lblNewPasswordConfirm);
        
        textNewPasswordConfirm = new JTextField();
        textNewPasswordConfirm.setFont(new Font("굴림", Font.PLAIN, 17));
        textNewPasswordConfirm.setColumns(10);
        textNewPasswordConfirm.setBounds(162, 170, 261, 61);
        contentPane.add(textNewPasswordConfirm);
        
        JButton btnChangePassword = new JButton("비밀번호 변경");
        btnChangePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });
        btnChangePassword.setFont(new Font("굴림", Font.PLAIN, 20));
        btnChangePassword.setBounds(116, 279, 199, 61);
        contentPane.add(btnChangePassword);
    }
    
    private void changePassword() {
        String password=textNewPassword.getText();
        String passwordConfirm=textNewPasswordConfirm.getText();
        
        if (password.equals("") || passwordConfirm.equals("")) {
            JOptionPane.showMessageDialog(
                    ChangePasswordFrame.this, 
                    "빈칸을 모두 채워주세요", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        } else if (!(password.equals(passwordConfirm))) {
            JOptionPane.showMessageDialog(
                    ChangePasswordFrame.this, 
                    "변경할 비밀번호를 확인하세요", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        } else {
            Member m=new Member(memberId, password, null);
            int confirm=JOptionPane.showConfirmDialog(
                    ChangePasswordFrame.this, 
                    "비밀번호를 변경 하시겠습니까?", 
                    "비밀번호 변경 확인", 
                    JOptionPane.YES_NO_OPTION);
            if (confirm==JOptionPane.YES_OPTION) {
                int result=dao.updatePassword(m);
                if (result==1) {
                    JOptionPane.showMessageDialog(
                            ChangePasswordFrame.this, 
                            "비밀번호가 변경되었습니다.");
                    dispose();
                }
            }
        }
    }
}
