<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>정보 확인</title>
</head>
<body>
<c:choose>
	<c:when test="${not empty userId }">
		<p>회원님의 아이디는 <strong>${userId } </strong>입니다.</p>
	</c:when>
	<c:when test="${not empty userPwd }">
		<p>회원님의 비밀번호는 <strong>${userPwd } </strong>입니다.</p>
	</c:when>
</c:choose>
<a href="loginform.do">로그인 하러가기</a>
<a href="../home.do">홈으로 가기</a>
</body>
</html>