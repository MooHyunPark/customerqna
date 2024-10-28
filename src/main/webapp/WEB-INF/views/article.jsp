<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	max-width: 70%; /* 가로 길이 조정 */
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

.content {
	min-height: 600px;
}

a, button {
	margin: 10px;
}

footer {
	display: flex;
	justify-content: space-between; /* 댓글과 작성일 양쪽 배치 */
	border: 1px solid #ddd; /* 푸터 테두리 */
	padding: 10px;
}

footer p {
	margin: 0 20px; /* 요소 간 간격 */
}

.comments-section {
	margin-top: 20px;
	border-top: 1px solid #ddd; /* 구분선 추가 */
	padding-top: 20px;
}

.comment {
	border-bottom: 1px solid #ddd; /* 댓글 구분선 추가 */
	padding: 10px 0;
}

#commentForm .commentDetail {
	display: flex;
	gap: 30px;
}

.password-dialog {
	display: none;
	position: fixed;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	padding: 20px;
	border: 1px solid #ccc;
	background: #fff;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.password-dialog input[type="password"] {
	height: 30px;
	width: 200px;
	padding: 5px;
	margin-bottom: 10px;
}

#dialogBtn {
	display: block;
	text-align: left; /* 왼쪽 정렬 */
}

#commentForm {
	text-align: center; 
	justify-content: center !important; 
	align-items: center !important;
}
</style>
</head>
<body>
	<article>
		<header>
			<input type="hidden" id="articleId" value="${qna.articleId}">
			<div>
				<h3>제목 : ${qna.title}</h3>
				<h3>작성자 : ${qna.username}</h3>
				<h3>조회수 : ${qna.views}</h3>
			</div>
			<div>
				<c:if test="${sessionScope.admin == null}">
					<button onclick="checkPassword('${qna.articleId}', 'edit')">수정</button>
					<button onclick="checkPassword('${qna.articleId}', 'delete')">삭제</button>
				</c:if>
				<c:if test="${sessionScope.admin != null}">
					<a href="/change?articleId=${qna.articleId}" style="text-decoration: none; color: black;">수정</a>
					<a href="/delete?articleId=${qna.articleId}" style="text-decoration: none; color: black;">삭제</a>
				</c:if>
				<a href="/qna" class="custom-link">메인으로 이동</a>
			</div>
		</header>
		<div class="content">
			<p>${qna.content}</p>
		</div>


		<div class="comments-section">
			<c:choose>
				<c:when test="${empty comments}">
					<h5>댓글이 없습니다</h5>
				</c:when>
				<c:otherwise>
					<h5>댓글목록 (댓글 수: ${fn:length(comments)}개)</h5>
					<c:forEach var="comment" items="${comments}">
						<div class="comment">
							<p>
								<strong>${comment.username}:</strong> ${comment.content}
								<button
									onclick="editComment('${comment.comment_id}', '${ admin }')">수정</button>
								<button
									onclick="deleteComment('${comment.comment_id}', '${ admin }')"
									style="margin-left: -10px;">삭제</button>
								/ 작성일:${comment.formatCreatedAt}
							</p>
						</div>

					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>

		<!-- 댓글 수정/삭제 다이얼로그 -->
		<div id="commentDialog" class="dialog" style="display: none;">
			<div style="display: flex;">
				<p id="dialogContent">비밀번호를 입력하세요:</p>
				<textarea id="dialogTextarea" style="display: none; height: 15px; resize: none; margin-top: 18px; margin-left: 20px;"></textarea>
				<c:if test="${sessionScope.admin == null}">
					<p style="margin-left: 20px;">비밀번호:</p>
				</c:if>
				<input type="password" id="dialogPassword" style="height: 15px; margin-top: 18px; margin-left: 20px;">
				<button id="dialogSubmitBtn" style="height: 25px; margin-top: 15px; margin-left: 20px;">제출</button>
				<button onclick="closeDialogAnother()" style="height: 25px; margin-top: 15px">취소</button>
				<input type="hidden" id="hiddenCommentId">
			</div>

		</div>



		<button id="dialogBtn" onclick="showCommentForm()">댓글 작성</button>
		<!-- 댓글 입력 폼 -->
		<div id="commentFormWrapper" style="display: none; margin-top: 20px;">
			<form id="commentForm" action="/submitComment" method="POST">
				<input type="hidden" name="articleId" value="${qna.articleId}">
				<div class="commentDetail">

					<div style="display: flex;">
						<c:if test="${sessionScope.admin != null}">
							<input type="hidden" id="username" name="username" value="관리자">
							<input type="hidden" id="password" name="password" value="관리자">
						</c:if>
						<c:if test="${sessionScope.admin == null}">
							<div>
								<label for="username">아이디:</label> <input type="text"
									id="username" name="username" required
									style="height: 10px; width: 100px; padding: 5px; margin-bottom: 10px;">
							</div>
							<div style="margin-left: 20px;">
								<label for="password">비밀번호:</label> <input type="password"
									id="password" name="password" required
									style="height: 10px; width: 100px; padding: 5px; margin-bottom: 10px;">
							</div>
						</c:if>
					</div>

				</div>

				<div style="display: flex;">
					<div style="text-align: initial;">
						<label for="content" style="vertical-align: top;">내용:</label>
						<textarea id="content" name="content" required
							style="height: 12px; width: 500px; resize: none; padding: 5px; margin-bottom: 10px;"></textarea>
					</div>
					<button type="submit"
						style="height: 30px; margin-top: -4px; margin-left: 10px;">댓글제출</button>
				</div>
			</form>
		</div>



		<div id="passwordDialog" class="password-dialog">
			<p>비밀번호를 입력하세요:</p>
			<input type="password" id="passwordInput">
			<button id="confirmBtn" onclick="submitPassword()">확인</button>
			<button id="cancelBtn" onclick="closeDialog()">취소</button>
		</div>


		<footer>
			<p>작성일 : ${qna.createdAt}</p>
		</footer>
