package edu.java.project.controller;

import static edu.java.project.model.Bank.Entity.*;
import static edu.java.project.model.Member.Entity.*;

public interface BankSql {
    // 모든 회원 찾기
    String SQL_SELECT_ALL_MEMBER=String.format(
            "select * from %s", 
            TBL_MEMBER);
    
    // 해당 회원 찾기
    String SQL_SELECT_MEMBER=String.format(
            "select * from %s where %s=? and %s=?", 
            TBL_MEMBER ,COL_MEMBERID, COL_MEMBERPASSWORD);
    
 // 멤버 아이디 순으로 모든 계좌 찾기
    String SQL_SELECT_ALL_BANK_BY_MEMBERID=String.format(
            "select * from %s order by %s", 
            TBL_BANK, COL_BANKMEMBERID);
    
    // 모든 계좌 찾기
    String SQL_SELECT_BANK_ALL=String.format(
            "select * from %s", 
            TBL_BANK);
    
    // 해당 회원의 계좌 모두 찾기
    String SQL_SELECT_MEMBER_ACCOUNT=String.format(
            "select * from %s where %s=?", 
            TBL_BANK, COL_BANKMEMBERID);
    
    // 해당 계좌 제외하고 찾기
    String SQL_SELECT_BANK_WITHOUT=String.format(
            "select * from %s where %s!=?", 
            TBL_BANK, COL_ACCOUNTNUM);
    
    // 해당 계좌만 찾기
    String SQL_SELECT_BANK_ONE=String.format(
            "select * from %s where %s=?", 
            TBL_BANK, COL_ACCOUNTNUM);
    
    // 은행 정보 업데이트
    String SQL_UPDATE_BANK=String.format(
            "update %s set %s = ? where %s = ?", 
            TBL_BANK, COL_BALANCE, COL_ACCOUNTNUM);
    
    // 새 회원 등록
    String SQL_INSERT_MEMBER=String.format(
            "insert into %s (%s, %s, %s) values (?, ?, ?)", 
            TBL_MEMBER, COL_MEMBERID, COL_MEMBERPASSWORD, COL_MEMBERNAME);
    
    // 새 계좌 등록
    String SQL_INSERT_ACCOUNT=String.format(
            "insert into %s (%s, %s, %s, %s) values (?, ?, ?, ?)", 
            TBL_BANK, COL_ACCOUNTNUM, COL_BANKNAME, COL_BANKMEMBERID, COL_BALANCE);
}
