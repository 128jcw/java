package edu.java.project.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.java.project.controller.BankDaoImpl;
import edu.java.project.model.Bank;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BankWithdrawFrame extends JFrame {
    public interface OnBankWithdrawListener {
        void onBankWithdrawed();
    }

    private JPanel contentPane;
    private JTextField textBalance;
    private JTextField textWithdraw;
    
    private OnBankWithdrawListener listener;
    private String accountNum;
    private Integer balance;
    private Component parent;
    
    private BankDaoImpl dao;

    /**
     * Launch the application.
     */
    public static void newBankWithdrawFrame(Component parent, String accountNum, Integer balance, OnBankWithdrawListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BankWithdrawFrame frame = new BankWithdrawFrame(parent, accountNum, balance, listener);
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
    
    public BankWithdrawFrame(Component parent, String accountNum, Integer balance, OnBankWithdrawListener listener) {
        this.accountNum=accountNum;
        this.balance=balance;
        this.listener=listener;
        this.parent=parent;
        this.dao=BankDaoImpl.getInstance();
        initialize();
        initializeText();
    }
    
    private void initializeText() {
        Bank bank=dao.selectOne(accountNum);
        textBalance.setText(bank.getBalance().toString());
    }
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX(); // 부모 창의 x 좌표
        int y = parent.getY();
        setBounds(x, y, 449, 331);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblBalance = new JLabel("잔액");
        lblBalance.setFont(new Font("굴림", Font.PLAIN, 20));
        lblBalance.setBounds(12, 10, 117, 56);
        contentPane.add(lblBalance);
        
        JLabel lblWithdraw = new JLabel("출금액");
        lblWithdraw.setFont(new Font("굴림", Font.PLAIN, 20));
        lblWithdraw.setBounds(12, 97, 117, 56);
        contentPane.add(lblWithdraw);
        
        textBalance = new JTextField();
        textBalance.setEditable(false);
        textBalance.setFont(new Font("굴림", Font.PLAIN, 17));
        textBalance.setBounds(173, 10, 220, 56);
        contentPane.add(textBalance);
        textBalance.setColumns(10);
        
        textWithdraw = new JTextField();
        textWithdraw.setFont(new Font("굴림", Font.PLAIN, 17));
        textWithdraw.setColumns(10);
        textWithdraw.setBounds(173, 97, 220, 56);
        contentPane.add(textWithdraw);
        
        JButton btnWithdraw = new JButton("출금하기");
        btnWithdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });
        btnWithdraw.setFont(new Font("굴림", Font.PLAIN, 20));
        btnWithdraw.setBounds(118, 180, 192, 56);
        contentPane.add(btnWithdraw);
    }
    
    private void withdraw() {
        int amount;
        
        Bank bank=dao.selectOne(accountNum);
        
        try {
            amount=Integer.parseInt(textWithdraw.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(BankWithdrawFrame.this,
                    "출금할 금액을 숫자로 입력하세요",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (amount>bank.getBalance()) {
            JOptionPane.showMessageDialog(
                    BankWithdrawFrame.this, 
                    "잔액이 부족합니다!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return ;
        }
        
        if (amount<=0) {
            JOptionPane.showMessageDialog(
                    BankWithdrawFrame.this, 
                    "출금할 금액을 양수로 입력하세요!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return ;
        }
        
        int confirm=JOptionPane.showConfirmDialog(
                this, 
                amount + "원을 출금 하시겠습니까?", 
                "출금 확인", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirm==JOptionPane.YES_OPTION) {
            int result=dao.update(bank, (-1)*amount);
            
            if (result==1) {
                JOptionPane.showMessageDialog(BankWithdrawFrame.this, 
                        "출금 성공");
                dispose();
                listener.onBankWithdrawed();
            }
        }
    }

}
