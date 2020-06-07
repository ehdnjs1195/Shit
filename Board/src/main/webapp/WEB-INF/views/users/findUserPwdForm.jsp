<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="findUserPwd.do" method="post">
		<label for="user_id"> 아이디 <input type="text" id="user_id"
			name="user_id" placeholder="아이디 입력" />
		</label> 
		<label for="user_name"> 이름 <input type="text" id="user_name"
			name="user_name" placeholder="이름 입력" />
		</label> 
		<label for="user_email"> 이메일 <input type="email"
			id="user_email" name="user_email" placeholder="이메일 입력" />
		</label> 
		<input type="submit" value="비밀번호 찾기" />
	</form>
</body>
</html>