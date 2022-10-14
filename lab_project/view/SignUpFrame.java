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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignUpFrame extends JFrame {

    private JPanel contentPane;
    private JTextField textId;
    private JTextField textPassword;
    private JTextField textConfirmPassword;
    private JTextField textName;
    
    private BankDaoImpl dao;
    private List<Member> memberList;
    private List<String> memberIdList=new ArrayList<>();

    /**
     * Launch the application.
     */
    public static void newSignUpFrame() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SignUpFrame frame = new SignUpFrame();
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
    
    public SignUpFrame() {
        this.dao=BankDaoImpl.getInstance();
        this.memberList=dao.selectAllMember();
        for (Member m : memberList) {
            this.memberIdList.add(m.getMemberId());
        }
        initialize();
    }
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 452, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblId = new JLabel("아이디");
        lblId.setFont(new Font("굴림", Font.PLAIN, 20));
        lblId.setBounds(12, 10, 126, 52);
        contentPane.add(lblId);
        
        textId = new JTextField();
        textId.setFont(new Font("굴림", Font.PLAIN, 17));
        textId.setBounds(167, 10, 257, 52);
        contentPane.add(textId);
        textId.setColumns(10);
        
        JLabel lblPassword = new JLabel("비밀번호");
        lblPassword.setFont(new Font("굴림", Font.PLAIN, 20));
        lblPassword.setBounds(12, 117, 126, 52);
        contentPane.add(lblPassword);
        
        JLabel lblConfirmPassword = new JLabel("비밀번호 확인");
        lblConfirmPassword.setFont(new Font("굴림", Font.PLAIN, 20));
        lblConfirmPassword.setBounds(12, 187, 137, 52);
        contentPane.add(lblConfirmPassword);
        
        textPassword = new JTextField();
        textPassword.setFont(new Font("굴림", Font.PLAIN, 17));
        textPassword.setColumns(10);
        textPassword.setBounds(167, 117, 257, 52);
        contentPane.add(textPassword);
        
        textConfirmPassword = new JTextField();
        textConfirmPassword.setFont(new Font("굴림", Font.PLAIN, 17));
        textConfirmPassword.setColumns(10);
        textConfirmPassword.setBounds(167, 187, 257, 52);
        contentPane.add(textConfirmPassword);
        
        JLabel lblName = new JLabel("이름");
        lblName.setFont(new Font("굴림", Font.PLAIN, 20));
        lblName.setBounds(12, 291, 126, 52);
        contentPane.add(lblName);
        
        textName = new JTextField();
        textName.setFont(new Font("굴림", Font.PLAIN, 17));
        textName.setColumns(10);
        textName.setBounds(167, 291, 257, 52);
        contentPane.add(textName);
        
        JButton btnSignUp = new JButton("회원가입");
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUp();
            }
        });
        btnSignUp.setFont(new Font("굴림", Font.PLAIN, 20));
        btnSignUp.setBounds(131, 377, 182, 61);
        contentPane.add(btnSignUp);
    }
    
    private void signUp() {
        String id=textId.getText();
        String password1=textPassword.getText();
        String password2=textConfirmPassword.getText();
        String name=textName.getText();
        
        if (id.equals("")||password1.equals("")||password2.equals("")||name.equals("")) {
            JOptionPane.showMessageDialog(
                    SignUpFrame.this, 
                    "모든 항목을 작성해 주세요!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        } else if (memberIdList.contains(id)) {
            JOptionPane.showMessageDialog(
                    SignUpFrame.this, 
                    "이미 존재하는 아이디입니다!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        } else if (!(password1.equals(password2))) {
            JOptionPane.showMessageDialog(
                    SignUpFrame.this, 
                    "비밀번호를 확인해주세요", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        } else {
            Member m=new Member(id, password1, name);
            int confirm=JOptionPane.showConfirmDialog(
                    this, 
                    "회원가입을 하시겠습니까?", 
                    "회원가입 확인", 
                    JOptionPane.YES_NO_OPTION);
            if (confirm==JOptionPane.YES_OPTION) {
                int result=dao.insertMember(m);
                if (result==1) {
                    JOptionPane.showMessageDialog(SignUpFrame.this, "회원가입 완료");
                    dispose();
                }
            }
        }
    }
}
