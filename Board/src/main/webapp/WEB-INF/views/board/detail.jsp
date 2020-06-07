<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/cafe/detail.jsp</title>
<style>
	/* div */
	.contents, table{
		width: 100%;
		border: 1px dotted #cecece;
		box-shadow: 1px 3px 3px 1px #ccc; /* 그림자 추가 */
	}
	#comment_table{
		margin-top:60px;
	}


	/* 글 내용을 출력할 div 에 적용할 css */
	.contents, table{
		width: 100%;
		border: 1px dotted #cecece;
		box-shadow: 3px 3px 5px 6px #ccc;
	}
	/* 댓글에 관련된 css */
	.comments ul{
		padding: 0;
		margin: 0;
		list-style-type: none;
	}
	.comments ul li{
		border-top: 1px solid #888; /* li 의 윗쪽 경계선 */
	}
	.comments dt{
		margin-top: 5px;
	}
	.comments dd{
		margin-left: 26px;
	}
	.comments form textarea, .comments form button{
		float: left;
	}
	.comments li{
		clear: left;
	}
	.comments form textarea{
		width: 85%;
		height: 100px;
	}
	.comments form button{
		width: 15%;
		height: 100px;
	}
	/* 댓글에 댓글을 다는 폼과 수정폼을 일단 숨긴다. */
	.comment form{
		display: none;
	}
	.comment{
		position: relative;
	}
	.comment .reply_icon{
		width: 8px;
		height: 8px;
		position: absolute;
		top: 10px;
		left: 30px;
	}
	.comments .user-img{
		width: 20px;
		height: 20px;
		border-radius: 50%;
	}
</style>
</head>
<body>

	<jsp:param value="cafe" name="category"/>
</jsp:include>
<div class="container">
	<ol class="breadcrumb">
		<li><a href="${pageContext.request.contextPath }/cafe/list.do">목록</a></li>
		<li>글 상세 보기</li>
	</ol>
	<table class="table table-bordered table-condensed">
		<colgroup>
			<col class="col-xs-4"/>
			<col class="col-xs-8"/>
		</colgroup>
		<tr>
			<th>글번호</th>
			<td>${dto.num }</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${dto.writer }</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${dto.title }</td>
		</tr>
		<tr>
			<th>등록일</th>
			<td>${dto.regdate }</td>
		</tr>
	</table>
	<div class="contents">${dto.content }</div>	<!-- div로 뿌려주면 에디터로 작성한 것이 그대로 출력된다. (textarea말고) -->
	<div style="margin-top:10px;">
		<a class="btn btn-primary pull-right" href="list.do?pageNum=${pageNum }">목록 보기</a>
		<%-- 
			글 작성자와 로그인 된 아이디가 같을때만 기능을 제공해준다.
			즉, 본인이 작성한 글만 수정할 수 있도록 하기위해(조건부로 출력)
		 --%>
		<c:if test="${dto.writer eq id }">
			<a class="btn btn-primary" style="margin-right:5px;" href="updateform.do?num=${dto.num }&pageNum=${pageNum}">수정</a>	<!-- 경로를 private으로 보내는 이유는 로그인 되있는 상태이어야 수정이 가능하도록 하기 위해서. -->	
			<a class="btn btn-warning" href="javascript:deleteConfirm();">삭제</a>
		</c:if>
	</div>
	
	<!-- 댓글창 -->
	<div class="comments">
		<ul>
		<c:forEach items="${commentList }" var="tmp">
			<c:choose>
				<c:when test="${tmp.deleted ne 'yes' }">
					<li class="comment" id="comment${tmp.num }" <c:if test="${tmp.num ne tmp.comment_group }">style="padding-left:50px;"</c:if> >
						<c:if test="${tmp.num ne tmp.comment_group }">
							<img class="reply_icon" src="${pageContext.request.contextPath}/resources/images/re.gif"/>
						</c:if>
						<dl>
							<dt>
								<c:choose>
									<c:when test="${empty tmp.profile }">
										<img class="user-img" src="${pageContext.request.contextPath}/resources/images/default_user.jpeg"/>
									</c:when>
									<c:otherwise>
										<img class="user-img" src="${pageContext.request.contextPath}${tmp.profile}"/>
									</c:otherwise>
								</c:choose>
								
								<span>${tmp.writer }</span>
								<c:if test="${tmp.num ne tmp.comment_group }">
									to <strong>${tmp.target_id }</strong>
								</c:if>
								<span>${tmp.regdate }</span>
								<a href="javascript:" class="reply_link">답글</a> |
								<c:choose>
									<%-- 로그인된 아이디와 댓글의 작성자가 같으면 --%>
									<c:when test="${id eq tmp.writer }">
										<a href="javascript:" class="comment-update-link">수정</a>&nbsp;&nbsp;
										<a href="javascript:deleteComment(${tmp.num })">삭제</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:">신고</a>
									</c:otherwise>
								</c:choose>
							</dt>
							<dd>
								<pre>${tmp.content }</pre>
							</dd>
						</dl>
						<form class="comment-insert-form" action="comment_insert.do" method="post">
							<input type="hidden" name="pageNum" value="${pageNum }" />
							<!-- 덧글 그룹 -->
							<input type="hidden" name="ref_group" value="${dto.num }" />
							<!-- 덧글 대상 -->
							<input type="hidden" name="target_id" value="${tmp.writer }" />
							<input type="hidden" name="comment_group" value="${tmp.comment_group }" />
							<textarea name="content"><c:if test="${empty id }">로그인이 필요합니다.</c:if></textarea>
							<button type="submit">등록</button>
						</form>	
						<!-- 로그인한 아이디와 댓글의 작성자와 같으면 수정폼 출력 -->				
						<c:if test="${id eq tmp.writer }">
							<form class="comment-update-form" action="comment_update.do" method="post">
								<input type="hidden" name="num" value="${tmp.num }" />
								<textarea name="content">${tmp.content }</textarea>
								<button type="submit">수정</button>
							</form>
						</c:if>
					</li>				
				</c:when>
				<c:otherwise>
					<li <c:if test="${tmp.num ne tmp.comment_group }">style="padding-left:50px;"</c:if> >삭제된 댓글 입니다.</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</ul>
		<div class="clearfix"></div>
		<!-- 원글에 댓글을 작성할 수 있는 폼 -->
		<div class="comment_form">
			<form action="comment_insert.do" method="post">
				<!-- 댓글의 그룹번호는 원글의 글번호가 된다. -->
				<input type="hidden" name="pageNum" value="${pageNum }" />
				<input type="hidden" name="ref_group" value="${dto.num }" />
				<!-- 댓글의 대상자는 원들의 작성자가 된다. -->
				<input type="hidden" name="target_id" value="${dto.writer }" />
				<textarea name="content"><c:if test="${empty id }">로그인이 필요합니다.</c:if></textarea> 
				<button type="submit">등록</button>
			</form>
		</div>
	</div>
