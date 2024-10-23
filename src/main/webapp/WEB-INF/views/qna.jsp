<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객 센터</title>
<style type="text/css">
div {
	display: flex;
	justify-content: center;
	align-items: center;
	text-align: center;
}

div h3 {
	margin-right: 20px;
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
}
</style>
</head>
<body>
	<h1>게시글 목록 구현</h1>
	<a href="/create">게시글 작성</a>
	<c:forEach var="qna" items="${qnaList}">
		<div>
			<h3>
				<a href="#"
					onclick="checkSecure('${qna.articleId}', ${qna.secure}, '${qna.password}')">~작성자~:
					${qna.username}</a>
			</h3>
			<h3>
				<a href="#"
					onclick="checkSecure('${qna.articleId}', ${qna.secure}, '${qna.password}')">~제목~:
					${qna.title}</a>
			</h3>
			<h3>
				<a href="#"
					onclick="checkSecure('${qna.articleId}', ${qna.secure}, '${qna.password}')">~조회수~:
					${qna.views}</a>
			</h3>
		</div>
	</c:forEach>
	
	
	<div class="pagination">
        <c:forEach var="i" begin="1" end="${totalPages}">
            <a href="/qna?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
        </c:forEach>
    </div>


	<script>
    function checkSecure(articleId, isSecure, qnaPassword) {
        if (isSecure) {
            const password = prompt("비밀번호를 입력하세요:");
            
            if (password) {
                if (password == qnaPassword) {
                    window.location.href = `/qna/` + articleId;
                } else {
                    alert("비밀번호가 일치하지 않습니다.");
                }
                
            }
        } else {
            window.location.href = `/qna/` + articleId; // 템플릿 리터럴 확인
        }
    }

    </script>
</body>
</html>
