package com.mire.score;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;

public class StudentDBU {
	//����Լ� : �����ͺ��̽� ���ӿ�û
	//Connection�� DB�ϰ� �������ִ� DB Key�̴�. �� ���豸���̶�� ��������
	//Connection : �����ͺ��̽� ���� handle��
   public static Connection getConnection()  {
      Connection con = null;
      FileReader fr = null;

      try {
         fr = new FileReader("src\\com\\mire\\score\\db.properties");
         Properties properties = new Properties();
         properties.load(fr);
         
         String DRIVER = properties.getProperty("DRIVER");
         String URL = properties.getProperty("URL");
         String userID = properties.getProperty("userID");
         String userPassword = properties.getProperty("userPassword");
         
         //����̹� ����
         Class.forName(DRIVER);   
         //�����ͺ��̽� ����
         con = (Connection) DriverManager.getConnection(URL,userID,userPassword);
      } catch(ClassNotFoundException e) {
         System.out.println("mysql database connection fail");
         e.printStackTrace();
      } catch (SQLException e) {
         System.out.println("mysql database connection fail");
         e.printStackTrace();
      } catch (FileNotFoundException e) {
         System.out.println("file not found db.propertied");
         e.printStackTrace();
      } catch (IOException e) {
         System.out.println("file db.propertied connection fail");
         e.printStackTrace();
      }finally {
         try {
            fr.close();
         } catch (IOException e) {
         e.printStackTrace();
         }
      }
      return con;
   }
}