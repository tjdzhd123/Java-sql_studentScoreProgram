package com.mire.score;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.Connection;

public class DBController {

	//멤버함수 : 정적멤버함수(테이블 모델객체 삽입)
   public static int StudentInsert(StudentDataSub studentDataSub) {
      int returnValue = 0; //데이터베이스 명령문 리턴값
      //1.데이터베이스 접속
      Connection con = StudentDBU.getConnection();
      
      if(con == null) {
         System.out.println("Mysql Connection Fail");
         return 0;}

      //2. 명령문하달(삽입명령문하달: 쿼리문으로 명령문하달 : c u r d)
         String insertQuery ="insert into studentscoredb.studentscoretbl values(?,?,?,?,?,?,?,?,?,?)";
         PreparedStatement ps = null;
         try {
            //2.1 insert query binding
            ps = (PreparedStatement)con.prepareStatement(insertQuery);
            ps.setInt(1, studentDataSub.getCnum());
            ps.setString(2, studentDataSub.getName());
            ps.setString(3, studentDataSub.getGender());
            ps.setString(4, studentDataSub.getbirthday());
            ps.setInt(5, studentDataSub.getKor());
            ps.setInt(6, studentDataSub.getMath());
            ps.setInt(7, studentDataSub.getEng());
            ps.setInt(8, studentDataSub.getTotal());
            ps.setDouble(9, studentDataSub.getAvr());
            ps.setString(10, studentDataSub.getGrade());
            //2.2 명령문 실행 
            //3.  리턴값(삽입한 개수를 리턴한다.)
            //리텀벨류값을준다 
             returnValue = ps.executeUpdate();
            
         } catch (SQLException e) {
            e.printStackTrace();
         } finally {
            try {
               if(ps != null && !ps.isClosed()) {
                  ps.close();               
               }
               if(con != null && !con.isClosed()) {
                  con.close();   
               }
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         
         //4. 결과값을 통보한다.
         return returnValue;
      }
   //정적멤버함수(테이블 모델객체 출력하기)
   public static List<StudentDataSub> StudentDataSelect() {
	   //테이블에 있는 레코드 셋을 가져오기 위한 ArrayList<StudentDataSub>
      List<StudentDataSub> list = new ArrayList<StudentDataSub>();
      //1. 데이터베이스 접속요청
      Connection con = StudentDBU.getConnection();
      
      if(con == null) {
         System.out.println("Mysql이 연결되지 않았습니다.");
         return null;
      }
      	//2. 명령문하달(삽입명형문 하달 : 쿼리문으로 명령문 하달 : select * from studentscoretbl;)
         String selectQuery = "select * from studentscoretbl";
         PreparedStatement ps = null;
         ResultSet resultSet = null;
         
         try {
        	 //2.1 select query binding
            ps = (PreparedStatement)con.prepareStatement(selectQuery);
            
            //2.2 명령문 실행 (select 쿼리문)
            //3. 리턴값(레코드 셋 = resultSet을 턴한다.) 구별 : executeUpdate()와 executeQuery()를 구별
            //while안에 들어간것은 있는지
            //레코드셋을 리스트로 가져온다.
            resultSet = ps.executeQuery();
            while(resultSet.next()) {
               int cnum = resultSet.getInt(1);
               String name = resultSet.getString(2);
               String gender = resultSet.getString(3);
               String birthday = resultSet.getString(4);
               int kor = resultSet.getInt(5);
               int math = resultSet.getInt(6);
               int eng = resultSet.getInt(7);
               int total = resultSet.getInt(8);
               double avr = resultSet.getDouble(9);
               String grade = resultSet.getString(10);

               StudentDataSub studentDataSub = new StudentDataSub(cnum, name, gender, birthday, kor, math, eng);
               //총점 평균 등급 가져오기 (set)
              studentDataSub.setTotal(total);
              studentDataSub.setAvr(avr);
              studentDataSub.setGrade(grade);
              
               list.add(studentDataSub);
            }
            
         } catch (SQLException e) {
            e.printStackTrace();
         } finally {
            try {
               if(ps != null && !ps.isClosed()) {
                  ps.close();               
               }
               if(con != null && !con.isClosed()) {
                  //con을 닫아야지 다른사람도 사용할 수 있다.
                  con.close();   
               }
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
   return list;
   }
   
   //정적멤버함수(테이블 검색하기 : 이름(1), 성별(2))
   public static List<StudentDataSub> StudentSearch(String searchData, int number){
	   final int NAME_NUM = 1, GENDER_NUM = 2;
	   //레코드 셋 저장을 List Collection
	   List<StudentDataSub> list = new ArrayList<StudentDataSub>();
	   
	   //1. 데이터베이스 접속요청
	   Connection con = StudentDBU.getConnection();
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return null;
		}
		
		//2. 명령문하달(검색명령문하달: 쿼리문으로 명령하달 : select * from studentscoredb.studentscoretbl where name like ~)
		String searchQuery = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		switch(number) {
		case NAME_NUM: 
			searchQuery = "select * from studentscoredb.studentscoretbl where name like ?";
			break;
		case GENDER_NUM: 
			searchQuery = "select * from studentscoredb.studentscoretbl where gender like ?";
			break;
		}
		//2.1 select query binding /prepareStatement = 보안을위해
		
		try {
			ps = (PreparedStatement) con.prepareStatement(searchQuery);
			searchData = "%"+searchData+"%";
			ps.setString(1, searchData);
			//2.2 명령문 실행
			//구별 : executeUpdate()와 executeQuery()
			resultSet = ps.executeQuery();
			//3.1 리턴값 ResultSet을 ArrayList<StudentDataSub> 변환한다. 
			while(resultSet.next()) {
				 int cnum = resultSet.getInt(1);
	             String name = resultSet.getString(2);
	             String gender = resultSet.getString(3);
	             String birthday = resultSet.getString(4);
	             int kor = resultSet.getInt(5);
	             int math = resultSet.getInt(6);
	             int eng = resultSet.getInt(7);
	             int total = resultSet.getInt(8);
	             double avr = resultSet.getDouble(9);
	             String grade = resultSet.getString(10);
				
	             StudentDataSub studentDataSub = new StudentDataSub(cnum, name, gender, birthday, kor, math, eng);
	              studentDataSub.setTotal(total);
	              studentDataSub.setAvr(avr);
	              studentDataSub.setGrade(grade);
	    
	              list.add(studentDataSub);
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !ps.isClosed()) {
					ps.close();
				}
				if(con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	//4. 결과값을 통보
   	return list;
   }
   //정적멤버함수(테이블 삭제하기 : 이름)
   	public static int StudentDelete(String deleteData, int number) {
   		final int NAME_NUM = 1;
   		
   		//1.데이터베이스 접속요청
		Connection con = StudentDBU.getConnection();
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return 0;
		}
		
		//2. 명령문하달(삭제명령문하달: 쿼리문으로 명령하달 :delete from studentscoredb.studentscoretbl where name like?)
		String deleteQuery = null;
		PreparedStatement ps = null;
		int resultNumber = 0;
		
		switch(number) {
		case NAME_NUM:
			deleteQuery = "delete from studentscoredb.studentscoretbl where name like ?";
			break;
		}
		//2.1 select query binding /prepareStatement = 보안을위해
		try {
			ps = (PreparedStatement) con.prepareStatement(deleteQuery);
			deleteData = "%"+deleteData+"%";
			ps.setString(1, deleteData);
			//2.2 명령문 실행
			//구별 : executeUpdate()와 executeQuery()
			resultNumber = ps.executeUpdate();
			
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !ps.isClosed()) {
					ps.close();					
				}
				if(con != null && !con.isClosed()) {
					con.close();	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}

	//4. 결과값을 통보한다.
	return resultNumber;
   	}
   	//정적멤버함수(테이블 수정하기 : 이름으로 검색 후 과목점수 수정)
   	public static int StudentUpdate(String name, int kor, int math, int eng, int total, double avr, String grade) {
   		
		//1.데이터베이스 접속요청
		Connection con = StudentDBU.getConnection();
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return 0;
		}
   		//2. 명령문 하달(수정명령문하달 : 쿼리문으로 명령하달 : update syudentscoretbl set ~)
		String updateQuery = null;
		PreparedStatement ps = null;
		int resultNumber = 0;
		
		//세과목, 총점, 평균, 등급 수정
		updateQuery = "update studentscoretbl set " +
						"kor = ?,   " 	+
						"math = ?, " 	+
						"eng = ?, "		+
						"total = ?, "	+
						"avr = ?, "		+
						"grade = ? "	+
						"where name like ?";

		//2.1  update query binding /prepareStatement = 보안을위해
		try {
			ps = (PreparedStatement) con.prepareStatement(updateQuery);
			ps.setInt(1, kor);
			ps.setInt(2, math);
			ps.setInt(3, eng);
			ps.setInt(4, total);
			ps.setDouble(5, avr);
			ps.setString(6, grade);
			ps.setString(7, name);
			
			resultNumber = ps.executeUpdate();
			
		}catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(ps != null && !ps.isClosed()) {
						ps.close();					
					}
					if(con != null && !con.isClosed()) {
						con.close();	
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		return resultNumber;
   	}
   	//정적멤버함수(테이블 정렬하기 : 이름으로 정렬 (오름차순, 내림차순)
   	public static List<StudentDataSub> studentSort(int no){
   		final int ASC = 1, DESC = 2;
   		//테이블에 있는 레코드 셋을 가져오기 위한 ArrayList<StudentDataSub>
   		List<StudentDataSub> list = new ArrayList<StudentDataSub>();
   		String sortQuery = null;
   		//1. 데이터베이스 접속요청
   		Connection con = StudentDBU.getConnection();
   		if(con == null) {
   			System.out.println("Mysql Connection Fail");
   			return null;
   		}
   		
   	//2. 명령문하달	(오름차순 명렬 하달 : select * from studentscoredb.studentscoretbl order by grade asc)
   	//			(내림차순 명렬 하달 : select * from studentscoredb.studentscoretbl order by grade desc)
   		switch(no) {
   		case ASC :
   			sortQuery = "select * from studentscoredb.studentscoretbl order by grade asc";
   			break;
   		case DESC :
   			sortQuery = "select * from studentscoredb.studentscoretbl order by grade desc";
   			break;
   		}
   		PreparedStatement ps = null;
   		ResultSet resultSet = null;
   		
   		try {
   			//2.1 select query binding
			ps = (PreparedStatement)con.prepareStatement(sortQuery);
			
			//2.2 명령문 실행 (select 쿼리문)
			resultSet = ps.executeQuery();
			//레코드셋 리스트 가져오기
			while(resultSet.next()) {
				 int cnum = resultSet.getInt(1);
	             String name = resultSet.getString(2);
	             String gender = resultSet.getString(3);
	             String birthday = resultSet.getString(4);
	             int kor = resultSet.getInt(5);
	             int math = resultSet.getInt(6);
	             int eng = resultSet.getInt(7);
	             int total = resultSet.getInt(8);
	             double avr = resultSet.getDouble(9);
	             String grade = resultSet.getString(10);
				
	             StudentDataSub studentDataSub = new StudentDataSub(cnum, name, gender, birthday, kor, math, eng);
	              studentDataSub.setTotal(total);
	              studentDataSub.setAvr(avr);
	              studentDataSub.setGrade(grade);
	    
	              list.add(studentDataSub);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !ps.isClosed()) {
					ps.close();					
				}
				if(con != null && !con.isClosed()) {
					con.close();	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
   		//4. 결과값 리턴
   		return list;
   	}

}//end

