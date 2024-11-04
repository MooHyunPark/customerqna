
<h1 align="center">
   질의 응답 익명 게시판
</h1>





# Contents

## What is?

<p align="center">익명으로 질의 응답을 작성할 수 있는 간단한 게시판입니다.</p>
<p align="center">원하는 닉네임과 비밀번호를 적고 게시글, 댓글을 작성할 수 있으며</p>
<p align="center">게시글을 관리할 수 있는 관리자 기능도 포함되어있습니다.</p>






## Key Features

<br><br>
<p align="center">
  메인화면은 아래와 같이 구성되어 있습니다.
</p>
<br><br><br><br><br><br>
<p align="center"><img src="https://github.com/user-attachments/assets/41d70df2-3011-464b-a8e0-837bd7e4fd92" width="900"></p>
<p align="center">
  게시글의 작성자 닉네임, 제목, 작성한 시간, 댓글 개수, 관리자 답변 여부 등을 한눈에 알 수 있도록 구성하였습니다.
</p>

<br><br><br><br><br><br>


<p align="center"><img src="https://github.com/user-attachments/assets/9ba0d1f0-e146-45a3-ae1a-14a9a858c130" width="900"></p>
<p align="center">
  특정 게시글을 간편하게 찾을 수 있도록 검색 및 필터 기능을 함께 추가하였습니다.
</p>
<br><br><br><br><br><br>


<p align="center"><img src="https://github.com/user-attachments/assets/59e41933-81a8-482f-88db-d901fe14ca53" width="500"></p>
<p align="center">
  비밀 게시글을 클릭하게 되면 비밀번호 입력과정을 거치도록 구현하였습니다. (관리자는 제외)
</p>
<br><br><br><br><br><br>


<p align="center"><img src="https://github.com/user-attachments/assets/14f65187-b2a6-4642-b4a2-d2b3626128f2" width="900"></p>
<p align="center">
  댓글 또한 익명으로 작성할 수 있으며 관리자의 댓글은 '관리자' 라고 출력될 수 있도록 구성해보았습니다.
</p>
<br><br><br><br><br><br>


<p align="center"><img src="https://github.com/user-attachments/assets/d14a1575-6e17-432f-bc9a-37859602daf1" width="900"></p>
<p align="center">
  게시글 작성시 첨부파일을 업로드 할 수 있으며, 해당 게시글의 파란색 첨부파일링크를 클릭할 시 해당 파일을 다운로드 할 수 있습니다.
</p>
<br><br><br><br><br><br>


<p align="center"><img src="https://github.com/user-attachments/assets/e7d97ace-22db-48d4-8002-cf8bb859e5db" width="900"></p>
<p align="center">
  관리자로 접속 시, 특정 게시글과 댓글의 수정 및 삭제를 비밀번호 입력과정 없이 진행할 수 있습니다.
</p>
<br><br><br><br><br><br>





## other components
<br><br><br>
<p align="center"><img src="https://github.com/user-attachments/assets/f7b37731-b50e-495b-b85b-67976dc48528" width="700"></p>
<p align="center">
  게시글 작성페이지입니다. 관리자로 접속하였을 때는 유저이름과 비밀번호 입력 양식을 생략하고 게시글을 작성합니다.
</p>
<br><br><br><br><br><br>

<p align="center"><img src="https://github.com/user-attachments/assets/f64c2625-5cd9-40bc-bc90-998322fb5a8a" width="700"></p>
<p align="center">
  메인의 게시글 목록 페이지에 구성되어 있는 자주 묻는 질문 페이지 입니다.
</p>
<br><br><br>
<p align="center"><img src="https://github.com/user-attachments/assets/aec5f01e-8621-454f-b194-7658deb07d9e" width="700"></p>
<p align="center"><img src="https://github.com/user-attachments/assets/fba626db-757e-4a68-a9b7-767affd11270" width="700"></p>
<p align="center"><img src="https://github.com/user-attachments/assets/35236e64-2ce8-418e-af09-b6f753407cbc" width="700"></p>
<p align="center">
  관리자로 로그인 시 자주 묻는 질문 목록을 추가 및 삭제할 수 있도록 구성하였습니다.
