package edu.java.project.view;

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

public class BankDepositFrame extends JFrame {
    public interface OnBankDepositListener {
        void onBankDeposited();
    }

    private JPanel contentPane;
    private JTextField textDeposit;
    private JTextField textAccount;
    
    private BankDaoImpl dao;
    private String accountNum;
    private OnBankDepositListener listener;

    /**
     * Launch the application.
     */
    public static void newBankDepositFrame(String accountNum, OnBankDepositListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BankDepositFrame frame = new BankDepositFrame(accountNum, listener);
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
    public BankDepositFrame(String accountNum, OnBankDepositListener listener) {
        this.accountNum=accountNum;
        this.listener=listener;
        dao=BankDaoImpl.getInstance();
        initialize();
        initializeText();
    }
    
    private void initializeText() {
        textAccount.setText(accountNum);
    }
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 453, 372);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblDeposit = new JLabel("금액");
        lblDeposit.setFont(new Font("굴림", Font.PLAIN, 20));
        lblDeposit.setBounds(12, 109, 127, 63);
        contentPane.add(lblDeposit);
        
        textDeposit = new JTextField();
        textDeposit.setFont(new Font("굴림", Font.PLAIN, 17));
        textDeposit.setBounds(164, 110, 261, 63);
        contentPane.add(textDeposit);
        textDeposit.setColumns(10);
        
        JButton btnDeposit = new JButton("입금");
        btnDeposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });
        btnDeposit.setFont(new Font("굴림", Font.PLAIN, 20));
        btnDeposit.setBounds(123, 206, 191, 63);
        contentPane.add(btnDeposit);
        
        JLabel lblAccount = new JLabel("계좌");
        lblAccount.setFont(new Font("굴림", Font.PLAIN, 20));
        lblAccount.setBounds(12, 10, 127, 63);
        contentPane.add(lblAccount);
        
        textAccount = new JTextField();
        textAccount.setEditable(false);
        textAccount.setFont(new Font("굴림", Font.PLAIN, 17));
        textAccount.setColumns(10);
        textAccount.setBounds(164, 10, 261, 63);
        contentPane.add(textAccount);
    }
    
    private void deposit() {
        int amount;
        
        Bank bank=dao.selectOne(accountNum);
        
        try {
            amount=Integer.parseInt(textDeposit.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(BankDepositFrame.this,
                    "출금할 금액을 숫자로 입력하세요",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (amount<=0) {
            JOptionPane.showMessageDialog(BankDepositFrame.this,
                    "입금할 금액을 양수로 입력하세요",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm=JOptionPane.showConfirmDialog(
                this, 
                amount + "원을 입금 하시겠습니까?", 
                "입금 확인", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirm==JOptionPane.YES_OPTION) {
            int result=dao.update(bank, amount);
            
            if (result==1) {
                JOptionPane.showMessageDialog(BankDepositFrame.this, 
                        "입금 성공");
                dispose();
                listener.onBankDeposited();
            }
        }
        
    }
}
