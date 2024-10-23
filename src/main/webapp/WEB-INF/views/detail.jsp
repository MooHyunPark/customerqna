<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>QNA 상세 보기</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            flex-direction: column;
            text-align: center;
        }
        .content {
            border: 1px solid #ddd;
            padding: 20px;
            width: 50%;
        }
        h1, h3 {
            margin: 10px 0;
        }
        a {
            margin-top: 20px;
            text-decoration: none; /* 밑줄 제거 */
            color: black; /* 검은색으로 변경 */
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="content">
        <h1>QNA 상세 보기</h1>
        <h3>제목: ${qna.title}</h3>
        <h3>유저이름: ${qna.username}</h3>
        <h3>내용: ${qna.content}</h3>
        <a href="/qna">메인화면으로 이동</a>
    </div>
</body>
</html>