</p>
<br><br><br><br><br><br>












<p align="center">
  <h2>Built With</h2>
   
   Development
   <br><br>
    <img src="https://img.shields.io/badge/H2DB-%234479A1?labelColor=black">
    <img src="https://img.shields.io/badge/Eclipse-2C2255?logo=eclipse&logoColor=white">
    <img src="https://img.shields.io/badge/Spring%20boot-%236DB33F?logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/javascript-%23F7DF1E?logo=javascript&logoColor=black">
    <img src="https://img.shields.io/badge/html-%23E34F26?logo=html5&logoColor=black">
 


Enviroment
<br><br>
<img src="https://img.shields.io/badge/VSCode-0078D4?logo=visual%20studio%20code&logoColor=white">
<img src="https://img.shields.io/badge/apachetomcat-%23F8DC75?logo=apachetomcat&logoColor=black">
<img src="https://img.shields.io/badge/git-%23F05032?logo=git&logoColor=black">






## Development setup

테이블 논리 구성, 작업 프로젝트 생성, 공유 repository 생성

H2DB 라이브러리 추가 및 활용(pom.xml파일과 properties 파일 수정,sql 파일을 프로젝트에 추가)

1. 테이블 정의 : 기본키(Pk), 외래키(Fk), 널 허용 여부(Null/Not Null), 자동 증가(Auto Increment)설정을 포함한 테이블 정의.
2. 테이블 생성 : customerqna, comment, filedata, question 테이블 생성 및 외래키를 구성하고 테스트 용 데이터를 추가.
<p align="center"><img src="https://github.com/user-attachments/assets/b1cd773c-52c1-46b7-a114-15dc377726ad" width="700"></p>
3. Test Mapper를 작성한 뒤 h2-console실시간 입출력 테스트


---
## ERD 구성

<img src="https://github.com/user-attachments/assets/f0f7ced3-da8c-47d2-b5fb-5f589e7c933b" width="600" height="700">

---


## work process

- 익명 게시판을 기획하고, 사용자 요구 사항을 분석하여 기능을 정의
- 테스트 과정을 간편화 하기 위해 H2DB를 사용하여 데이터를 저장하고, 서버 시작 시 필요한 테이블들을 자동으로 생성하도록 초기화 스크립트를 작성
- 기본적인 게시글 작성 및 댓글 기능을 구현한 후, 비밀번호를 통한 수정 및 삭제 기능을 추가
- 관리자의 역할을 구분하여, 관리자로 로그인 후 게시글이나 댓글을 작성할 때 > 아이디와 비밀번호를 입력하는 절차를 생략하도록 구현
- '관리자 답변 완료' 표시 기능을 통해 사용자에게 게시글 상태를 명확하게 전달할 수 있도록 구성
- 자주 묻는 질문 페이지를 구성하고, 관리자가 내용을 추가하거나 삭제할 수 있는 기능을 구현

## FAQ

<h3>자주 묻는 질문?</h3>
<h4>굳이 H2DB를 사용한 이유가 무엇인가요?</h4>

<h6>당연히 실제 제작하는 프로그램이나 web 사이트의 경우 일반적인 데이터베이스를 사용하는 것이 맞습니다.
하지만 해당 게시판은 실제 배포를 위함이 아닌, 개인적인 역량을 강화하는 목적으로 만들었기 때문에 기존의 Web 프로젝트에서 사용한 MySQL대신 
테스트가 간편한 H2DB를 사용하게 되었습니다. <br>
(테스트를 진행한 뒤, 서버를 재시작했을 때 sql파일에 작성된 내용을 제외한 나머지 데이터들은 초기화되기 때문에 빠른 테스트를 진행할 수 있습니다.)
</h6>
  

   

    
## Authors

[![GitHub stats](https://github-readme-stats.vercel.app/api?username=MooHyunPark)](https://github.com/MooHyunPark)


