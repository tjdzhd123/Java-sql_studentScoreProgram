package com.mire.score;

import java.io.Serializable;
import java.util.Objects;

//자바 DB를 활용한 학생성적프로그램 
public class StudentDataSub implements Comparable<Object>, Serializable {
    public static final int SUB =3;
   //멤버변수 입력 : 반번호, 이름, 성별, 국어, 수학, 영어 총합 평균 등급
   private int cnum;
   private String name;
   private String gender;
   private String birthday;
   private int kor;
   private int math;
   private int eng;
   private int total;
   private double avr;
   private String grade;
   
   //생성자
   public StudentDataSub(){
      this(0,null,null, null,0,0,0);
   }

   public StudentDataSub(int cnum, String name, String gender, String birthday, 
         int kor, int math, int eng) {
      super();
      this.cnum = cnum;
      this.name = name;
      this.gender = gender;
      this.birthday = birthday;
      this.kor = kor;
      this.math = math;
      this.eng = eng;
      this.total = 0;
      this.avr = 0.0;
      this.grade = null;
   }
   
   //총점(total)함수
   public void totalScore() {
      this.total = this.kor+this.math+this.eng;
   }
   //평균(avr)함수
   public void avrScore() {
      this.avr = this.total / (double)SUB;
   }
   //등급(grade)함수
   public void gradeScore() {
      if(this.avr >= 90.0) {
         this.grade = "A";
      }else if(this.avr >= 80.0 && this.avr < 90.0 ) {
         this.grade = "B";
      }else if(this.avr >= 70.0 && this.avr < 80.0 ) {
         this.grade = "C";
      }else if(this.avr >= 60.0 && this.avr < 70.0 ) {
         this.grade = "D";
      }else {
         this.grade = "F";
      }
   }

   //오버라딩 : hashCode(name), equals(name), compareTo(정렬 = grade), toString(Text)
   public int getCnum() {
      return cnum;
   }

   public void setCnum(int cnum) {
      this.cnum = cnum;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getGender() {
      return gender;
   }

   public void setGender(String gender) {
      this.gender = gender;
   }
   public String getbirthday() {
      return birthday;
   }
   public void setbirthday(String birthday) {
      this.birthday = birthday;
   }

   public int getKor() {
      return kor;
   }

   public void setKor(int kor) {
      this.kor = kor;
   }

   public int getMath() {
      return math;
   }

   public void setMath(int math) {
      this.math = math;
   }

   public int getEng() {
      return eng;
   }

   public void setEng(int eng) {
      this.eng = eng;
   }

   public int getTotal() {
      return total;
   }

   public void setTotal(int total) {
      this.total = total;
   }

   public double getAvr() {
      return avr;
   }

   public void setAvr(double avr) {
      this.avr = avr;
   }

   public String getGrade() {
      return grade;
   }

   public void setGrade(String grade) {
      this.grade = grade;
   }

   @Override
   public int hashCode() {

      return Objects.hash(name);
   }

   @Override
   public boolean equals(Object obj) {
      if(!(obj instanceof StudentDataSub)) {
         throw new IllegalArgumentException("StudentDataSub Class가 아닙니다.");
      }
      StudentDataSub studentDataSub = (StudentDataSub)obj;
   
      return this.name.equals(studentDataSub.name);
   }

   @Override
   public int compareTo(Object obj) {
      if(!(obj instanceof StudentDataSub)) {
         throw new IllegalArgumentException("StudentDataSub Class가 아닙니다.");
      }
      StudentDataSub studentDataSub = (StudentDataSub)obj;
      
      return this.name.compareTo(studentDataSub.grade);
   }
   
   @Override
   public String toString() {
      String year = birthday.substring(0,4);
      String month = birthday.substring(5,7);
      String day = birthday.substring(8,10);
      String data = year+ "년"+ month+ "월"+ day+ "일";
      
      return "반 번호 :"+cnum + "\t" + name +"\t" + gender + "\t"+ data +"\t"+ "국어 :"+ kor + "   " +"수학 :" + math + "   " + "영어 :" + eng +
    		  "   "+ "총점 :" +total+"   "+ "평균 :" + String.format("%6.2f", avr)+"\t"+"등급(A~F):" + grade+"\n";
   	}

}