</div>

<script>
	//댓글 수정 링크를 눌렀을때 호출되는 함수 등록
	$(".comment-update-link").click(function(){
		$(this)
		.parent().parent().parent() 	//세칸 올라가서 하위에 class="comment-update-form" 을 찾는다.
	 	.find(".comment-update-form")	
		.slideToggle(200);				
	});
	
	//댓글 수정 폼에 submit 이벤트가 일어났을때 호출되는 함수 등록
	$(".comment-update-form").on("submit", function(){
		// "comment_update.do"
		var url=$(this).attr("action");	// form에 action 속성을 읽어온다.
		//폼에 작성된 내용을 query 문자열로 읽어온다.
		// num=댓글번호&content=댓글내용
		var data=$(this).serialize();	//넘길 data를 (num=xx&content=xx) 읽어온다.
		//이벤트가 일어난 폼을 선택해서 변수에 담아 놓는다.
		var $this=$(this);
		$.ajax({
			url:url,
			method:"post",
			data:data,
			success:function(responseData){
				// responseData : {isSuccess:true}
				if(responseData.isSuccess){
					//폼을 안보이게 한다 
					$this.slideUp(200);
					//폼에 입력한 내용 읽어오기
					var content=$this.find("textarea").val();
					//pre 요소에 수정 반영하기 
					$this.parent().find("pre").text(content);
				}
			}
		});
		//폼 제출 막기 
		return false;
	});
	
	//댓글 삭제를 눌렀을때 호출되는 함수
	function deleteComment(num){
		var isDelete=confirm("확인을 누르면 댓글이 삭제 됩니다.");
		if(isDelete){
			//페이지 전환 없이 ajax 요청을 통해서 삭제하기
			$.ajax({
				url:"comment_delete.do",	// 상대경로 =>/cafe/comment_delete.do로 요청됨.
				method:"post",
				data:{"num":num},	// num 이라는 파라미터명으로 삭제할 댓글의 번호 전송
				success:function(responseData){
					if(responseData.isSuccess){
						var sel="#comment"+num;	//아이디가 comment##인 것에 삭제 멘트 출력하기.
						$(sel).text("삭제된 댓글 입니다.");
					}
				}
			});
		}
	}
	
	//폼에 submit 이벤트가 일어 났을때 실행할 함수 등록 
	$(".comments form").on("submit", function(){
		//로그인 여부
		var isLogin=${not empty id};
		if(isLogin==false){
			alert("로그인 페이지로 이동 합니다.");
			location.href="${pageContext.request.contextPath}/users/loginform.do?url=${pageContext.request.contextPath}/cafe/detail.do?num=${dto.num}";
			return false;//폼 전송 막기 
		}
	});
	//폼에 focus 이벤트가 일어 났을때 실행할 함수 등록 
	$(".comments form textarea").on("click", function(){
		//로그인 여부
		var isLogin=${not empty id};
		if(isLogin==false){
			var isMove=confirm("로그인 하러 갈텨?");
			if(isMove){
				location.href="${pageContext.request.contextPath}/users/loginform.do?url=${pageContext.request.contextPath}/cafe/detail.do?num=${dto.num}";
			}
		}
	});
	
	//답글 달기 링크를 클릭했을때 실행할 함수 등록
	$(".comment .reply_link").click(function(){
		$(this)
		.parent().parent().parent()
		.find(".comment-insert-form")	//부모요소(dt), 부모요소(dl), 부모요소(li) 를 찾아올라가서 자손 요소로 class="comment-insert-form" 을 찾는다.
		.slideToggle(200);
		
		// 답글 <=> 취소가 서로 토글 되도록 한다. 
		if($(this).text()=="답글"){
			$(this).text("취소");
		}else{
			$(this).text("답글");
		}
	});
	
	function deleteConfirm(){
		var isDelete=confirm("글을 삭제 하시겠습니까?");
		if(isDelete){
			location.href="delete.do?num=${dto.num}";
		}
	}
</script>
</body>
</html>