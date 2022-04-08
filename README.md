## Java(Eclipse)와 DB(Sql)을 이용해 만든 학생성적프로그램
학생들의 성적을 보다 간단하고, 편하게 관리하여 DB에 남기는 프로그램

학생 성적프로그램의 메뉴
1. 입력 2. 조회 3. 삭제
4. 수정 5. 출력 6. 정렬
7. 종료

## StudentDBU(DB 유틸리티) 클래스 주요 기능

##### 1. JAVA 와 DB 연결하는 Connection
```
 public static Connection getConnection()  {
      Connection con = null;
      FileReader fr = null;
```

##### 2. Mysql ID와 Password보이지 않게 설정
```
      String DRIVER = properties.getProperty("DRIVER");
         String URL = properties.getProperty("URL");
         String userID = properties.getProperty("userID");
         String userPassword = properties.getProperty("userPassword");
```

## DBController 클래스 주요 기능

##### 1. PreparedStatement을 사용하여 코드의 안정성 향상
```
  PreparedStatement ps = null;
```
##### 2. 예외처리을 정상 작동
```
catch (SQLException e) {

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
```
## StudentDataSub 주요 기능

##### 1. 오버라이딩 hashCode , equals, compareTo, toString

hashCode = name
equals = name
compareTo(정렬) = grade
toString = Text
