<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>자주 묻는 질문</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f0f0f0;
        }
        .container {
            border: 2px solid #4CAF50;
            border-radius: 10px;
            background-color: #fff;
            padding: 20px;
            width: 60%;
            max-height: 80vh;
            overflow-y: auto;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
        }
        h2 {
            color: #4CAF50;
        }
        h4 {
            margin: 10px 0 5px;
            color: #333;
        }
        p {
            margin: 0 0 15px;
            line-height: 1.6;
            color: #666;
        }
        .styled-link {
            margin-top: 10px;
            text-decoration: none;
            color: #4CAF50;
            font-weight: bold;
            font-size: 18px;
            background-color: #f0f0f0;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.3s, color 0.3s;
        }
        .styled-link:hover {
            background-color: #4CAF50;
            color: white;
        }
        .delete-btn {
            color: white;
            background-color: #ff6347;
            border: none;
            padding: 5px 10px;
            margin-left: 10px;
            cursor: pointer;
            border-radius: 3px;
            transition: background-color 0.3s;
        }
        .delete-btn:hover {
            background-color: #e5533d;
        }
    </style>
</head>
<body>
    <div class="container">
        <div style="display: flex; justify-content: space-between;">
            <h2>자주 묻는 질문</h2>
            <a href="/qna" class="styled-link" style="height: 25px;">메인으로</a>
        </div>
        <c:forEach var="question" items="${questionList}">
            <div style="display: flex; justify-content: space-between; align-items: center;">
                <div>
                    <h4>${question.question_title}</h4>
                    <p>${question.question_detail}</p>
                </div>
                <c:if test="${admin == 'admin'}">
                    <button class="delete-btn" onclick="deleteQuestion('${question.question_id}')">삭제하기</button>
                </c:if>
            </div>
        </c:forEach>
    </div>

    <script>
        function deleteQuestion(questionId) {
            if (confirm('정말 삭제하시겠습니까?')) {
                fetch(`/deleteQuestion?id=` + questionId, {
                    method: 'POST'
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert('질문이 삭제되었습니다.');
                        location.reload();
                    } else {
                        alert('질문 삭제에 실패했습니다.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('오류 발생');
                });
            }
        }
    </script>
</body>
</html>
