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


tr:hover {
	background-color: #f0f0f0; /* 마우스 가져갔을 때 옅은 회색 */
}

th, td {
	padding: 10px;
	border: 1px solid #ddd; /* 모든 셀에 세로줄 추가 */
}

a {
	text-decoration: none; /* 밑줄 제거 */
	color: black; /* 검은색으로 변경 */
}

th {
	background-color: lightgreen;    
   	color: white; 
    height: 12px; /* 헤더의 높이를 30px로 설정 */
    line-height: 12px; /* 텍스트의 수직 정렬 */
}



th:first-child, td:first-child {
	width: 70px; /* 첫 번째 열의 너비 설정 */
}

th:nth-child(2), td:nth-child(2) {
	min-width: 100px;
}

th:nth-child(3), td:nth-child(3) {
	width: 500px; /* 세 번째 열의 너비 설정 */
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

.top {
	display: flex;
	align-content: center;
}

.top .link {
	justify-content: flex-end;
	margin-top: 33px;
	margin-left: 200px;
}
</style>
</head>
<body>
	<div class="main">
		<div class="top">
			<c:if test="${not empty sessionScope.admin}">
				<h1 style="margin-left: 380px">게시글 목록</h1>
			</c:if>
			<c:if test="${empty sessionScope.admin}">
				<h1 style="margin-left: 330px">게시글 목록</h1>
			</c:if>
			
			
			<div class="link">
				<c:choose>
					<c:when test="${not empty sessionScope.admin}">
						<div style="margin-left: -40px;">
							<span>(관리자 모드 활성 중)</span>
							<a href="/logout" style="font-weight: bold;">로그 아웃</a>	
						</div>
						
					</c:when>
					<c:otherwise>
						<a href="/login" style="margin-left: 30px;">관리자 로그인</a>
					</c:otherwise>
				</c:choose>
			</div>

		</div>

		<div class="sorting">
			<div>
				<label for="sort">정렬 기준:</label> <select id="sort" name="sort"
					onchange="searchArticles()">
					<option value="created_at"
						<c:if test="${sort == 'created_at'}">selected</c:if>>최신순</option>
					<option value="old_order"
						<c:if test="${sort == 'old_order'}">selected</c:if>>오래된순</option>
					<option value="views"
						<c:if test="${sort == 'views'}">selected</c:if>>조회순</option>
					<option value="comments"
						<c:if test="${sort == 'comments'}">selected</c:if>>댓글순</option>
					<option value="adminComment"
						<c:if test="${sort == 'adminComment'}">selected</c:if>>관리자 답글 없는 것만</option>
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
				onkeypress="if(event.key==='Enter') searchArticles()"
				<c:if test="${query != 'none'}">value="${query}"</c:if>>

			<button onclick="searchArticles()">검색</button>
		</div>
		<table>
			<tr>
				<th>글 번호</th>
				<th>작성자</th>
				<th>제목</th>
				<th>조회수</th>
				<c:if test="${sessionScope.admin != null}">
					<th>삭제</th>
				</c:if>
			</tr>
			<c:forEach var="qna" items="${qnaList}">
				<tr style="cursor: pointer;">
					<td onclick="checkSecure('${qna.articleId}')">${qna.articleId}</td>
					<td onclick="checkSecure('${qna.articleId}')">${qna.username}</td>
					<td onclick="checkSecure('${qna.articleId}')">
							<c:if test="${qna.secure == true}">&#x1F512;</c:if> 
							${qna.title}<c:if
							test="${qna.comments > 0}">(${qna.comments})</c:if>
							<c:if test="${qna.adminComment == true}">(관리자 답변완료)</c:if></td>
					<td onclick="checkSecure('${qna.articleId}')">${qna.views}</td>
					<c:if test="${sessionScope.admin != null}">
						<td><a href="#" style="color: red;"
							onclick="event.stopPropagation(); confirmDelete('${qna.articleId}')">x</a>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</table>


		<div class="pagination">
			<c:forEach var="i" begin="1" end="${totalPages}">
				<c:if
					test="${(currentPage <= 4 && i <= 10) || (i >= currentPage - 4 && i <= currentPage + 5)}">
					<a
						href="/qna?page=${i}&category=${category}&query=${query}&sort=${sort}"
						class="${i == currentPage ? 'active' : ''}">${i}</a>
				</c:if>
			</c:forEach>
		</div>


	</div>
	<script>
		function confirmDelete(articleId) {
			const confirmation = confirm(articleId + "번 게시글을 삭제하시겠습니까?");
			if (confirmation) {
				window.location.href = "/adminDelete?articleId=" + articleId;
			}
		}
		function checkSecure(articleId) {
			window.location.href = `/qna/` + articleId;
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
