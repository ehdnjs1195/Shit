<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
   <h2>회원 목록</h2>
   <script>
   
   // 원하는 페이지로 이동시 검색조건, 키워드 값을 유지하기 위해 
   function list(page){
      location.href="${pageContext.request.contextPath }/admin/private/usersList.do?curPage="+page+"&searchOption=${map.searchOption}"+"&keyword=${map.keyword}";
   }
</script>
   
   
   <form class="form" name="form1" method="post" action="usersList.do">
      <select name="searchOption">
         <!-- 검색조건을 검색처리후 결과화면에 보여주기위해  c:out 출력태그 사용, 삼항연산자 -->
         <option value="all" <c:out value="${map.searchOption == 'all'?'selected':''}"/> >아이디+상태+권한</option>
         <option value="user_id" <c:out value="${map.searchOption == 'user_id'?'selected':''}"/> >아이디</option>
         <option value="user_state_code" <c:out value="${map.searchOption == 'user_state_code'?'selected':''}"/> >상태</option>
         <option value="user_authority_code" <c:out value="${map.searchOption == 'user_authority_code'?'selected':''}"/> >권한</option>
      </select>
      <input name="keyword" value="${map.keyword}">
      <input type="submit" value="조회">
   
   </form>
   <!-- 레코드의 갯수를 출력 -->
   ${map.count}개의 게시물이 있습니다.
   <table class="table">
      <tr>
         <th>아이디</th>
         <th>이름</th>
         <th>이메일</th>
         <th>회원 상태</th>
         <th>회원 권한</th>
         <th>가입 날짜</th>
      </tr>
   
   <c:forEach var="row" items="${map.list}">
      
         
      <!-- show 컬럼이 y일때(삭제X 글) -->
      <tr>
         <td>${row.user_id}</td>
         <!-- 게시글 상세보기 페이지로 이동시 게시글 목록페이지에 있는 검색조건, 키워드, 현재페이지 값을 유지하기 위해 -->
         <td>${row.user_name }</td>
         <td>${row.user_email}</td>
         <td>${row.user_state_code}</td>
         <td>${row.user_authority_code}</td>
         <td>
            <!-- 원하는 날짜형식으로 출력하기 위해 fmt태그 사용 -->
            <fmt:parseDate value="${row.user_regdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
         </td>
      </tr>
      
      
      
   </c:forEach>
      
      <!-- 페이징 -->
      <tr>
         <td colspan="5">
            <!-- 처음페이지로 이동 : 현재 페이지가 1보다 크면  [처음]하이퍼링크를 화면에 출력-->
            <c:if test="${map.pagingDto.curBlock > 1}">
               <a href="javascript:list('1')">[처음]</a>
            </c:if>
            
            <!-- 이전페이지 블록으로 이동 : 현재 페이지 블럭이 1보다 크면 [이전]하이퍼링크를 화면에 출력 -->
            <c:if test="${map.pagingDto.curBlock > 1}">
               <a href="javascript:list('${map.pagingDto.prevPage}')">[이전]</a>
            </c:if>
            
            <!-- **하나의 블럭 시작페이지부터 끝페이지까지 반복문 실행 -->
            <c:forEach var="num" begin="${map.pagingDto.blockBegin}" end="${map.pagingDto.blockEnd}">
               <!-- 현재페이지이면 하이퍼링크 제거 -->
               <c:choose>
                  <c:when test="${num == map.pagingDto.curPage}">
                     <span style="color: red">${num}</span>&nbsp;
                  </c:when>
                  <c:otherwise>
                     <a href="javascript:list('${num}')">${num}</a>&nbsp;
                  </c:otherwise>
               </c:choose>
            </c:forEach>
            
            <!-- 다음페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음]하이퍼링크를 화면에 출력 -->
            <c:if test="${map.pagingDto.curBlock <= map.pagingDto.totBlock}">
               <a href="javascript:list('${map.pagingDto.nextPage}')">[다음]</a>
            </c:if>
            
            <!-- 끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝]하이퍼링크를 화면에 출력 -->
            <c:if test="${map.pagingDto.curPage <= map.pagingDto.totPage}">
               <a href="javascript:list('${map.pagingDto.totPage}')">[끝]</a>
            </c:if>
         </td>
      </tr>
      <!-- 페이징 -->
</table>

</body>
</html>