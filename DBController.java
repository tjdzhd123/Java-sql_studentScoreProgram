package com.mire.score;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.Connection;

public class DBController {

	//����Լ� : ��������Լ�(���̺� �𵨰�ü ����)
   public static int StudentInsert(StudentDataSub studentDataSub) {
      int returnValue = 0; //�����ͺ��̽� ��ɹ� ���ϰ�
      //1.�����ͺ��̽� ����
      Connection con = StudentDBU.getConnection();
      
      if(con == null) {
         System.out.println("Mysql Connection Fail");
         return 0;}

      //2. ��ɹ��ϴ�(���Ը�ɹ��ϴ�: ���������� ��ɹ��ϴ� : c u r d)
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
            //2.2 ��ɹ� ���� 
            //3.  ���ϰ�(������ ������ �����Ѵ�.)
            //���Һ��������ش� 
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
         
         //4. ������� �뺸�Ѵ�.
         return returnValue;
      }
   //��������Լ�(���̺� �𵨰�ü ����ϱ�)
   public static List<StudentDataSub> StudentDataSelect() {
	   //���̺� �ִ� ���ڵ� ���� �������� ���� ArrayList<StudentDataSub>
      List<StudentDataSub> list = new ArrayList<StudentDataSub>();
      //1. �����ͺ��̽� ���ӿ�û
      Connection con = StudentDBU.getConnection();
      
      if(con == null) {
         System.out.println("Mysql�� ������� �ʾҽ��ϴ�.");
         return null;
      }
      	//2. ��ɹ��ϴ�(���Ը����� �ϴ� : ���������� ��ɹ� �ϴ� : select * from studentscoretbl;)
         String selectQuery = "select * from studentscoretbl";
         PreparedStatement ps = null;
         ResultSet resultSet = null;
         
         try {
        	 //2.1 select query binding
            ps = (PreparedStatement)con.prepareStatement(selectQuery);
            
            //2.2 ��ɹ� ���� (select ������)
            //3. ���ϰ�(���ڵ� �� = resultSet�� ���Ѵ�.) ���� : executeUpdate()�� executeQuery()�� ����
            //while�ȿ� ������ �ִ���
            //���ڵ���� ����Ʈ�� �����´�.
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
               //���� ��� ��� �������� (set)
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
                  //con�� �ݾƾ��� �ٸ������ ����� �� �ִ�.
                  con.close();   
               }
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
   return list;
   }
   
   //��������Լ�(���̺� �˻��ϱ� : �̸�(1), ����(2))
   public static List<StudentDataSub> StudentSearch(String searchData, int number){
	   final int NAME_NUM = 1, GENDER_NUM = 2;
	   //���ڵ� �� ������ List Collection
	   List<StudentDataSub> list = new ArrayList<StudentDataSub>();
	   
	   //1. �����ͺ��̽� ���ӿ�û
	   Connection con = StudentDBU.getConnection();
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return null;
		}
		
		//2. ��ɹ��ϴ�(�˻���ɹ��ϴ�: ���������� ����ϴ� : select * from studentscoredb.studentscoretbl where name like ~)
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
		//2.1 select query binding /prepareStatement = ����������
		
		try {
			ps = (PreparedStatement) con.prepareStatement(searchQuery);
			searchData = "%"+searchData+"%";
			ps.setString(1, searchData);
			//2.2 ��ɹ� ����
			//���� : executeUpdate()�� executeQuery()
			resultSet = ps.executeQuery();
			//3.1 ���ϰ� ResultSet�� ArrayList<StudentDataSub> ��ȯ�Ѵ�. 
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
	//4. ������� �뺸
   	return list;
   }
   //��������Լ�(���̺� �����ϱ� : �̸�)
   	public static int StudentDelete(String deleteData, int number) {
   		final int NAME_NUM = 1;
   		
   		//1.�����ͺ��̽� ���ӿ�û
		Connection con = StudentDBU.getConnection();
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return 0;
		}
		
		//2. ��ɹ��ϴ�(������ɹ��ϴ�: ���������� ����ϴ� :delete from studentscoredb.studentscoretbl where name like?)
		String deleteQuery = null;
		PreparedStatement ps = null;
		int resultNumber = 0;
		
		switch(number) {
		case NAME_NUM:
			deleteQuery = "delete from studentscoredb.studentscoretbl where name like ?";
			break;
		}
		//2.1 select query binding /prepareStatement = ����������
		try {
			ps = (PreparedStatement) con.prepareStatement(deleteQuery);
			deleteData = "%"+deleteData+"%";
			ps.setString(1, deleteData);
			//2.2 ��ɹ� ����
			//���� : executeUpdate()�� executeQuery()
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

	//4. ������� �뺸�Ѵ�.
	return resultNumber;
   	}
   	//��������Լ�(���̺� �����ϱ� : �̸����� �˻� �� �������� ����)
   	public static int StudentUpdate(String name, int kor, int math, int eng, int total, double avr, String grade) {
   		
		//1.�����ͺ��̽� ���ӿ�û
		Connection con = StudentDBU.getConnection();
		if(con == null) {
			System.out.println("Mysql Connection Fail");
			return 0;
		}
   		//2. ��ɹ� �ϴ�(������ɹ��ϴ� : ���������� ����ϴ� : update syudentscoretbl set ~)
		String updateQuery = null;
		PreparedStatement ps = null;
		int resultNumber = 0;
		
		//������, ����, ���, ��� ����
		updateQuery = "update studentscoretbl set " +
						"kor = ?,   " 	+
						"math = ?, " 	+
						"eng = ?, "		+
						"total = ?, "	+
						"avr = ?, "		+
						"grade = ? "	+
						"where name like ?";

		//2.1  update query binding /prepareStatement = ����������
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
   	//��������Լ�(���̺� �����ϱ� : �̸����� ���� (��������, ��������)
   	public static List<StudentDataSub> studentSort(int no){
   		final int ASC = 1, DESC = 2;
   		//���̺� �ִ� ���ڵ� ���� �������� ���� ArrayList<StudentDataSub>
   		List<StudentDataSub> list = new ArrayList<StudentDataSub>();
   		String sortQuery = null;
   		//1. �����ͺ��̽� ���ӿ�û
   		Connection con = StudentDBU.getConnection();
   		if(con == null) {
   			System.out.println("Mysql Connection Fail");
   			return null;
   		}
   		
   	//2. ��ɹ��ϴ�	(�������� ��� �ϴ� : select * from studentscoredb.studentscoretbl order by grade asc)
   	//			(�������� ��� �ϴ� : select * from studentscoredb.studentscoretbl order by grade desc)
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
			
			//2.2 ��ɹ� ���� (select ������)
			resultSet = ps.executeQuery();
			//���ڵ�� ����Ʈ ��������
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
   		//4. ����� ����
   		return list;
   	}

}//end

