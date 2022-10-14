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

public class BankLoginFrame extends JFrame {
    
    public interface OnBankLoginListener {
        void onBankLogin(String id);
    }
    
    private OnBankLoginListener listener;
    private BankDaoImpl dao;

    private JPanel contentPane;
    private JTextField textId;
    private JTextField textPassword;

    /**
     * Launch the application.
     */
    public static void newBankLoginFrame(OnBankLoginListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BankLoginFrame frame = new BankLoginFrame(listener);
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
    public BankLoginFrame(OnBankLoginListener listener) {
        this.listener=listener;
        this.dao=BankDaoImpl.getInstance();
        
        initialize();
    }
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 451, 376);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblId = new JLabel("아이디");
        lblId.setFont(new Font("굴림", Font.PLAIN, 20));
        lblId.setBounds(12, 10, 117, 49);
        contentPane.add(lblId);
        
        JLabel lblPassword = new JLabel("비밀번호");
        lblPassword.setFont(new Font("굴림", Font.PLAIN, 20));
        lblPassword.setBounds(12, 94, 117, 49);
        contentPane.add(lblPassword);
        
        textId = new JTextField();
        textId.setFont(new Font("굴림", Font.PLAIN, 15));
        textId.setBounds(146, 10, 277, 49);
        contentPane.add(textId);
        textId.setColumns(10);
        
        textPassword = new JTextField();
        textPassword.setFont(new Font("굴림", Font.PLAIN, 15));
        textPassword.setColumns(10);
        textPassword.setBounds(146, 94, 277, 49);
        contentPane.add(textPassword);
        
        JButton btnLogin = new JButton("로그인");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginBank();
            }
        });
        btnLogin.setFont(new Font("굴림", Font.PLAIN, 20));
        btnLogin.setBounds(120, 231, 175, 54);
        contentPane.add(btnLogin);
    }
    
    private void loginBank() {
        String id=textId.getText();
        String password=textPassword.getText();
        
        if (id.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(this, 
                    "아이디, 비밀번호는 반드시 입력되어야 합니다!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return ;
        }
        
        if (dao.selectMember(new Member(id, password, null))==null) {
            JOptionPane.showMessageDialog(this, 
                    "아이디, 비밀번호가 잘못되었습니다.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return ;
        } else {
            JOptionPane.showMessageDialog(this, id + "님, 안녕하세요!");
            dispose();
            listener.onBankLogin(id);
        }
    }
}
