package edu.java.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.java.project.model.Bank;
import edu.java.project.model.Member;
import oracle.jdbc.driver.OracleDriver;

import static edu.java.project.connect.OracleJdbc.*;
import static edu.java.project.model.Bank.Entity.*;
import static edu.java.project.model.Member.Entity.*;
import static edu.java.project.controller.BankSql.*;

public class BankDaoImpl implements BankDao {
    
    private static BankDaoImpl instance=null;
    
    private BankDaoImpl() {}
    
    public static BankDaoImpl getInstance() {
        if (instance==null) {
            instance = new BankDaoImpl();
        }
        
        return instance;
    }
    
    private Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new OracleDriver());
        
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    private void closeResources(Connection conn, Statement stmt) throws SQLException {
        stmt.close();
        conn.close();
    }
    
    private void closeResources(Connection conn, Statement stmt, ResultSet rs) 
            throws SQLException {
        rs.close();
        closeResources(conn, stmt);
    }
    
    @Override
    public List<Member> selectAllMember() {
        List<Member> list=new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn=getConnection();
            System.out.println(SQL_SELECT_ALL_MEMBER);
            stmt=conn.prepareStatement(SQL_SELECT_ALL_MEMBER);
            rs=stmt.executeQuery();
            
            while (rs.next()) {
                String id=rs.getString(COL_MEMBERID);
                String password=rs.getString(COL_MEMBERPASSWORD);
                String name=rs.getString(COL_MEMBERNAME);
                Member member=new Member(id, password, name);
                
                list.add(member);
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return list;
    }

    @Override
    public Member selectMember(Member m) {
        Member member=null;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn=getConnection();
            System.out.println(SQL_SELECT_MEMBER);
            stmt=conn.prepareStatement(SQL_SELECT_MEMBER);
            stmt.setString(1, m.getMemberId());
            stmt.setString(2, m.getPassword());
            
            rs=stmt.executeQuery();
            while (rs.next()) {
                String memberId=rs.getString(COL_MEMBERID);
                String memberpassword=rs.getString(COL_MEMBERPASSWORD);
                String memberName=rs.getString(COL_MEMBERNAME);
                
                member=new Member(memberId, memberpassword, memberName);
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return member;
    }
    
    @Override
    public List<Bank> selectAllBankByMemberId() {
        List<Bank> list=new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn=getConnection();
            
            System.out.println(SQL_SELECT_ALL_BANK_BY_MEMBERID);
            stmt=conn.prepareStatement(SQL_SELECT_ALL_BANK_BY_MEMBERID);
            rs=stmt.executeQuery();
            
            while (rs.next()) {
                String accountNum=rs.getString(COL_ACCOUNTNUM);
                String bankName=rs.getString(COL_BANKNAME);
                String memberId=rs.getString(COL_BANKMEMBERID);
                Integer balance=rs.getInt(COL_BALANCE);
                
                Bank bank=new Bank(accountNum, bankName, memberId, balance);
                
                list.add(bank);
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return list;
    }

    @Override
    public List<Bank> selectAll() {
        List<Bank> list=new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn=getConnection();
            
            System.out.println(SQL_SELECT_BANK_ALL);
            stmt=conn.prepareStatement(SQL_SELECT_BANK_ALL);
            rs=stmt.executeQuery();
            
            while (rs.next()) {
                String accountNum=rs.getString(COL_ACCOUNTNUM);
                String bankName=rs.getString(COL_BANKNAME);
                String memberId=rs.getString(COL_BANKMEMBERID);
                Integer balance=rs.getInt(COL_BALANCE);
                
                Bank bank=new Bank(accountNum, bankName, memberId, balance);
                
                list.add(bank);
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return list;
    }

    @Override
    public List<Bank> selectWithout(String accountNum) {
        List<Bank> list=new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn=getConnection();
            
            System.out.println(SQL_SELECT_BANK_WITHOUT);
            stmt=conn.prepareStatement(SQL_SELECT_BANK_WITHOUT);
            stmt.setString(1, accountNum);
            
            rs=stmt.executeQuery();
            
            while (rs.next()) {
                String accountnumber=rs.getString(COL_ACCOUNTNUM);
                String bankName=rs.getString(COL_BANKNAME);
                String bankMemberId=rs.getString(COL_BANKMEMBERID);
                Integer balance=rs.getInt(COL_BALANCE);
                
                Bank bank=new Bank(accountnumber, bankName, bankMemberId, balance);
                list.add(bank);
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return list;
    }

    @Override
    public List<Bank> selectMemberAccount(String memberId) {
        List<Bank> list=new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn=getConnection();
            
            System.out.println(SQL_SELECT_MEMBER_ACCOUNT);
            stmt=conn.prepareStatement(SQL_SELECT_MEMBER_ACCOUNT);
            stmt.setString(1, memberId);
            
            rs=stmt.executeQuery();
            
            while (rs.next()) {
                String accountnumber=rs.getString(COL_ACCOUNTNUM);
                String bankName=rs.getString(COL_BANKNAME);
                String bankMemberId=rs.getString(COL_BANKMEMBERID);
                Integer balance=rs.getInt(COL_BALANCE);
                
                Bank bank=new Bank(accountnumber, bankName, bankMemberId, balance);
                list.add(bank);
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return list;
    }

    @Override
    public Bank selectOne(String accountNum) {
        Bank bank=null;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn=getConnection();
            
            System.out.println(SQL_SELECT_BANK_ONE);
            stmt=conn.prepareStatement(SQL_SELECT_BANK_ONE);
            stmt.setString(1, accountNum);
            
            rs=stmt.executeQuery();
            
            while (rs.next()) {
                String accountnumber=rs.getString(COL_ACCOUNTNUM);
                String bankName=rs.getString(COL_BANKNAME);
                String bankMemberId=rs.getString(COL_BANKMEMBERID);
                Integer balance=rs.getInt(COL_BALANCE);
                
                bank=new Bank(accountnumber, bankName, bankMemberId, balance);
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return bank;
    }

    @Override
    public int update(Bank bank, int amount) {
        int result=0;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn=getConnection();
            
            System.out.println(SQL_UPDATE_BANK);
            stmt=conn.prepareStatement(SQL_UPDATE_BANK);
            stmt.setInt(1, bank.getBalance()+amount);
            stmt.setString(2, bank.getAccountNum());
            
            result=stmt.executeUpdate();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return result;
    }

    @Override
    public int insertMember(Member m) {
        int result=0;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn=getConnection();
            
            System.out.println(SQL_INSERT_MEMBER);
            stmt=conn.prepareStatement(SQL_INSERT_MEMBER);
            stmt.setString(1, m.getMemberId());
            stmt.setString(2, m.getPassword());
            stmt.setString(3, m.getName());
            
            result=stmt.executeUpdate();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return result;
    }

    @Override
    public int insertAccount(Bank b) {
        int result=0;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn=getConnection();
            
            System.out.println(SQL_INSERT_ACCOUNT);
            stmt=conn.prepareStatement(SQL_INSERT_ACCOUNT);
            stmt.setString(1, b.getAccountNum());
            stmt.setString(2, b.getBankName());
            stmt.setString(3, b.getMemberId());
            stmt.setInt(4, 0);
            
            result=stmt.executeUpdate();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        return result;
    }

}
