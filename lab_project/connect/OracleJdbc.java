package edu.java.project.connect;

public interface OracleJdbc {
 // Oracle DB에 접속하는 주소(, 포트번호, SID)를 정의
    String URL="jdbc:oracle:thin:@192.168.20.2:1521:xe";
    
    // Oracle DB에 접속하는 사용자 계정
    String USER="scott";
    
    // Oracle DB에 접속하는 사용자 비밀번호
    String PASSWORD="tiger";
}
