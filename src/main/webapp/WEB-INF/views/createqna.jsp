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
            width: 100%; /* 모든 입력 요소의 가로 길이를 100%로 설정 */
            margin-top: 50px;
            margin-bottom: 50px;
        }
        form {
            display: flex;
            flex-direction: column;
            width: 600px;
        }
        label {
            margin-top: 10px;
        }
        input, textarea {
            margin-top: 5px;
            padding: 10px;
            font-size: 16px;
        }
        .submitBtn {
            margin-top: 20px;
            padding: 10px;
            font-size: 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
            width: 300px;
            margin-left: 150px;
        }
    </style>
</head>
<body>
<form id="qnaForm">
    <h3>게시글 작성 페이지 <c:if test="${sessionScope.admin != null}">(관리자 모드)</c:if></h3>
    <label for="title">제목</label>
    <input type="text" id="title" name="title" required>
    <label for="content">내용</label>
    <textarea id="content" name="content" rows="4" style="height: 150px;" required></textarea>
    <c:if test="${sessionScope.admin == null}">
        <label for="username">유저이름</label>
        <input type="text" id="username" name="username" required>
        <label for="password">비밀번호</label>
        <input type="password" id="password" name="password" required>
    </c:if>
    <c:if test="${sessionScope.admin != null}">
        <input type="hidden" id="username" name="username" value="관리자" required>
        <input type="hidden" id="password" name="password" value="관리자" required>
    </c:if>
    <label for="secure" style="margin-bottom: 10px;">비밀글 여부</label>
    <select id="secure" name="secure" style="height: 30px;" required>
        <option value="false">공개</option>
        <option value="true">비공개</option>
    </select>
    <div id="fileUploads" style="max-height: 150px; margin-top: 20px; overflow-y: auto; border: 1px solid #ccc; padding: 10px;">
    	<input type="file" name="file" multiple />
    </div>
    <button type="button" class="submitBtn"onclick="submitForm()">제출</button>
</form>

<script>
    function submitForm() {
        const form = document.getElementById('qnaForm');
        const title = document.getElementById('title').value.trim();
        const content = document.getElementById('content').value.trim();
        const username = document.getElementById('username') ? document.getElementById('username').value.trim() : '관리자';
        const password = document.getElementById('password') ? document.getElementById('password').value.trim() : '관리자';
        const secure = document.getElementById('secure').value;
		
        if (title === "") {
        	alert('제목을 작성해주세요.');
            return;
        }
        if (content === "") {
        	alert('내용을 작성해주세요.');
            return;
        }
        if (username === "") {
        	alert('닉네임을 작성해주세요.');
            return;
        }
        if (password === "") {
        	alert('비밀번호를 작성해주세요.');
            return;
        }
        
        const formData = new FormData(form);
        fetch('/create', {
            method: 'POST',
            body: formData
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
