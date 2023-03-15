### basicBoard

<hr/>


#개발환경

|분류|사용|
|frontEnd|HTML, CSS, JavaScript, BootStrap, jQuery|
|backend|Java 17, Spring Boot 2.7.8, MyBatis|
|DB|MySQL|
|IDE|Eclipse|



#구현기능
1. Spring Security를 이용한 회원가입, 로그인
2. CRUD(첨부파일을 포함한 게시글 작성/조회/수정/삭제)
3. 검색과 페이징처리


#주요기능
1. 회원가입
![image](https://user-images.githubusercontent.com/85056691/225222781-09696cad-9276-4572-b33f-9745b28ccfe0.png)

정규표현식을 이용한 유효성 검증
Spring Security의 bcryptpasswordencoder를 이용, 암호화된 비밀번호 사용

2. 로그인
![image](https://user-images.githubusercontent.com/85056691/225222947-8f7d841e-a362-4259-810f-b8a02554db76.png)
Spring Security를 이용한 보안 로그인


3. 메인페이지
![image](https://user-images.githubusercontent.com/85056691/225223115-2d63f3c6-5a2a-4fee-9080-e4d8f0984de6.png)
페이징처리
- 한 페이지에 게시글 10개
- 페이징바에서 페이지 10개
- 제목, 작성자 검색

4. 글쓰기 페이지
![image](https://user-images.githubusercontent.com/85056691/225223697-523eff5b-69f8-485e-818b-34f8bef2758c.png)
제목, 내용 작성 시 byte수 표시
입력 크기 제한
단일첨부파일




