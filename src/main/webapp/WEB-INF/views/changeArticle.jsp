<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
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
	margin-left: 220px;
}

#btn {
    width: 150px;
    text-align: center !important;
    margin: 0 auto; /* Added to center the button */
    margin-top: 15px;
}

</style>
</head>
<body>
    <form id="qnaForm" method="post">
        <h3>게시글 수정 페이지</h3>
        <input type="hidden" id="articleId" name="articleId" value="${qna.articleId}">
        <label for="title">제목</label>
        <input type="text" id="title" value="${qna.title}" name="title" required>
        <label for="content">내용</label>
        <textarea id="content" name="content" style="resize: none; height: 250px;" rows="4" required>${qna.content}</textarea>
        <label for="username">유저이름</label>
        <input type="text" id="username" value="${qna.username}" name="username" required>
        <label for="password">비밀번호</label>
        <input type="password" id="password" value="${qna.password}" name="password" required>
        <label for="secure" style="margin-bottom: 5px;">비밀글 여부</label>
        <select id="secure" style="height: 40px;" name="secure" required>
            <option value="false" <c:if test="${qna.secure == false}">selected</c:if>>공개</option>
            <option value="true" <c:if test="${qna.secure == true}">selected</c:if>>비공개</option>
        </select>
        <button type="submit" id="btn">제출</button>
    </form>
</body>

</html>
