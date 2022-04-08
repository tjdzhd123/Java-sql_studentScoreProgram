package com.mire.score;
//�ҽ� ������ ctrl + shift + ������ Ű�е� �� "/"
//ctrl + *
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentDataMain {
   public static Scanner scan = new Scanner(System.in);
   public static final int INSERT = 1, SEARCH = 2, DELETE = 3, UPDATE = 4, PRINT = 5, SORT = 6, EXIT = 7;
   public static final int NAME = 1, BIRTHDAY = 2;
   public static void main(String[] args) {
      
      boolean flag = false;
      while(!flag) {
         int no = selectMenu(); //�޴�����
         switch(no) {
         case INSERT: insertStudent(); break; //����
         case SEARCH: searchStudent(); break; //�˻�
         case DELETE: deleteStudent(); break; //����
         case UPDATE: updateStudent(); break; //����
         case PRINT:  printStudent(); break;  //���
         case SORT:     sortStudent(); break; //����
         case EXIT: System.out.println("���α׷� ����"); //����
                  scan.nextLine(); flag = true; break;
         default : System.out.println("�ٽ� �Է����ּ���"); break;
         }
      }
   }
   //�Է� : �� ��ȣ, �̸�, ����, �������, ����, ����, ���� �� �Է��Ѵ�.
   private static void insertStudent() {
	  //�Է� �޾ƾ��� ��
      int cnum = 0;
      String name = null;
      String gender = null;
      String birthday = null;
      int kor = 0;
      int math = 0;
      int eng = 0;
      
      //�� ��ȣ
      while(true) {
         System.out.print("�� ��ȣ �Է�(30xx) >>");
         cnum = scan.nextInt();
         
         if(cnum >= 3000 && cnum <= 3099) {
            break;
         }else {
            System.out.println("�ٽ� �Է����ּ���.");
         }
      }
      //�̸�
      while(true) {
         System.out.print("�̸� �Է� >>");
         scan.nextLine();
         name = scan.nextLine();
         
         if(patternCheck(name, NAME)) {
            break;
         }else {
            System.out.println("�ٽ� �Է����ּ���");
         }
      }
      //����
      while(true) {
         System.out.print("����(���� or ����) �Է� >>");
         gender = scan.nextLine();
         
         if(gender.equals("����") || gender.equals("����")) {
            break;
         }else {
            System.out.println("�ٽ� �Է����ּ���");
         }
      }
      
      //�������
      while(true) {
         System.out.print("�������(1995-03-09)10�� �Է�>>");
         birthday = scan.nextLine();
         
         if(patternCheck(birthday, BIRTHDAY)) {
            break;
         }else {
            System.out.println("�ٽ� �Է����ּ���.");
         }
      }
      //����
      while(true) {
         System.out.print("���� ����(0~100) �Է�>>");
         kor = scan.nextInt();
         
         if(kor >= 0 && kor <= 100) {
            break;
         }else {
            System.out.println("�ٽ� �Է����ּ���.");
         }
      }
      //����
      while(true) {
         System.out.print("���� ����(0~100) �Է�>>");
         math = scan.nextInt();
         
         if(math >= 0 && math <= 100) {
            break;
         }else {
            System.out.println("�ٽ� �Է����ּ���.");
         }
      }
      //����
      while(true) {
         System.out.print("���� ����(0~100) �Է�>>");
         eng = scan.nextInt();
         
         if(eng >= 0 && eng <= 100) {
            break;
         }else {
            System.out.println("�ٽ� �Է����ּ���.");
         }
      }
      scan.nextLine();
      //������ �� ����
      StudentDataSub studentDataSub = new StudentDataSub( cnum, name, gender, birthday, kor, math, eng);
      //�޼ҵ弱���� ���� ��� ����� ����ϱ�
      studentDataSub.totalScore();
      studentDataSub.avrScore();
      studentDataSub.gradeScore();
      
      int returnvalue = DBController.StudentInsert(studentDataSub);
      
      if(returnvalue != 0) {
    	  System.out.println(studentDataSub.getName()+"�� ���� ����");
      }else {
    	  System.out.println(studentDataSub.getName()+"�� ���� ����");
      }
      
   }
   
   //�˻� (�̸�, ����)
   private static void searchStudent() {
	   final int NAME_NUM = 1, GENDER_NUM = 2, EXIT = 3;
      List<StudentDataSub> list = new ArrayList<StudentDataSub>();
      //�˻��� ������ ��û(�̸��� ����)
      String name = null;
      String gender = null;
      
      String searchData = null;
      int number = 0;
      int no = 0;
      boolean flag = false;      
      
      //�˻� �޴�
      no = studentMenu();
      
      switch(no) {
      case NAME_NUM :
    	  while(true) {
				System.out.print("ã���� �ϴ� �̸� �Է�>>");
				name = scan.nextLine();
				
				if(patternCheck(name, NAME)) {
					break;
				}else {
					System.out.println("�ٽ� �Է����ּ���.");
				}
    	  }
			searchData = name;
			number = NAME_NUM;
			
			break;
			
      case GENDER_NUM :
    	  while(true) {
				System.out.print("����(���� or ����) �Է� >>");
				gender = scan.nextLine();
				
				if(gender.equals("����") || gender.equals("����")) {
					break;
				}else {
					System.out.println("�ٽ� �Է����ּ���.");
				}
			}
			searchData = gender;
			number = GENDER_NUM;
			break;

      case EXIT :
			System.out.println("�˻� ��� �Ϸ�");
			flag = true;
			break;
		}
		if(flag) {			
			return; 
		} 	
			list = DBController.StudentSearch(searchData, number);
			
			if(list.size() <= 0) {
				System.out.println(searchData +"ã�� ���߽��ϴ�.");
				return;
			}
		for(StudentDataSub data : list) {
			System.out.println(data);
		}	
			
			
      }
   
   private static int studentMenu() {
		boolean flag =false;
		int no = 0;
		
		while(!flag) {
			System.out.println("*******************");
			System.out.println("1. �̸� 2. ���� 3. ����");
			System.out.println("*******************");
			System.out.print("��ȣ���� >>");
			
			try {
				no = Integer.parseInt(scan.nextLine());
			}catch(InputMismatchException e) {
				System.out.println("�ٽ� �Է����ּ���!");
				continue;
			}catch(Exception e) {
				System.out.println("�ٽ� �Է����ּ���!");
				continue;
			}
			
			if(no >= 1 && no <= 3) {
				flag = true;
			}else {
				System.out.println("�ٽ� �Է����ּ���!(1~3)");
				continue;
			}
			
		}//end of while
		return no;
	}
   
   //���� (�̸�)
   private static void deleteStudent() {
	  final int NAME_NUM = 1;
	  String name = null;
	  String deleteData = null;
	  int number = 0;
	  int resultNumber = 0;
	  
	  while(true) {
		  System.out.print("ã���� �ϴ� �̸� �Է�>>");
			name = scan.nextLine();

			if(patternCheck(name, NAME)) {
				break;
			}else {
				System.out.println("�ٽ� �Է����ּ���.");
			}
	  }
	  deleteData = name;
	  //Ȯ�强�� ���ؼ�
	  number = NAME_NUM;
	  
	  resultNumber = DBController.StudentDelete(deleteData, number);
		
		 if(resultNumber != 0) {
			 System.out.println(name+" �̸� ���ڵ尡 �����Ϸ��߽��ϴ�.");
		 }else {
			 System.out.println(name+" ���� �����Ͽ����ϴ�.");
		 }
      
   }
   
   //����(�̸�) -> �������(����, ����, ���� ����, ���, ���)
   private static void updateStudent() {
    
	   final int NAME_NUM = 1;
	   List<StudentDataSub> list = new ArrayList<StudentDataSub>();
	   //�˻��� ������ ���ÿ�û(��ȭ��ȣ, �̸�, ����)
	   String name = null;
	   String searchData = null;
	   int number = 0;
	   int resultNumber = 0;
	   int kor = 0;
	   int math = 0;
	   int eng = 0;
	   
	   int total = 0;
	   double avr = 0.0;
	   String grade = null;
	   
	   //������ �̸� �˻�
	   while(true) {
		   System.out.print("�����ϰ��� �ϴ� �̸� �Է� >>");
		   name = scan.nextLine();
		   
		   if(patternCheck(name, NAME)) {
			   break;
		   }else {
			   System.out.println("�ٽ� �Է����ּ���.");
		   }
	   }
	   searchData = name;
	   number = NAME_NUM;
	   
	   list = DBController.StudentSearch(searchData, number);
	   
	   if(list.size() <= 0) {
			System.out.println(searchData +"ã�� ���߽��ϴ�.");
			return;
		}
	   
	   StudentDataSub data_buffer = null;
	   for(StudentDataSub data : list) {
		   System.out.println(data);
		   data_buffer = data;
	   }
	   
	   //2. �̸��� ������ ������ �۾��� �޾Ƽ� ������û
	   while(true) {
		   System.out.print("["+data_buffer.getKor()+"] \n������ ���� ���� �Է� >>");
		   kor = scan.nextInt();
		   
		   if(kor >= 0 && kor <= 100) {
			   break;
		   }else {
			   System.out.println("�ٽ� �Է����ּ���.");
		   }
	   }
		   
	   while(true) {
		   System.out.print("["+data_buffer.getMath()+"] \n������ ���� ���� �Է� >>");
		   math = scan.nextInt();
		   
		   if(math >= 0 && math <= 100) {
			   break;
		   }else {
			   System.out.println("�ٽ� �Է����ּ���.");
		   }
	   }
		   
	   while(true) {
		   System.out.print("["+data_buffer.getEng()+"] \n������ ���� ���� �Է� >>");
		   eng = scan.nextInt();
		   
		   if(eng >= 0 && eng <= 100) {
			   break;
		   }else {
			   System.out.println("�ٽ� �Է����ּ���.");
		   }
	   }
	   scan.nextLine();
	   
	   StudentDataSub studentDataSub = new StudentDataSub();
	   //�Է��� kor, math, eng�� studentDataSub�� �� �ֱ�
	   studentDataSub.setKor(kor);
	   studentDataSub.setMath(math);
	   studentDataSub.setEng(eng);
	   //����, ���, ���� �Լ� ��������
	   studentDataSub.totalScore();
	   studentDataSub.avrScore();
	   studentDataSub.gradeScore();
	   
	   total = studentDataSub.getTotal();
	   avr = studentDataSub.getAvr();
	   grade =studentDataSub.getGrade();
	   
		//3. ����� Ȯ��
		resultNumber = DBController.StudentUpdate(name, kor, math, eng, total, avr, grade);

		if(resultNumber != 0) {
			System.out.println(name + "���� �����Ϸ��߽��ϴ�.");
		}else {
			System.out.println(name + "���� ���������Ͽ����ϴ�.");
		}
		
		return;
   }
   //���
   private static void printStudent() {
      List<StudentDataSub> list = new ArrayList<StudentDataSub>();
      
      list = DBController.StudentDataSelect();
      
      if(list.size() <= 0) {
         System.out.println("����� ��ȭ��ȣ�� ������ �����");
         return;
      }
      
      for(StudentDataSub data: list) {
         System.out.println(data.toString());
      }
      
   }
   
   //���� = ������ �ǵ��� �ڹٺ��� DB�� �ϴ°��� ������. (�� Ưȭ �Ǿ��ֱ� �����̰� ������� ������ �Ǳ� ������)
   private static void sortStudent() {
	   List<StudentDataSub> list = new ArrayList<StudentDataSub>();
	   int no = 0;
	   boolean flag = false;
	   while(!flag) {
		   
		//1. ���Ĺ��(��������, ��������)
		System.out.println("[���] 1. ��������, 2. ��������");
		System.out.print("���Ĺ�� ����>>");
		
		try {
			no = Integer.parseInt(scan.nextLine());
		}catch(InputMismatchException e) {
			System.out.println("Warning : Input Number plz!");
			continue;
		}catch(Exception e) {
			System.out.println("Warning : Input Number plz!");
			continue;
		}
		   
		if(no >= 1 && no <= 2) {
			flag = true;
		}else {
			System.out.println("�ٽ� �Է����ּ���!(1~2)");
			continue;
		}
   }//end of while
	   
	   //2. ��¹� ��������
	   list = DBController.studentSort(no);
	   
	   if(list.size() <= 0) {
			System.out.println("nothing to sort");
			return;
		}
	
		for(StudentDataSub data: list) {
			System.out.println(data.toString());
		}
		
		return;
	}
   
   	  //���ϸ�ġ ó���Լ�
      private static boolean patternCheck(String patternData, int patternType) {
         String filter = null;
         
         switch(patternType) {
         case NAME : filter = "^[��-�R]{2,5}$"; break;
         case BIRTHDAY : filter ="^(19[0-9][0-9]|20\\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$"; break;
         }
         
         Pattern pattern = Pattern.compile(filter);
           Matcher matcher = pattern.matcher(patternData);
         return matcher.matches();
      }


   private static int selectMenu() {
      boolean flag =false;
      int no = 0;
      
      while(!flag) {
    	  
         System.out.println("==================================================");
         System.out.println("1. �Է� 2. ��ȸ 3. ���� 4. ���� 5. ��� 6. ���� 7. ����");
         System.out.println("==================================================");
         System.out.print("��ȣ���� >>");
         
         try {
            no = Integer.parseInt(scan.nextLine());
         }catch(InputMismatchException e) {
            System.out.println("�ٽ� �Է����ּ���!");
            continue;
         }catch(Exception e) {
            System.out.println("�ٽ� �Է����ּ���!");
            continue;
         }
         
         if(no >= 1 && no <= 7) {
            flag = true;
         }else {
            System.out.println("�ٽ� �Է����ּ���!(1~7)");
            continue;
         }
         
      }//end of while
      
      return no;
   }

}
