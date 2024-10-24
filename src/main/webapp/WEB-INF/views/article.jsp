<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 조회 페이지</title>
</head>
<body>
    <article>
        <header>
            <h3>제목 : ${qna.title}</h3>
            <h4>작성자 : ${qna.username}</h4>
            <p>조회수 : ${qna.views}</p>
        </header>
        <p>내용</p>
        <p>${qna.content}</p>
        <footer>
            <p>작성일 : ${qna.createdAt}</p>
            <p>마지막 수정 시간 : ${qna.updatedAt}</p>
        </footer>
    </article>
    <a href="/qna">메인화면으로 이동</a>
    <button onclick="checkPassword('${qna.articleId}', 'edit')">수정</button>
    <button onclick="checkPassword('${qna.articleId}', 'delete')">삭제</button>

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
</html>
