<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FAQ 추가하기</title>
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
            width: 50%;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #4CAF50;
        }
        label {
            display: block;
            margin: 10px 0 5px;
            color: #333;
        }
        input[type="text"], textarea {
            width: 97%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }
        button {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>자주 묻는 질문 추가하기</h2>
        <form id="faqForm">
            <label for="questionTitle">질문 제목</label>
            <input type="text" id="questionTitle" name="questionTitle" required>

            <label for="questionDetail">질문 내용</label>
            <textarea id="questionDetail" name="questionDetail" rows="4" required></textarea>

            <button type="button" onclick="submitForm()">제출</button>
        </form>
    </div>

    <script>
        function submitForm() {
            const form = document.getElementById('faqForm');
            const formData = new FormData(form);

            fetch('/submitFaq', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
                alert('FAQ가 성공적으로 제출되었습니다.');
                window.location.href = '/qna';
            })
            .catch((error) => {
                console.error('Error:', error);
                alert('FAQ 제출에 실패했습니다.');
            });
        }
    </script>
</body>
</html>