</body>
<script>
const articleId = document.getElementById('articleId').value;


function closeDialogAnother() {
    document.getElementById('commentDialog').style.display = 'none';
    document.getElementById('dialogTextarea').value = '';
    document.getElementById('dialogPassword').value = '';
    document.getElementById('hiddenCommentId').value = ''; // 히든 타입 input 초기화
}

function editComment(commentId, admin) {
	closeCommentForm();
	closeDialog();
    const dialog = document.getElementById('commentDialog');
    const contentText = document.getElementById('dialogContent');
    const textarea = document.getElementById('dialogTextarea');
    const passwordInput = document.getElementById('dialogPassword');
    const submitBtn = document.getElementById('dialogSubmitBtn');
    const hiddenCommentId = document.getElementById('hiddenCommentId');
    
    hiddenCommentId.value = commentId; // 히든 타입 input에 commentId 값 설정
    
    contentText.textContent = '내용:';
    textarea.style.display = 'block';
    if (admin === 'admin') {
        passwordInput.style.display = 'none';
    } else {
        passwordInput.style.display = 'block';
    }

    submitBtn.onclick = function () {
        const content = textarea.value;
        const password = passwordInput.value;
        const commentId = hiddenCommentId.value; // 히든 타입 input에서 commentId 값 읽기

        if (admin === 'admin') {
            // 관리자일 경우 비밀번호 없이 수정
            const url = `/editComment?commentId=` + commentId + `&content=` + content;
            editprocedure(url, "댓글 수정에 실패했습니다.");
        } else {
            // 관리자 아닐 경우 비밀번호 확인
            if (password) {
                const url = `/editComment?commentId=` + commentId + `&password=` + password + `&content=` + content;
                editprocedure(url, "올바른 비밀번호를 입력해주세요.");
            } else {
                alert('비밀번호를 입력해주세요.');
            }
        }
    };
    dialog.style.display = 'block';
}

function editprocedure(url, message) {
	fetch(url, {
        method: 'POST'
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert("댓글 수정에 성공했습니다.");
            window.location.reload(); // 페이지 새로고침
        } else {
            alert(message);
        }
    })
    .catch((error) => {
        console.error('Error:', error);
        alert('오류가 발생했습니다.');
        closeDialogAnother();
    });
}

