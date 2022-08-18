package edu.java.condition4;

import java.util.Random;

public class ConditionMain4 {

    public static void main(String[] args) {
        // Random 클래스: 난수를 발생시키는 기능을 가지고있는 클래스
        Random random = new Random(); // Random 타입의 변수를 선언, 초기화.
        
        // 0 이상 100 이하의 정수 난수 생성
        /*
        int x = random.nextInt(11); // 101 미만인 난수 생성
        System.out.println("x = " + x);
        
        int y = random.nextInt(11);
        System.out.println("y = " + y);
        
        // 두 숫자 x와 y의 차이(큰 수 - 작은 수)를 계산
        int diff;
        if (x>y) {
            diff=x-y;
        } else {
            diff=y-x;
        }
        System.out.println("diff = " + diff); 
        
        // 삼항 연산자
        // (조건식) ? 조건이 참일 때 선택할 값 : 조건이 거짓일 때 선택할 값
        int diff2 = (x>y) ? (x-y) : (y-x);
        System.out.println("diff2 = " + diff2); */
        
        // 0 이상 10 이하의 정수 난수를 생성해서 변수(z)에 저장.
        // 문자열 변수를 선언, 변수 z가 짝수이면 even", 그렇지 않으면 "odd"를 저장
        int z = random.nextInt(11);
        String e;
        System.out.println("z = " + z);
        if (z%2==0) {
            e="even";
        } else {
            e="odd";
        }
        System.out.println(e);
        
        // 정수 난수 2개를 저장.(0 이상 11 미만)
        // int 타입 변수 bigger에 두 난수 중에서 더 크거나 같은 숫자를 저장
        int num1=random.nextInt(11);
        int num2=random.nextInt(11);
        System.out.println("num1 = " + num1 + "  num2 = " + num2);
        int bigger = (num1>=num2) ? num1 : num2;
        System.out.println("bigger = " + bigger);
        
        // Random 타입의 변수를 선언하고, 초기화
        
        //Java 과목의 점수를 0 이상 100이하의 난수를 만들어서 저장
        int javaScore = random.nextInt(101);
        System.out.println("JAVA 점수 = " + javaScore);
        //SQL 과목의 점수를 0 이상 100이하의 난수를 만들어서 저장
        int sqlScore = random.nextInt(101);
        System.out.println("SQL 점수 = " + sqlScore);
        //JSP 과목의 점수를 0 이상 100이하의 난수를 만들어서 저장
        int jspScore = random.nextInt(101);
        System.out.println("JSP 점수 = " + jspScore);
        // 세 과목의 평균을 계산
        double avg = (double)(javaScore+sqlScore+jspScore)/3;
        System.out.println("평균 점수 = " + avg);
        // 모든 과목의 점수가 40점 이상이고, 평균이 60점 이상이면 "합격" 그렇지 않으면 불합격
        if (javaScore>=40 && sqlScore>=40 && jspScore>=40 && avg>=60) {
            System.out.println("합격");
        } else {
            System.out.println("불합격");
        }

    }

}
