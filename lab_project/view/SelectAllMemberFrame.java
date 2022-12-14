package edu.java.project.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import edu.java.project.controller.BankDaoImpl;
import edu.java.project.model.Member;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import static edu.java.project.model.Member.Entity.*;

public class SelectAllMemberFrame extends JFrame {
    private static final String[] COLUMN_NAMES= {
            COL_MEMBERID, COL_MEMBERPASSWORD, COL_MEMBERNAME
    };

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private Component parent;
    
    private BankDaoImpl dao;

    /**
     * Launch the application.
     */
    public static void newSelectAllMemberFrame(Component parent) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SelectAllMemberFrame frame = new SelectAllMemberFrame(parent);
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
    public SelectAllMemberFrame(Component parent) {
        this.parent=parent;
        this.dao=BankDaoImpl.getInstance();
        initialize();
        initializeTable();
    }
    
    private void initializeTable() {
        model=new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(model);
        
        List<Member> list=dao.selectAllMember();
        
        for (Member m : list) {
            Object[] row= {
                    m.getMemberId(), m.getPassword(), m.getName()
            };
            model.addRow(row);
        }
    }
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX(); // ?????? ?????? x ??????
        int y = parent.getY();
        setBounds(x, y, 451, 533);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblMemberInformation = new JLabel("?????? ??????");
        lblMemberInformation.setHorizontalAlignment(SwingConstants.CENTER);
        lblMemberInformation.setFont(new Font("??????", Font.PLAIN, 20));
        lblMemberInformation.setBounds(119, 10, 219, 63);
        contentPane.add(lblMemberInformation);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 84, 411, 334);
        contentPane.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
    }
}