function deleteComment(commentId, admin) {
	closeDialog();
	closeCommentForm();
    const dialog = document.getElementById('commentDialog');
    const contentText = document.getElementById('dialogContent');
    const textarea = document.getElementById('dialogTextarea');
    const passwordInput = document.getElementById('dialogPassword');
    const submitBtn = document.getElementById('dialogSubmitBtn');
    const hiddenCommentId = document.getElementById('hiddenCommentId');
    
    hiddenCommentId.value = commentId; // 히든 타입 input에 commentId 값 설정

    contentText.textContent = '';
    textarea.style.display = 'none';
    if (admin === 'admin') {
    	contentText.textContent = '해당 댓글을 삭제하시겠습니까?';
        passwordInput.style.display = 'none';
    } else {
        passwordInput.style.display = 'block';
    }

    submitBtn.onclick = function () {
        const password = passwordInput.value;
        const commentId = hiddenCommentId.value; // 히든 타입 input에서 commentId 값 읽기
        if (admin === 'admin') {
            // 관리자일 경우 비밀번호 없이 수정
            const url = `/deleteComment?articleId=` + articleId + `&commentId=` + commentId;
            deleteprocedure(url);
            
        } else {
            // 관리자 아닐 경우 비밀번호 확인
        	const url = `/deleteComment?articleId=` + articleId + `&commentId=` + commentId + `&password=` + password;
        	deleteprocedure(url);
        }
    };

    dialog.style.display = 'block';
}

function deleteprocedure(url) {
	fetch(url, {
        method: 'POST'
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert("댓글이 삭제되었습니다.");
            window.location.reload(); // 페이지 새로고침
        } else {
            alert("댓글 삭제에 실패했습니다.");
        }
        closeDialogAnother();
    })
    .catch((error) => {
        console.error('Error:', error);
        alert('오류가 발생했습니다.');
        closeDialogAnother();
    });
}


document.getElementById("commentForm").addEventListener("submit", function(event) {
    event.preventDefault(); // 폼 제출 기본 동작 막기

    const formData = new FormData(this);
    const jsonData = {};
    formData.forEach((value, key) => {
        jsonData[key] = value;
    });

    fetch("/submitComment", {
        method: "POST",
        headers: {
            "Content-Type": "application/json" // 미디어 타입을 JSON으로 설정
        },
        body: JSON.stringify(jsonData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            window.location.reload(); // 페이지 새로고침
        } else {
            alert("댓글 추가에 실패했습니다.");
        }
    })
    .catch(error => {
        console.error("Error:", error);
        alert("오류가 발생했습니다.");
    });
});

function checkPassword(articleId, action) {
    const dialog = document.getElementById('passwordDialog');
    const passwordInput = document.getElementById('passwordInput');
    const confirmBtn = document.getElementById('confirmBtn');
    const cancelBtn = document.getElementById('cancelBtn');

    dialog.style.display = 'block';

    confirmBtn.onclick = () => submitPassword(articleId, action);
    cancelBtn.onclick = closeDialog;

    passwordInput.addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            submitPassword(articleId, action);
        }
    });

    passwordInput.focus();
}

function submitPassword(articleId, action) {
	
    const passwordInput = document.getElementById('passwordInput');
    const password = passwordInput.value;
    const dialog = document.getElementById('passwordDialog');

    if (password) {
    	if (passwordInput != '관리자') {
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
                closeDialog();
            })
            .catch((error) => {
                console.error('Error:', error);
                alert('오류가 발생했습니다.');
                closeDialog();
            });
 		} else {
 			 alert('비밀번호가 일치하지 않습니다.');
 		}
        
    }
}

function closeDialog() {
    document.getElementById('passwordDialog').style.display = 'none';
    document.getElementById('passwordInput').value = '';
}

function showCommentForm() {
	closeDialogAnother();
    document.getElementById("commentFormWrapper").style.display = "flex";
    document.getElementById("dialogBtn").style.display = "none";
}

function closeCommentForm() {
    document.getElementById("commentFormWrapper").style.display = "none";
    document.getElementById("dialogBtn").style.display = "flex";
}

</script>

</html>
