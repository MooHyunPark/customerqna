<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 체크</title>
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

form {
	display: flex;
	flex-direction: column;
	width: 300px;
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

a {
	margin-top: 20px;
	text-decoration: none; /* 밑줄 제거 */
	color: black; /* 검은색으로 변경 */
	font-weight: bold;
}
</style>
</head>
<body>
	<h1>비밀번호 체크</h1>
	<form id="passwordForm">
		<label for="password">비밀번호</label> <input type="password"
			id="password" name="password" required>
		<button type="button" onclick="checkPassword()">제출</button>
	</form>
	<a href="/qna">메인화면으로 이동</a>

	<script>
		function checkPassword() {
			const inputPassword = document.getElementById('password').value;
			const qnaPassword = '${qna.password}';

			if (inputPassword == qnaPassword) {
				window.location.href = '/page1';
			} else {
				alert('비밀번호가 일치하지 않습니다.');
			}
		}
	</script>
</body>
</html>
