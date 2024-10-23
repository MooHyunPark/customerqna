<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>글 작성하기</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        form {
            display: flex;
            flex-direction: column;
            width: 300px;
        }
        label {
            margin-top: 10px;
        }
        input, textarea {
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
    </style>
</head>
<body>
    <form id="qnaForm">
        <label for="title">제목</label>
        <input type="text" id="title" name="title" required>

        <label for="content">내용</label>
        <textarea id="content" name="content" rows="4" required></textarea>

        <label for="username">유저이름</label>
        <input type="text" id="username" name="username" required>

        <label for="password">비밀번호</label>
        <input type="password" id="password" name="password" required>

        <button type="button" onclick="submitForm()">제출</button>
    </form>

    <script>
        function submitForm() {
            const form = document.getElementById('qnaForm');
            const formData = new FormData(form);
            const jsonData = {};

            formData.forEach((value, key) => {
                jsonData[key] = value;
            });

            fetch('/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(jsonData)
            })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
                alert('글이 성공적으로 제출되었습니다.');
                window.location.href = '/qna';
            })
            .catch((error) => {
                console.error('Error:', error);
                alert('글 제출에 실패했습니다.');
            });
        }
    </script>
</body>
</html>
