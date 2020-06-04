<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
</head>
<body>
<div class="container">
	<h1>회원 목록입니다.</h1>
	<table class="table ">
		<thead>
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>이메일</th>
				<th>회원상태</th>
				<th>회원권한</th>
				<th>가입일자</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tmp" items="${requestScope.list }">
				<tr>
					<td>${tmp.user_id }</td>
					<td>${tmp.user_name }</td>
					<td>${tmp.user_email }</td>
					<td>${tmp.user_state_code }</td>
					<td>${tmp.user_authority_code }</td>
					<td>${tmp.user_regdate }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</body>
</html>