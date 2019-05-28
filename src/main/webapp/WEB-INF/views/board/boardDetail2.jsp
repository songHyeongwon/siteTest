<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 브라우저의 호환성 보기 모드를 막고, 해당 브라우저에서 지원하는 가장 최신 버전의 방식으로  html을 보여주도록 설정 -->
<meta name="viewport"
	content="width=device-width initial-scale=1.0,
		maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<!-- viewport : 화면에 보이는 영역을 제어하는 기술.
		width는 device-width로 설정. initial-scale는 초기비율 -->
<!-- IE8이하 브라우저에서 HTML5를 인식하기 위해서는 아래의 패스필터를 적용하면 된다. -->
<!-- 만약 lt IE 9보다 낮다면 script html5shiv.js를 읽어와 적용하라 -->
<!-- [if lt IE 9]>
			<script src="../js/html5shiv.js"></script>
		<![endif] -->
<link rel="shortcut icon" href="/resources/image/icon.png" />
<link rel="apple-touch-icon" href="/resources/image/icon.png" />
<link rel="stylesheet"
	href="/resources/include/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/resources/include/dist/css/bootstrap-theme.min.css">
<!--모바일 웹 페이지 설정 끝 -->
<script type="text/javascript"
	src="/resources/include/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="/resources/include/js/common.js"></script>
<script src="/resources/include/dist/js/bootstrap.min.js"></script>
<script type="text/javascript">
			var butChk = 0;
			$(function() {
				$("#pwdChk").css("visibility","hidden");
				
				//수정버튼 클릭시 처리 이벤트
				$("#updateFormBtn").click(function() {
					$("#pwdChk").css("visibility","visible");
					$("#msg").text("작성시 입력한 비밀번호를 입력해주세요.").css("color","#000099");
					butChk=1;
				});
				//삭제버튼 클릭시 처리 이벤트
				//댓글기능 추가로 이벤트 변경함
				/* $("#boardDeleteBtn").click(function() {
					$("#pwdChk").css("visibility","visible");
					$("#msg").text("작성시 입력한 비밀번호를 입력해주세요.").css("color","#000099");
					butChk=2;
				}); */
				
				//삭제버튼 클릭시 댓글 확인 후 처리 이벤트
				$("#boardDeleteBtn").click(function() {
					$.ajax({
						url : "/board/replyCnt",
						type : "post",
						data : "b_num="+$("#b_num").val()+"",
						dataType : "text",
						error : function() {
							alert("시스템 오류 입니다. 관리자에게 문의 하세요.")
						},
						success : function(resultData) {
							if(resultData==0){
								$("#pwdChk").css("visibility","visible");
								$("#msg").text("작성시 입력한 비밀번호를 입력해주세요.").css("color","#000099");
								butChk=2;
							}else{
								alert("댓글 존재시 게시물 삭제할 수가 없습니다. \n댓글 삭제 후 다시 확인해주세요");
								return;
							}
						}
					});
				});
				
				//비밀번호 입력 양식 enter 제거
				$("#b_pwd").bind("keydown", function(event) {
					if(event.keyCode === 13){
						event.preventDefault();
					}
				});
				
				//비밀번호 확인 버튼 클릭시 처리 이벤트
				$("#pwdBtn").click(function() {
					boardPwdConfirm();
				});
				//목록 버튼 클릭시 처리 이벤트
				$("#boardListBtn").click(function() {
					var queryString = "?pageNum="+$("#pageNum").val()+"&amount="+$("#amount").val();
					location.href="/board/boardList"+queryString;
				});
			});
			function boardPwdConfirm() {
				if(!chkSubmit($("#b_pwd"),"비밀번호를")) return
				else {
					$.ajax({
						url : "/board/pwdConfirm",
						type : "post",
						data : $("#f_pwd").serialize(),
						dataType : "text",
						error : function() {
							alert("시스템 오류입니다. 관리자에게 문의하세요");
						},
						success : function(resultData) {
							var goUrl="";
							if(resultData=="실패"){
								$("#msg").text("작성시 입력한 비밀번호가 일치하지 않습니다.").css("color","red");
								$("#b_pwd").select();
								$("#b_pwd").val("");
							}else if(resultData=="성공"){
								$("#msg").text("");
								if(butChk==1){
									goUrl = "/board/updateForm";
								}else{
									if(confirm("정말 삭제하시겠습니까??")){
										goUrl = "/board/boardDelete";
									}else {
										$("#b_pwd").val("");
										return;
									}
								}
								$("#f_data").attr("action",goUrl);
								$("#f_data").submit();
							}
						}
					});
				}
			}
		</script>
<!--모바일 웹 페이지 설정 끝 -->
</head>
<body>
	<div class="contentContainer container-fiuid">
		<div class="contentTit page-header">
			<h3 class="text-center">게시판 상세보기</h3>
		</div>
		<form action="" name="f_data" id="f_data" method="post">
			<input type="hidden" name="b_num" value="${detail.b_num}" />
			<input type="hidden" name="pageNum" id="pageNum" value="${data.pageNum}"/>
			<input type="hidden" name="amount" id="amount" value="${data.amount}"/>
		</form>
		<%--비밀번호 확인 버튼 및 버튼 추가 시작 --%>
		<div id="boardPwdBut" class="row text-center">
			<div id="pwdChk" class="authArea col-md-8">
				<form name="f_pwd" id="f_pwd">
					<input type="hidden" name="b_num" id="b_num"
						value="${detail.b_num}" /> <label for="b_pwd" id="l_pwd">비밀번호
						: </label> <input type="password" name="b_pwd" id="b_pwd" />

					<button type="button" id="pwdBtn">확인</button>
					<span id="msg"></span>
				</form>
			</div>

		</div>
		<%--비밀번호 확인 버튼 및 버튼 추가 종료 --%>

		<%--상세 정보 보여주기 시작 --%>
		<div class="contentTB text-center">
			<table class="table table-bordered">
				<colgroup>
					<col width="17%" />
					<col width="33%" />
					<col width="17%" />
					<col width="33%" />
				</colgroup>
				<tbody>
					<tr>
						<td>작성자</td>
						<td class="text-left">${detail.b_name}</td>
						<td>작성일</td>
						<td class="text-left">${detail.b_date}</td>
					</tr>
					<tr>
						<td>제 목</td>
						<td class="text-left" colspan="3">${detail.b_title}</td>
					</tr>
					<tr class="table-height">
						<td>내 용</td>
						<td colspan="3" class="text-left">${detail.b_content}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<%--============================상세 정보 보여주기 종료============================== --%>
		<div class="btnArea text-right">
			<input type="button" value="수정" id="updateFormBtn"
				class="btn btn-success" /> <input type="button" value="삭제"
				id="boardDeleteBtn" class="btn btn-success" /> <input type="button"
				value="목록" id="boardListBtn" class="btn btn-success" />
		</div>
		<%-- <c:import url="reply.jsp"></c:import> --%>
		<jsp:include page="reply.jsp"></jsp:include>
	</div>
</body>
</html>