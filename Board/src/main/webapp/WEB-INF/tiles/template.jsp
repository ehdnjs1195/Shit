<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>제목</title>
    <style>
        body{
			padding-top: 60px;
		}
        #left{
            float:left;
             width:15%;
            background-color: gray;
        }
        #main{
            float:left;
             width:85%;
            background-color: lime;
        }
        #footer{
            width: 100%;
            height: 50px;            
            text-align: center;
            background-color: orange;
            clear:both;
        }
         #left, #main{ 
               min-height: 600px;
         } 
    </style>
    

    <%-- 필요한 css 로딩하기 --%>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/custom.css" />
	<%-- 필요한 javascript 로딩하기 --%>
	<script src="${pageContext.request.contextPath }/resources/js/jquery-3.3.1.js"></script>
	<script src="${pageContext.request.contextPath }/resources/js/bootstrap.js"></script>
</head>
<body>

    <div class="container">
	    <div id="header"><tiles:insertAttribute name="header" /></div>
	    <%-- <div id="left"><tiles:insertAttribute name="left" /></div> --%>
	    <div id="main"><tiles:insertAttribute name="body" /></div>    
	    <div id="footer"><tiles:insertAttribute name="footer" /></div>
    </div>
 	
    <script type="text/javascript">
        $(function() {
 
        });    
    </script>    
</body>
</html>