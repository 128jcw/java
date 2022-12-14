package edu.java.project.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import edu.java.project.controller.BankDaoImpl;
import edu.java.project.model.Bank;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import static edu.java.project.model.Bank.Entity.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BankRemitFrame extends JFrame {
    private static final String[] COLUMN_NAMES= {
            COL_ACCOUNTNUM, COL_BANKNAME, COL_BANKMEMBERID, COL_BALANCE
    };
    
    public interface OnBankRemitListener {
        void onBankRemitted();
    }

    private JPanel contentPane;
    private JTextField textAmount;
    private JTable table;
    private DefaultTableModel model;
    
    private OnBankRemitListener listener;
    private String accountNum;
    private Integer balance;
    private Component parent;
    
    private BankDaoImpl dao;

    /**
     * Launch the application.
     */
    public static void newBankRemitFrame(Component parent, String accountNum, Integer balance, OnBankRemitListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BankRemitFrame frame = new BankRemitFrame(parent, accountNum, balance, listener);
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
    
    public BankRemitFrame(Component parent, String accountNum, Integer balance, OnBankRemitListener listener) {
        this.accountNum=accountNum;
        this.balance=balance;
        this.listener=listener;
        this.parent=parent;
        this.dao=BankDaoImpl.getInstance();
        initialize();
        initializeTable();
    }
    
    private void initializeTable() {
        model=new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(model);
        
        List<Bank> list=dao.selectWithout(accountNum);
        
        for (Bank b : list) {
            Object[] row= {
                    b.getAccountNum(), b.getBankName(), b.getMemberId(), b.getBalance()
            };
            model.addRow(row);
        }
    }
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX(); // ?????? ?????? x ??????
        int y = parent.getY();
        setBounds(x, y, 491, 444);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblAmount = new JLabel("??????");
        lblAmount.setFont(new Font("??????", Font.PLAIN, 20));
        lblAmount.setBounds(12, 10, 140, 50);
        contentPane.add(lblAmount);
        
        textAmount = new JTextField();
        textAmount.setFont(new Font("??????", Font.PLAIN, 15));
        textAmount.setBounds(208, 10, 233, 50);
        contentPane.add(textAmount);
        textAmount.setColumns(10);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 88, 451, 224);
        contentPane.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        
        JButton btnRemit = new JButton("??????");
        btnRemit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remit();
            }
        });
        btnRemit.setFont(new Font("??????", Font.PLAIN, 20));
        btnRemit.setBounds(142, 338, 203, 50);
        contentPane.add(btnRemit);
    }
    
    private void remit() {
        int amount;
        int row=table.getSelectedRow();
        if (row==-1) {
            JOptionPane.showMessageDialog(BankRemitFrame.this,
                    "????????? ????????? ?????? ???????????????.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } 
        String remitAccount=(String) model.getValueAt(row, 0);
        String remitBankName=(String) model.getValueAt(row, 1);
        String remitMemberId=(String) model.getValueAt(row, 2);
        Integer remitAccountBalance=(Integer) model.getValueAt(row, 3);
        
        Bank bank=dao.selectOne(accountNum);
        
        Bank remitBank=new Bank(remitAccount, remitBankName, remitMemberId, remitAccountBalance);
        
        try {
            amount=Integer.parseInt(textAmount.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(BankRemitFrame.this,
                    "????????? ????????? ????????? ???????????????",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (amount>bank.getBalance()) {
            JOptionPane.showMessageDialog(
                    BankRemitFrame.this, 
                    "????????? ???????????????!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return ;
        }
        
        if (amount<=0) {
            JOptionPane.showMessageDialog(
                    BankRemitFrame.this, 
                    "????????? ????????? ????????? ???????????????!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return ;
        }
        
        int confirm=JOptionPane.showConfirmDialog(
                this, 
                "?????? ??????????", 
                "?????? ??????", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirm==JOptionPane.YES_OPTION) {
            int result1=dao.update(bank, (-1)*amount);
            int result2=dao.update(remitBank, amount);
            
            if ((result1==1)&&(result2==1)) {
                JOptionPane.showMessageDialog(BankRemitFrame.this, 
                        "?????? ??????");
                dispose();
                listener.onBankRemitted();
            }
        }
        
    }

}
