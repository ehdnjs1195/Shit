<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>로그인</title>
</head>
<body>
<div class="container">
	<form class="" action="login.do" method="post">
		<h1>Login</h1>
			<input type="hidden" name="url" value="" />
			<input type="text" name="user_id" placeholder="Username" value=""/>
			<input type="password" name="user_pwd" placeholder="Password" value=""/>
			<input type="submit" name="" value="Login"/>
	</form>
</div>
</body>
</html>