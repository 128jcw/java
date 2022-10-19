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
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import static edu.java.project.model.Bank.Entity.*;

public class SelectAllBankFrame extends JFrame {
    private static final String[] COLUMN_NAMES= {
            COL_ACCOUNTNUM, COL_BANKNAME, COL_BANKMEMBERID, COL_BALANCE
    };

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private Component parent;
    
    private BankDaoImpl dao;

    /**
     * Launch the application.
     */
    public static void newSelectAllBankFrame(Component parent) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SelectAllBankFrame frame = new SelectAllBankFrame(parent);
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
    public SelectAllBankFrame(Component parent) {
        this.parent=parent;
        this.dao=BankDaoImpl.getInstance();
        initialize();
        initializeTable();
    }
    
    public void initializeTable() {
        model=new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(model);
        
        List<Bank> list=dao.selectAllBankByMemberId();
        
        for (Bank b : list) {
            Object[] row= {
                    b.getAccountNum(), b.getBankName(), b.getMemberId(), b.getBalance()
            };
            model.addRow(row);
        }
    }
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX(); // 부모 창의 x 좌표
        int y = parent.getY();
        setBounds(x, y, 559, 579);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblBankInformation = new JLabel("회원 계좌 정보");
        lblBankInformation.setFont(new Font("굴림", Font.PLAIN, 20));
        lblBankInformation.setHorizontalAlignment(SwingConstants.CENTER);
        lblBankInformation.setBounds(139, 10, 267, 60);
        contentPane.add(lblBankInformation);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(28, 117, 488, 379);
        contentPane.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
    }

}
