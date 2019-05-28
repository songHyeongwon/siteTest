<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			$(function() {
				$("#boardInsertBtn").click(function() {
					if(!chkSubmit($("#b_name"),"작성자를")) return
					else if(!chkSubmit($("#b_title"),"글제목을")) return
					else if(!chkSubmit($("#b_content"),"내용을")) return
					else if(!chkSubmit($("#b_pwd"),"비밀번호를")) return
					else {
						$("#f_writeForm").attr({
							"method" : "post",
							"action" : "/board/boardInsert"
						});
						$("#f_writeForm").submit();
					}
				});
				
				//취소버튼 클릭시 처리 이벤트
				$("#boardCancelBtn").click(function() {
					$("#f_writeForm").each(function() {
						this.reset();
					})
				});
				
				//목록버튼 클릭시 처리 이벤트
				$("#boardListBtn").click(function() {
					var queryString = "?pageNum="+$("#pageNum").val()+"&amount="+$("#amount").val();
					location.href = "/board/boardList"+queryString;
				})
			});
		</script>
</head>
<body>
	<div class="contentContainer container-fluid">
		<div class="contentTit page-header">
			<h3 class="text-center">게시판 글작성</h3>
		</div>
		<div class="contentTB text-center">
			<form id="f_writeForm" name="f_writeForm" class="form-horizontal">
				<input type="hidden" name="pageNum" id="pageNum" value="${data.pageNum}" /> 
				<input type="hidden" name="amount" id="amount" value="${data.amount}" />
				<table class="table table-bordered">
					<colgroup>
						<col width="20%" />
						<col width="80%" />
					</colgroup>
					<tbody>
						<tr>
							<td>작성자</td>
							<td class="text-Left"><input type="text" id="b_name"
								name="b_name" class="form-control"></td>
						</tr>
						<tr>
							<td>글제목</td>
							<td class="text-Left"><input type="text" id="b_title"
								name="b_title" class="form-control"></td>
						</tr>
						<tr>
							<td>내용</td>
							<td class="text-Left"><textarea rows="8" id="b_content"
									name="b_content" class="form-control"></textarea></td>
						</tr>
						<tr>
							<td>비밀번호</td>
							<td class="text-Left"><input type="password" id="b_pwd"
								name="b_pwd" class="form-control"></td>
						</tr>
					</tbody>
				</table>
				<div class="text-right">
					<input type="button" value="저장" id="boardInsertBtn"
						class="btn btn-success" /> <input type="button" value="취소"
						id="boardCancelBtn" class="btn btn-success" /> <input
						type="button" value="목록" id="boardListBtn" class="btn btn-success" />
				</div>
			</form>
		</div>
	</div>

	<!-- <div>
			<h1>게시판 글작성</h1>
		</div>
		<form action="">
			<table>
				<tr>
					<td>작성자</td>
					<td><input type="text" id="b_name" name="b_name"></td>
				</tr>
				<tr>
					<td>글제목</td>
					<td><input type="text" id="b_title" name="b_title"></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea rows="5" cols="10" id="b_content" name="b_content"></textarea></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" id="b_pwd" name="b_pwd"></td>
				</tr>
			</table>
			<input type="button" value="저장"/>
			<input type="button" value="취소"/>
			<input type="button" value="목록"/>
		</form> -->
</body>
</html>