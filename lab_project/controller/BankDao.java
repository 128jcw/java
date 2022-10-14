package edu.java.project.controller;

import java.util.List;

import edu.java.project.model.Bank;
import edu.java.project.model.Member;

public interface BankDao {
    List<Member> selectAllMember();
    Member selectMember(Member m);
    List<Bank> selectAll();
    List<Bank> selectAllBankByMemberId();
    List<Bank> selectWithout(String accountNum);
    List<Bank> selectMemberAccount(String memberId);
    Bank selectOne(String accountNum);
    int update(Bank bank, int amount);
    int insertMember(Member m);
    int insertAccount(Bank b);
}
