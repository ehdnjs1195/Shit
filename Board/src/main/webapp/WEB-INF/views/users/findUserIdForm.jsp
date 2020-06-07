<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
</head>
<body>
	<form action="findUserId.do" method="post">
		<label for="user_name"> 이름 <input type="text" id="user_name"
			name="user_name" placeholder="이름 입력" />
		</label> 
		<label for="user_email"> 이메일 <input type="email"
			id="user_email" name="user_email" placeholder="이메일 입력" />
		</label> 
		<input type="submit" value="아이디 찾기" />
	</form>
</body>
</html>