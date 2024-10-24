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
		
	<form id="qnaForm" method="post">
	
		<h3>게시글 수정 페이지</h3>
	
	
		<input type="hidden" id="articleId" name="articleId" value="${ qna.articleId }">
	
		<label for="title">제목</label> <input type="text" id="title"
			name="title" required> <label for="content">내용</label>
		<textarea id="content" name="content" rows="4" required></textarea>

		<label for="username">유저이름</label> <input type="text" id="username"
			name="username" required> <label for="password">비밀번호</label>
		<input type="password" id="password" name="password" required>.

		<label for="secure">비밀글 여부</label> <select id="secure"
			name="secure" required>
			<option value="false">공개</option>
			<option value="true">비공개</option>
		</select>

		<button type="submit">제출</button>
	</form>

	
</body>
</html>
