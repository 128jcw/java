package edu.java.project.model;

public class Bank {
    public interface Entity {
        String TBL_BANK="BANK";
        String COL_ACCOUNTNUM="ACCOUNTNUM";
        String COL_BANKNAME="BANKNAME";
        String COL_BANKMEMBERID="MEMBERID";
        String COL_BALANCE="BALANCE";
    }
    
    private String accountNum;
    private String bankName;
    private String memberId;
    private Integer balance;
    
    public Bank() {}

    public Bank(String accountNum, String bankName, String memberId, Integer balance) {
        this.accountNum = accountNum;
        this.bankName = bankName;
        this.memberId = memberId;
        this.balance = balance;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public String getBankName() {
        return bankName;
    }

    public String getMemberId() {
        return memberId;
    }

    public Integer getBalance() {
        return balance;
    }
    
    @Override
    public String toString() {
        return String.format("Bank(accountNum=%s, bankName=%s, memberId=%s, balance=%d", 
                accountNum, bankName, memberId, balance);
    }
    
}
