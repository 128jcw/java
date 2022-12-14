package edu.java.project.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import edu.java.project.controller.BankDaoImpl;
import edu.java.project.model.Bank;
import edu.java.project.view.BankDepositFrame.OnBankDepositListener;
import edu.java.project.view.BankLoginFrame.OnBankLoginListener;
import edu.java.project.view.BankRemitFrame.OnBankRemitListener;
import edu.java.project.view.BankWithdrawFrame.OnBankWithdrawListener;
import edu.java.project.view.InsertAccountFrame.OnInsertAccountListener;

import static edu.java.project.model.Bank.Entity.*;
import static edu.java.project.model.Member.Entity.*;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BankMain implements OnBankLoginListener, OnBankRemitListener, OnBankWithdrawListener, 
OnBankDepositListener, OnInsertAccountListener {
    private static final String[] COLUMN_NAMES= {
            COL_ACCOUNTNUM, COL_BANKNAME, COL_BANKMEMBERID, COL_BALANCE
    };
    
    private String loginId="";

    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    
    private BankDaoImpl dao;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BankMain window = new BankMain();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public BankMain() {
        initialize();
        dao=BankDaoImpl.getInstance();
        initializeTable(loginId);
    }
    
    private void initializeTable(String id) {
        model=new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(model);
        
        loginId=id;
        
        List<Bank> list=dao.selectMemberAccount(loginId);
        
        for (Bank b : list) {
            Object[] row= {
                    b.getAccountNum(), b.getBankName(), b.getMemberId(), b.getBalance()
            };
            model.addRow(row);
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 560, 659);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JButton btnLogin = new JButton("?????????/????????????");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkLogin()) {
                    logout();
                } else {
                    BankLoginFrame.newBankLoginFrame(frame, BankMain.this);
                }
            }
        });
        btnLogin.setFont(new Font("??????", Font.PLAIN, 12));
        btnLogin.setBounds(12, 10, 145, 60);
        frame.getContentPane().add(btnLogin);
        
        JButton btnRemit = new JButton("??????");
        btnRemit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkLogin()) {
                    showRemitFrame();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "?????? ????????? ?????????.",
                            "Warning", 
                            JOptionPane.WARNING_MESSAGE); 
                }
            }
        });
        btnRemit.setFont(new Font("??????", Font.PLAIN, 20));
        btnRemit.setBounds(201, 10, 145, 60);
        frame.getContentPane().add(btnRemit);
        
        JButton btnWithdraw = new JButton("??????");
        btnWithdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkLogin()) {
                    showWithdrawFrame();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "?????? ????????? ?????????.",
                            "Warning", 
                            JOptionPane.WARNING_MESSAGE); 
                }
            }
        });
        btnWithdraw.setFont(new Font("??????", Font.PLAIN, 20));
        btnWithdraw.setBounds(387, 10, 145, 60);
        frame.getContentPane().add(btnWithdraw);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 227, 520, 383);
        frame.getContentPane().add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        
        JButton btnDeposit = new JButton("??????");
        btnDeposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkLogin()) {
                    showDepositFrame();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "?????? ????????? ?????????.",
                            "Warning", 
                            JOptionPane.WARNING_MESSAGE); 
                }
            }
        });
        btnDeposit.setFont(new Font("??????", Font.PLAIN, 20));
        btnDeposit.setBounds(12, 80, 125, 60);
        frame.getContentPane().add(btnDeposit);
        
        JButton btnSignUp = new JButton("????????????");
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkLogin()) {
                    JOptionPane.showMessageDialog(frame,
                            "????????? ?????? ???????????????.",
                            "Warning", 
                            JOptionPane.WARNING_MESSAGE); 
                } else {
                    showSignUpFrame();
                }
            }
        });
        btnSignUp.setFont(new Font("??????", Font.PLAIN, 18));
        btnSignUp.setBounds(149, 80, 113, 60);
        frame.getContentPane().add(btnSignUp);
        
        JButton btnInsertAccount = new JButton("?????? ??????");
        btnInsertAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkLogin()) {
                    showInsertAccountFrame();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "?????? ????????? ?????????.",
                            "Warning", 
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnInsertAccount.setFont(new Font("??????", Font.PLAIN, 18));
        btnInsertAccount.setBounds(274, 80, 119, 60);
        frame.getContentPane().add(btnInsertAccount);
        
        JButton btnSelectAllMember = new JButton("?????? ????????????");
        btnSelectAllMember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isManager()) {
                    SelectAllMemberFrame.newSelectAllMemberFrame(frame);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "manager???????????? ????????? ?????????!",
                            "Warning", 
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnSelectAllMember.setFont(new Font("??????", Font.PLAIN, 14));
        btnSelectAllMember.setBounds(12, 150, 125, 60);
        frame.getContentPane().add(btnSelectAllMember);
        
        JButton btnSelectAllAccount = new JButton("?????? ????????????");
        btnSelectAllAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isManager()) {
                    SelectAllBankFrame.newSelectAllBankFrame(frame);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "manager???????????? ????????? ?????????!",
                            "Warning", 
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnSelectAllAccount.setFont(new Font("??????", Font.PLAIN, 14));
        btnSelectAllAccount.setBounds(149, 150, 125, 60);
        frame.getContentPane().add(btnSelectAllAccount);
        
        JButton btnDeleteMember = new JButton("?????? ??????");
        btnDeleteMember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(checkLogin())) {
                    JOptionPane.showMessageDialog(frame,
                            "?????? ????????? ?????????.",
                            "Warning", 
                            JOptionPane.WARNING_MESSAGE);
                } else if (isManager()) {
                    JOptionPane.showMessageDialog(frame,
                            "manager????????? ????????? ??? ????????????!",
                            "Warning", 
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    showMemberDeleteFrame();
                    
                }
            }
        });
        btnDeleteMember.setFont(new Font("??????", Font.PLAIN, 14));
        btnDeleteMember.setBounds(287, 150, 100, 60);
        frame.getContentPane().add(btnDeleteMember);
        
        JButton btnDeleteAccount = new JButton("?????? ??????");
        btnDeleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(checkLogin())) {
                    JOptionPane.showMessageDialog(frame,
                            "?????? ????????? ?????????.",
                            "Warning", 
                            JOptionPane.WARNING_MESSAGE);
                } else if (isManager()) {
                    JOptionPane.showMessageDialog(frame,
                            "manager????????? ????????? ??? ????????????!",
                            "Warning", 
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    showBankDeleteFrame();
                    
                }
            }
        });
        btnDeleteAccount.setFont(new Font("??????", Font.PLAIN, 14));
        btnDeleteAccount.setBounds(407, 150, 106, 60);
        frame.getContentPane().add(btnDeleteAccount);
        
        JButton btnChangePassword = new JButton("???????????? ??????");
        btnChangePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(checkLogin())) {
                    JOptionPane.showMessageDialog(frame,
                            "?????? ????????? ?????????.",
                            "Warning", 
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    ChangePasswordFrame.newChangePasswordFrame(frame, loginId);
                }
                
            }
        });
        btnChangePassword.setFont(new Font("??????", Font.PLAIN, 15));
        btnChangePassword.setBounds(405, 80, 127, 60);
        frame.getContentPane().add(btnChangePassword);
    }
    
    private void showBankDeleteFrame() {
        int row=table.getSelectedRow();
        if (row==-1) {
            JOptionPane.showMessageDialog(frame,
                    "????????? ?????? ???????????????.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String accountNum=(String) model.getValueAt(row, 0);
        Integer balance=(Integer) model.getValueAt(row, 3);
        
        if (balance!=0) {
            JOptionPane.showMessageDialog(frame,
                    "?????? ????????? ????????? 0?????? ??????????????????!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            int confirm=JOptionPane.showConfirmDialog(frame, 
                    "?????? ????????? ?????? ???????????????????", 
                    "?????? ??????", 
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm==JOptionPane.YES_OPTION) {
                int result=dao.deleteBank(accountNum);
                if (result==1) {
                    JOptionPane.showMessageDialog(frame, "?????????????????????.");
                    initializeTable(loginId);
                }
            } else {
                return ;
            }
        }
    }
    
    private void showMemberDeleteFrame() {
        List<Bank> list=new ArrayList<>();
        list=dao.selectMemberAccount(loginId);
        int isAllBalanceZero=1;
        for (Bank b : list) {
            if (b.getBalance()!=0) {
                isAllBalanceZero=0;
                break;
            }
        }
        if (isAllBalanceZero==0) {
            JOptionPane.showMessageDialog(frame,
                    "?????? ????????? ????????? 0?????? ??????????????????!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            int confirm=JOptionPane.showConfirmDialog(frame, 
                    "?????? ????????? ?????? ???????????????????", 
                    "?????? ??????", 
                    JOptionPane.YES_NO_OPTION);
            if (confirm==JOptionPane.YES_OPTION) {
                int result=dao.deleteMember(loginId);
                int result2=dao.deleteMemberBank(loginId);
                if (result==1) {
                    JOptionPane.showMessageDialog(frame, "?????????????????????.");
                    loginId="";
                    initializeTable(loginId);
                }
            } else {
                return ;
            }
        }
    }
    
    private void showInsertAccountFrame() {
        InsertAccountFrame.newInsertAccountFrame(frame, loginId, BankMain.this);
    }
    
    private void showSignUpFrame() {
        SignUpFrame.newSignUpFrame(frame);
    }
    
    private void showDepositFrame() {
        int row=table.getSelectedRow();
        if (row==-1) {
            JOptionPane.showMessageDialog(frame,
                    "????????? ?????? ???????????????.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } 
        String accountNum=(String) model.getValueAt(row, 0);
        
        System.out.println("accountNum = " + accountNum);
        
        BankDepositFrame.newBankDepositFrame(frame, accountNum, BankMain.this);
    }
    
    private void showWithdrawFrame() {
        int row=table.getSelectedRow();
        if (row==-1) {
            JOptionPane.showMessageDialog(frame,
                    "????????? ?????? ???????????????.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } 
        String accountNum=(String) model.getValueAt(row, 0);
        Integer balance=(Integer) model.getValueAt(row, 3);
        
        System.out.println("accountNum = " + accountNum);
        
        BankWithdrawFrame.newBankWithdrawFrame(frame, accountNum, balance, BankMain.this);
    }
    
    private void showRemitFrame() {
        int row=table.getSelectedRow();
        if (row==-1) {
            JOptionPane.showMessageDialog(frame,
                    "????????? ?????? ???????????????.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } 
        String accountNum=(String) model.getValueAt(row, 0);
        Integer balance=(Integer) model.getValueAt(row, 3);
        
        System.out.println("accountNum = " + accountNum);
        
        BankRemitFrame.newBankRemitFrame(frame, accountNum, balance, BankMain.this);
        
    }
    
    private boolean checkLogin() {
        if (loginId.equals("")) {
            return false;
        } else {
            return true;
        }
    }
    
    private boolean isManager() {
        if (loginId.equals("manager")) {
            return true;
        } else {
            return false;
        }
    }
    
    private void logout() {
        int confirm=JOptionPane.showConfirmDialog(
                frame, 
                "?????? ???????????? ??????????", 
                "???????????? ??????", 
                JOptionPane.YES_NO_OPTION);
        if (confirm==JOptionPane.YES_OPTION) {
            loginId="";
            initializeTable(loginId);
        }
    }

    @Override
    public void onBankLogin(String id) {
        initializeTable(id);
        
    }

    @Override
    public void onBankRemitted() {
        initializeTable(loginId);
        
    }

    @Override
    public void onBankWithdrawed() {
        initializeTable(loginId);
        
    }

    @Override
    public void onBankDeposited() {
        initializeTable(loginId);
        
    }

    @Override
    public void onAccountInserted() {
        initializeTable(loginId);
        
    }
}
