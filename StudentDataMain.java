package com.mire.score;
//¼Ò½º ¿©´ÝÀÌ ctrl + shift + ¿À¸¥ÂÊ Å°ÆÐµå À§ "/"
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
         int no = selectMenu(); //¸Þ´º¼±ÅÃ
         switch(no) {
         case INSERT: insertStudent(); break; //»ðÀÔ
         case SEARCH: searchStudent(); break; //°Ë»ö
         case DELETE: deleteStudent(); break; //»èÁ¦
         case UPDATE: updateStudent(); break; //¼öÁ¤
         case PRINT:  printStudent(); break;  //Ãâ·Â
         case SORT:     sortStudent(); break; //Á¤·Ä
         case EXIT: System.out.println("ÇÁ·Î±×·¥ Á¾·á"); //Á¾·á
                  scan.nextLine(); flag = true; break;
         default : System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä"); break;
         }
      }
   }
   //ÀÔ·Â : ¹Ý ¹øÈ£, ÀÌ¸§, ¼ºº°, »ý³â¿ùÀÏ, ±¹¾î, ¼öÇÐ, ¿µ¾î ¸¦ ÀÔ·ÂÇÑ´Ù.
   private static void insertStudent() {
	  //ÀÔ·Â ¹Þ¾Æ¾ßÇÒ °Í
      int cnum = 0;
      String name = null;
      String gender = null;
      String birthday = null;
      int kor = 0;
      int math = 0;
      int eng = 0;
      
      //¹Ý ¹øÈ£
      while(true) {
         System.out.print("¹Ý ¹øÈ£ ÀÔ·Â(30xx) >>");
         cnum = scan.nextInt();
         
         if(cnum >= 3000 && cnum <= 3099) {
            break;
         }else {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
         }
      }
      //ÀÌ¸§
      while(true) {
         System.out.print("ÀÌ¸§ ÀÔ·Â >>");
         scan.nextLine();
         name = scan.nextLine();
         
         if(patternCheck(name, NAME)) {
            break;
         }else {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
         }
      }
      //¼ºº°
      while(true) {
         System.out.print("¼ºº°(³²ÀÚ or ¿©ÀÚ) ÀÔ·Â >>");
         gender = scan.nextLine();
         
         if(gender.equals("³²ÀÚ") || gender.equals("¿©ÀÚ")) {
            break;
         }else {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
         }
      }
      
      //»ý³â¿ùÀÏ
      while(true) {
         System.out.print("»ý³â¿ùÀÏ(1995-03-09)10ÀÚ ÀÔ·Â>>");
         birthday = scan.nextLine();
         
         if(patternCheck(birthday, BIRTHDAY)) {
            break;
         }else {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
         }
      }
      //±¹¾î
      while(true) {
         System.out.print("±¹¾î Á¡¼ö(0~100) ÀÔ·Â>>");
         kor = scan.nextInt();
         
         if(kor >= 0 && kor <= 100) {
            break;
         }else {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
         }
      }
      //¼öÇÐ
      while(true) {
         System.out.print("¼öÇÐ Á¡¼ö(0~100) ÀÔ·Â>>");
         math = scan.nextInt();
         
         if(math >= 0 && math <= 100) {
            break;
         }else {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
         }
      }
      //¿µ¾î
      while(true) {
         System.out.print("¿µ¾î Á¡¼ö(0~100) ÀÔ·Â>>");
         eng = scan.nextInt();
         
         if(eng >= 0 && eng <= 100) {
            break;
         }else {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
         }
      }
      scan.nextLine();
      //»ðÀÔÇÒ ¸ðµ¨ »ý¼º
      StudentDataSub studentDataSub = new StudentDataSub( cnum, name, gender, birthday, kor, math, eng);
      //¸Þ¼Òµå¼±¾ðÇÑ ÃÑÁ¡ Æò±Õ µî±ÞÀ» »ç¿ëÇÏ±â
      studentDataSub.totalScore();
      studentDataSub.avrScore();
      studentDataSub.gradeScore();
      
      int returnvalue = DBController.StudentInsert(studentDataSub);
      
      if(returnvalue != 0) {
    	  System.out.println(studentDataSub.getName()+"´Ô »ðÀÔ ¼º°ø");
      }else {
    	  System.out.println(studentDataSub.getName()+"´Ô »ðÀÔ ½ÇÆÐ");
      }
      
   }
   
   //°Ë»ö (ÀÌ¸§, ¼ºº°)
   private static void searchStudent() {
	   final int NAME_NUM = 1, GENDER_NUM = 2, EXIT = 3;
      List<StudentDataSub> list = new ArrayList<StudentDataSub>();
      //°Ë»öÇÒ ³»¿ëÀ» ¿äÃ»(ÀÌ¸§°ú ¼ºº°)
      String name = null;
      String gender = null;
      
      String searchData = null;
      int number = 0;
      int no = 0;
      boolean flag = false;      
      
      //°Ë»ö ¸Þ´º
      no = studentMenu();
      
      switch(no) {
      case NAME_NUM :
    	  while(true) {
				System.out.print("Ã£°íÀÚ ÇÏ´Â ÀÌ¸§ ÀÔ·Â>>");
				name = scan.nextLine();
				
				if(patternCheck(name, NAME)) {
					break;
				}else {
					System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				}
    	  }
			searchData = name;
			number = NAME_NUM;
			
			break;
			
      case GENDER_NUM :
    	  while(true) {
				System.out.print("¼ºº°(³²ÀÚ or ¿©ÀÚ) ÀÔ·Â >>");
				gender = scan.nextLine();
				
				if(gender.equals("³²ÀÚ") || gender.equals("¿©ÀÚ")) {
					break;
				}else {
					System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				}
			}
			searchData = gender;
			number = GENDER_NUM;
			break;

      case EXIT :
			System.out.println("°Ë»ö Ãë¼Ò ¿Ï·á");
			flag = true;
			break;
		}
		if(flag) {			
			return; 
		} 	
			list = DBController.StudentSearch(searchData, number);
			
			if(list.size() <= 0) {
				System.out.println(searchData +"Ã£Áö ¸øÇß½À´Ï´Ù.");
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
			System.out.println("1. ÀÌ¸§ 2. ¼ºº° 3. Á¾·á");
			System.out.println("*******************");
			System.out.print("¹øÈ£¼±ÅÃ >>");
			
			try {
				no = Integer.parseInt(scan.nextLine());
			}catch(InputMismatchException e) {
				System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä!");
				continue;
			}catch(Exception e) {
				System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä!");
				continue;
			}
			
			if(no >= 1 && no <= 3) {
				flag = true;
			}else {
				System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä!(1~3)");
				continue;
			}
			
		}//end of while
		return no;
	}
   
   //»èÁ¦ (ÀÌ¸§)
   private static void deleteStudent() {
	  final int NAME_NUM = 1;
	  String name = null;
	  String deleteData = null;
	  int number = 0;
	  int resultNumber = 0;
	  
	  while(true) {
		  System.out.print("Ã£°íÀÚ ÇÏ´Â ÀÌ¸§ ÀÔ·Â>>");
			name = scan.nextLine();

			if(patternCheck(name, NAME)) {
				break;
			}else {
				System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
			}
	  }
	  deleteData = name;
	  //È®Àå¼ºÀ» À§ÇØ¼­
	  number = NAME_NUM;
	  
	  resultNumber = DBController.StudentDelete(deleteData, number);
		
		 if(resultNumber != 0) {
			 System.out.println(name+" ÀÌ¸§ ·¹ÄÚµå°¡ »èÁ¦¿Ï·áÇß½À´Ï´Ù.");
		 }else {
			 System.out.println(name+" »èÁ¦ ½ÇÆÐÇÏ¿´½À´Ï´Ù.");
		 }
      
   }
   
   //¼öÁ¤(ÀÌ¸§) -> ³»¿ë¼öÁ¤(±¹¾î, ¼öÇÐ, ¿µ¾î ÃÑÁ¡, Æò±Õ, µî±Þ)
   private static void updateStudent() {
    
	   final int NAME_NUM = 1;
	   List<StudentDataSub> list = new ArrayList<StudentDataSub>();
	   //°Ë»öÇÒ ³»¿ëÀ» ¼±ÅÃ¿äÃ»(ÀüÈ­¹øÈ£, ÀÌ¸§, ¼ºº°)
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
	   
	   //¼öÁ¤ÇÒ ÀÌ¸§ °Ë»ö
	   while(true) {
		   System.out.print("¼öÁ¤ÇÏ°íÀÚ ÇÏ´Â ÀÌ¸§ ÀÔ·Â >>");
		   name = scan.nextLine();
		   
		   if(patternCheck(name, NAME)) {
			   break;
		   }else {
			   System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
		   }
	   }
	   searchData = name;
	   number = NAME_NUM;
	   
	   list = DBController.StudentSearch(searchData, number);
	   
	   if(list.size() <= 0) {
			System.out.println(searchData +"Ã£Áö ¸øÇß½À´Ï´Ù.");
			return;
		}
	   
	   StudentDataSub data_buffer = null;
	   for(StudentDataSub data : list) {
		   System.out.println(data);
		   data_buffer = data;
	   }
	   
	   //2. ÀÌ¸§ÀÌ ÀÖÀ¸¸é ¼öÁ¤ÇÒ ÀÛ¾÷À» ¹Þ¾Æ¼­ ¼öÁ¤¿äÃ»
	   while(true) {
		   System.out.print("["+data_buffer.getKor()+"] \n¼öÁ¤ÇÒ ±¹¾î Á¡¼ö ÀÔ·Â >>");
		   kor = scan.nextInt();
		   
		   if(kor >= 0 && kor <= 100) {
			   break;
		   }else {
			   System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
		   }
	   }
		   
	   while(true) {
		   System.out.print("["+data_buffer.getMath()+"] \n¼öÁ¤ÇÒ ¼öÇÐ Á¡¼ö ÀÔ·Â >>");
		   math = scan.nextInt();
		   
		   if(math >= 0 && math <= 100) {
			   break;
		   }else {
			   System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
		   }
	   }
		   
	   while(true) {
		   System.out.print("["+data_buffer.getEng()+"] \n¼öÁ¤ÇÒ ¿µ¾î Á¡¼ö ÀÔ·Â >>");
		   eng = scan.nextInt();
		   
		   if(eng >= 0 && eng <= 100) {
			   break;
		   }else {
			   System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
		   }
	   }
	   scan.nextLine();
	   
	   StudentDataSub studentDataSub = new StudentDataSub();
	   //ÀÔ·ÂÇÑ kor, math, engÀ» studentDataSub¿¡ °ª ³Ö±â
	   studentDataSub.setKor(kor);
	   studentDataSub.setMath(math);
	   studentDataSub.setEng(eng);
	   //ÃÑÁ¡, Æò±Õ, ¼ºÀû ÇÔ¼ö °¡Á®¿À±â
	   studentDataSub.totalScore();
	   studentDataSub.avrScore();
	   studentDataSub.gradeScore();
	   
	   total = studentDataSub.getTotal();
	   avr = studentDataSub.getAvr();
	   grade =studentDataSub.getGrade();
	   
		//3. °á°ú°ª È®ÀÎ
		resultNumber = DBController.StudentUpdate(name, kor, math, eng, total, avr, grade);

		if(resultNumber != 0) {
			System.out.println(name + "Á¡¼ö ¼öÁ¤¿Ï·áÇß½À´Ï´Ù.");
		}else {
			System.out.println(name + "Á¡¼ö ¼öÁ¤½ÇÆÐÇÏ¿´½À´Ï´Ù.");
		}
		
		return;
   }
   //Ãâ·Â
   private static void printStudent() {
      List<StudentDataSub> list = new ArrayList<StudentDataSub>();
      
      list = DBController.StudentDataSelect();
      
      if(list.size() <= 0) {
         System.out.println("Ãâ·ÂÇÒ ÀüÈ­¹øÈ£ºÎ ³»¿ëÀÌ ¾ø¾î¿ä");
         return;
      }
      
      for(StudentDataSub data: list) {
         System.out.println(data.toString());
      }
      
   }
   
   //Á¤·Ä = Á¤·ÄÀº µÇµµ·Ï ÀÚ¹Ùº¸´Ù DB·Î ÇÏ´Â°ÍÀÌ ºü¸£´Ù. (´õ Æ¯È­ µÇ¾îÀÖ±â ¶§¹®ÀÌ°í °á°ú°ª¸¸ ¹ÞÀ¸¸é µÇ±â ¶§¹®¿¡)
   private static void sortStudent() {
	   List<StudentDataSub> list = new ArrayList<StudentDataSub>();
	   int no = 0;
	   boolean flag = false;
	   while(!flag) {
		   
		//1. Á¤·Ä¹æ½Ä(¿À¸§Â÷¼ø, ³»¸²Â÷¼ø)
		System.out.println("[µî±Þ] 1. ¿À¸§Â÷¼ø, 2. ³»¸²Â÷¼ø");
		System.out.print("Á¤·Ä¹æ½Ä ¼±ÅÃ>>");
		
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
			System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä!(1~2)");
			continue;
		}
   }//end of while
	   
	   //2. Ãâ·Â¹® °¡Á®¿À±â
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
   
   	  //ÆÐÅÏ¸ÅÄ¡ Ã³¸®ÇÔ¼ö
      private static boolean patternCheck(String patternData, int patternType) {
         String filter = null;
         
         switch(patternType) {
         case NAME : filter = "^[°¡-ÆR]{2,5}$"; break;
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
         System.out.println("1. ÀÔ·Â 2. Á¶È¸ 3. »èÁ¦ 4. ¼öÁ¤ 5. Ãâ·Â 6. Á¤·Ä 7. Á¾·á");
         System.out.println("==================================================");
         System.out.print("¹øÈ£¼±ÅÃ >>");
         
         try {
            no = Integer.parseInt(scan.nextLine());
         }catch(InputMismatchException e) {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä!");
            continue;
         }catch(Exception e) {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä!");
            continue;
         }
         
         if(no >= 1 && no <= 7) {
            flag = true;
         }else {
            System.out.println("´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä!(1~7)");
            continue;
         }
         
      }//end of while
      
      return no;
   }

}
