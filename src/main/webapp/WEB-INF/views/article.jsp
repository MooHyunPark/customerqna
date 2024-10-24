<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 조회 페이지</title>
<style type="text/css">
.custom-link {
	text-decoration: none; /* 밑줄 제거 */
	color: gray; /* 색깔 변경 */
	
}

.custom-link:hover {
	color: darkblue; /* 호버 시 색깔 변경 */
}

body {
	display: flex;
	flex-direction: column;
	align-items: center;
	margin-top: 20px;
}

article {
	width: 70%;
	border: 1px solid #ddd; /* 아티클 테두리 */
	padding: 20px;
	margin-bottom: 20px;
	text-align: center;
}

header {
	display: flex;
	justify-content: space-between; /* 양 끝에 배치 */
	border: 1px solid #ddd; /* 헤더 테두리 */
	padding: 10px;
	width: 100%;
}

header div {
	display: flex;
	align-items: center; /* 세로로 중앙 정렬 */
}

header h3 {
	margin: 0 20px; /* 요소 간 간격 */
}

p {
	text-align: left; /* 본문은 왼쪽 정렬 */
}

a, button {
	margin: 10px;
}

footer {
	display: flex;
	justify-content: center;
	border: 1px solid #ddd; /* 푸터 테두리 */
	padding: 10px;
}

footer p {
	margin: 0 20px; /* 요소 간 간격 */
}
</style>
</head>
<body>
	<article>
		<header>
			<div>
				<h3>제목 : ${qna.title}</h3>
				<h3>작성자 : ${qna.username}</h3>
				<h3>조회수 : ${qna.views}</h3>
			</div>
			<div>
				<button onclick="checkPassword('${qna.articleId}', 'edit')">수정</button>
				<button onclick="checkPassword('${qna.articleId}', 'delete')">삭제</button>
				<a href="/qna" class="custom-link">메인으로 이동</a>
			</div>
		</header>
		<p>내용</p>
		<p>${qna.content}</p>
		<footer>
			<p>작성일 : ${qna.createdAt}</p>
		</footer>
	</article>

	<script>
        function checkPassword(articleId, action) {
            const password = prompt("비밀번호를 입력하세요:");
            if (password) {
                fetch(`/checkPassword`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ articleId, password })
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        if (action === 'edit') {
                            window.location.href = `/change?articleId=` + articleId;
                        } else if (action === 'delete') {
                            alert('게시글을 삭제하였습니다.');
                            window.location.href = `/delete?articleId=` + articleId;
                        }
                    } else {
                        alert('비밀번호가 일치하지 않습니다.');
                    }
                })
                .catch((error) => {
                    console.error('Error:', error);
                    alert('오류가 발생했습니다.');
                });
            }
        }
    </script>
</body>