<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객 센터</title>
<style type="text/css">
.custom-link {
	text-decoration: none; /* 밑줄 제거 */
	color: gray; /* 색깔 변경 */
}

.custom-link:hover {
	color: darkblue; /* 호버 시 색깔 변경 */
}

body, html {
	height: 100%;
	margin: 0;
	display: flex;
	flex-direction: column;
	justify-content: flex-start; /* 상단부터 정렬 */
	align-items: center;
	text-align: center;
	margin-bottom: 100px; /* 최하단 여백 */
}

#searchQuery {
	width: 700px;
}

table {
	width: 100%;
	text-align: center;
	border-collapse: collapse; /* 테이블 셀 경계선을 합침 */
}

th, td {
	padding: 10px;
	border: 1px solid #ddd; /* 모든 셀에 세로줄 추가 */
}

a {
	text-decoration: none; /* 밑줄 제거 */
	color: black; /* 검은색으로 변경 */
}

th:first-child, td:first-child {
	width: 200px; /* 첫 번째 열의 너비 설정 */
}

th:nth-child(2), td:nth-child(2) {
	width: 500px; /* 두 번째 열의 너비 설정 */
}

th:nth-child(3), td:nth-child(3) {
	width: 100px; /* 세 번째 열의 너비 설정 */
}

.sorting {
	display: flex;
	justify-content: space-between;
	width: 100%;
	margin-bottom: 20px;
}

.search-bar {
	display: flex;
	justify-content: space-between;
	width: 100%;
	margin: 20px 0;
}

.search-bar select, .search-bar input {
	margin-right: 10px; /* 좌우 여백 추가 */
}

.pagination {
	margin-top: 20px;
	margin-bottom: 100px;
}

.pagination a {
	margin: 0 10px; /* 좌우 여백 추가 */
	padding: 10px;
	text-decoration: none;
	color: black;
}

.pagination a.active {
	font-weight: bold;
	background-color: #ddd;
}
</style>
</head>
<body>
	<div class="main">
		<h1>게시글 목록</h1>
		<div class="sorting">
			<div>
				<label for="sort">정렬 기준:</label> <select id="sort" name="sort"
					onchange="sortArticles()">
					<option value="created_at"
						<c:if test="${sort == 'created_at'}">selected</c:if>>최신순</option>
					<option value="views"
						<c:if test="${sort == 'views'}">selected</c:if>>조회수순</option>
					<option value="comments"
						<c:if test="${sort == 'comments'}">selected</c:if>>댓글수순</option>
				</select>
			</div>
			<a href="/create" class="custom-link">게시글 작성</a>
		</div>
		<div class="search-bar">
			<select id="searchCategory">
				<option value="title"
					<c:if test="${category == 'title'}">selected</c:if>>제목</option>
				<option value="content"
					<c:if test="${category == 'content'}">selected</c:if>>내용</option>
				<option value="username"
					<c:if test="${category == 'username'}">selected</c:if>>작성자</option>
			</select> <input type="text" id="searchQuery" placeholder="검색어를 입력하세요"
				<c:if test="${query != 'none'}">value="${query}"</c:if>>

			<button onclick="searchArticles()">검색</button>
		</div>
		<table>
			<tr>
				<th>작성자</th>
				<th>제목</th>
				<th>조회수</th>
			</tr>
			<c:forEach var="qna" items="${qnaList}">
				<tr>
					<td><a href="#" onclick="checkSecure('${qna.articleId}')">${qna.username}</a></td>
					<td><a href="#" onclick="checkSecure('${qna.articleId}')">${qna.title}</a></td>
					<td><a href="#" onclick="checkSecure('${qna.articleId}')">${qna.views}</a></td>
				</tr>
			</c:forEach>
		</table>
		<div class="pagination">
			<c:forEach var="i" begin="1" end="${totalPages}">
				<a
					href="/qna?page=${i}&category=${category}&query=${query}&sort=${sort}"
					class="${i == currentPage ? 'active' : ''}">${i}</a>
			</c:forEach>
		</div>
	</div>
	<script>
		function checkSecure(articleId) {
			window.location.href = `/qna/` + articleId;
		}
		function sortArticles() {
			const sortValue = document.getElementById('sort').value;
			window.location.href = `/qna?sort=` + sortValue;
		}
		function searchArticles() {
			const sortValue = document.getElementById('sort').value;
			const category = document.getElementById('searchCategory').value;
			const query = document.getElementById('searchQuery').value;
			window.location.href = `/qna?category=` + category + `&query=`
					+ query + `&sort=` + sortValue;
		}
	</script>
</body>
</html>
