package edu.java.project.model;

public class Member {
    public interface Entity {
        String TBL_MEMBER="MEMBER";
        String COL_MEMBERID="MEMBERID";
        String COL_MEMBERPASSWORD="PASSWORD";
        String COL_MEMBERNAME="NAME";
    }
    
    private String memberId;
    private String password;
    private String name;
    
    public Member() {}

    public Member(String memberId, String password, String name) {
        this.memberId = memberId;
        this.password = password;
        this.name=name;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getPassword() {
        return password;
    }
    
    public String getName() {
        return name;
    }

    
    @Override
    public String toString() {
        return String.format("Member(memberId=%s, password=%s, name=%s)", memberId, password, name);
    }
    
}
