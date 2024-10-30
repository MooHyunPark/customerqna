<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인 페이지</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f0f0f0;
        }
        form {
            display: flex;
            flex-direction: column;
            width: 300px;
            padding: 20px;
            background: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        label {
            margin-top: 10px;
        }
        input {
            margin-top: 5px;
            padding: 10px;
            font-size: 16px;
        }
        button {
            margin-top: 20px;
            padding: 10px;
            font-size: 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        
        h3 {
      		margin-left: 92px;
        }
    </style>
</head>
<body>
    <form id="loginForm" method="post" action="/login">
        <h3>관리자 로그인</h3>
        <label for="username">아이디</label>
        <input type="text" id="username" name="username" required>
        <label for="password">비밀번호</label>
        <input type="password" id="password" name="password" required>
        <button type="submit">로그인</button>
    </form>
</body>
</html>
