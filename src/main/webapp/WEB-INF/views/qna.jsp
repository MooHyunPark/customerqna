<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>고객 센터</title>
    <style type="text/css">
        table {
            width: 70%;
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
        body, html {
            height: 100%;
            margin: 0;
            justify-content: center;
            align-items: center;
            text-align: center;
            display: flex;
            flex-direction: column;
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
    </style>
</head>
<body>
    <h1>게시글 목록</h1>

    <div class="sorting">
        <label for="sort">정렬 기준:</label>
        <select id="sort" name="sort" onchange="sortArticles()">
            <option value="created_at" <c:if test="${sort == 'created_at'}">selected</c:if>>최신순</option>
            <option value="views" <c:if test="${sort == 'views'}">selected</c:if>>조회수순</option>
            <option value="comments" <c:if test="${sort == 'comments'}">selected</c:if>>댓글수순</option>
        </select>
    </div>

    <a href="/create">게시글 작성</a>
    
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
            <a href="/qna?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
        </c:forEach>
    </div>

    <script>
        function checkSecure(articleId) {
            window.location.href = `/qna/` + articleId;
        }

        function sortArticles() {
            const sortValue = document.getElementById('sort').value;
            window.location.href = `/qna?sort=` + sortValue;
        }
    </script>
</body>
</html>
