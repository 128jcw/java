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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InsertAccountFrame extends JFrame {
    
    public interface OnInsertAccountListener {
        void onAccountInserted();
    }
    
    private JPanel contentPane;
    private JTextField textAccountNum;
    private JTextField textBankName;
    private JTextField textMemberId;
    
    private OnInsertAccountListener listener;
    private String memberId;
    private BankDaoImpl dao;
    private List<Bank> bankList;
    private List<String> accountNumList=new ArrayList<>();
    private Component parent;

    /**
     * Launch the application.
     */
    public static void newInsertAccountFrame(Component parent, String memberId, OnInsertAccountListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InsertAccountFrame frame = new InsertAccountFrame(parent, memberId, listener);
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
    
    public InsertAccountFrame(Component parent, String memberId, OnInsertAccountListener listener) {
        this.memberId=memberId;
        this.listener=listener;
        this.parent=parent;
        this.dao=BankDaoImpl.getInstance();
        bankList=dao.selectAll();
        for (Bank b : bankList) {
            accountNumList.add(b.getAccountNum());
        }
        initialize();
        initializeTable();
    }
    
    private void initializeTable() {
        textMemberId.setText(this.memberId);
    }
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX(); // ?????? ?????? x ??????
        int y = parent.getY();
        setBounds(x, y, 449, 392);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblAccountNum = new JLabel("????????????");
        lblAccountNum.setFont(new Font("??????", Font.PLAIN, 20));
        lblAccountNum.setBounds(12, 10, 123, 55);
        contentPane.add(lblAccountNum);
        
        textAccountNum = new JTextField();
        textAccountNum.setFont(new Font("??????", Font.PLAIN, 17));
        textAccountNum.setBounds(167, 10, 255, 55);
        contentPane.add(textAccountNum);
        textAccountNum.setColumns(10);
        
        JLabel lblBankName = new JLabel("?????????");
        lblBankName.setFont(new Font("??????", Font.PLAIN, 20));
        lblBankName.setBounds(12, 84, 123, 55);
        contentPane.add(lblBankName);
        
        JLabel lblMemberId = new JLabel("???????????????");
        lblMemberId.setFont(new Font("??????", Font.PLAIN, 20));
        lblMemberId.setBounds(12, 159, 123, 55);
        contentPane.add(lblMemberId);
        
        textBankName = new JTextField();
        textBankName.setFont(new Font("??????", Font.PLAIN, 17));
        textBankName.setColumns(10);
        textBankName.setBounds(167, 84, 255, 55);
        contentPane.add(textBankName);
        
        textMemberId = new JTextField();
        textMemberId.setFont(new Font("??????", Font.PLAIN, 17));
        textMemberId.setEditable(false);
        textMemberId.setColumns(10);
        textMemberId.setBounds(167, 159, 255, 55);
        contentPane.add(textMemberId);
        
        JButton btnIsertAccount = new JButton("?????? ??????");
        btnIsertAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertAccount();
            }
        });
        btnIsertAccount.setFont(new Font("??????", Font.PLAIN, 20));
        btnIsertAccount.setBounds(107, 256, 218, 55);
        contentPane.add(btnIsertAccount);
    }
    
    private void insertAccount() {
        String accountNum=textAccountNum.getText();
        String bankName=textBankName.getText();
        Integer balance=0;
        
        if (accountNum.equals("") || bankName.equals("")) {
            JOptionPane.showMessageDialog(
                    InsertAccountFrame.this, 
                    "????????????, ???????????? ?????? ??????????????????!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        } else if (accountNumList.contains(accountNum)) {
            JOptionPane.showMessageDialog(
                    InsertAccountFrame.this, 
                    "?????? ???????????? ???????????????!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        } else {
            Bank bank=new Bank(accountNum, bankName, memberId, balance);
            
            int confirm=JOptionPane.showConfirmDialog(
                    this, 
                    "????????? ?????? ??????????", 
                    "?????? ??????", 
                    JOptionPane.YES_NO_OPTION);
            if (confirm==JOptionPane.YES_OPTION) {
                int result=dao.insertAccount(bank);
                if (result==1) {
                    JOptionPane.showMessageDialog(InsertAccountFrame.this, "?????? ?????? ??????");
                    dispose();
                    listener.onAccountInserted();
                }
            }
        }
    }

}
