<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>회원가입</title>
</head>
<body>
<div class="container">
	<h1>회원가입 페이지</h1>
	<form action="signup.do" method="post" id="signupForm">
		<div class="form-group has-feedback">
			<label class="control-label" for="id">아이디</label>
			<input class="form-control" type="text" id="id" name="user_id" />
		</div>
		
		<div class="form-group has-feedback">
			<label class="control-label" for="pwd">비밀번호</label>
			<input class="form-control" type="password" id="pwd" name="user_pwd"/>
		</div>
		<div class="form-group">
			<label class="control-label" for="pwd2">비밀번호 확인</label>
			<input class="form-control" type="password" id="pwd2" name="user_pwd2"/>
		</div>
		<div class="form-group has-feedback">
			<label class="control-label" for="name">이름</label>
			<input class="form-control" type="text" id="name" name="user_name" />
		</div>
		<div class="form-group has-feedback">
			<label class="control-label" for="email">이메일</label>
			<input class="form-control" type="email" id="email" name="user_email" />
		</div>
		<button class="btn btn-primary" type="submit">가입</button>
		<a class="btn btn-warning" href="../">취소</a>
	</form>
</div>
</body>
</html>