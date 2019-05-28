<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<!-- 브라우저의 호환성 보기 모드를 막고, 해당 브라우저에서 지원하는 가장 최신 버전의 방식으로  html을 보여주도록 설정 -->
		<meta name="viewport" content="width=device-width initial-scale=1.0,
		maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
		<!-- viewport : 화면에 보이는 영역을 제어하는 기술.
		width는 device-width로 설정. initial-scale는 초기비율 -->
		<!-- IE8이하 브라우저에서 HTML5를 인식하기 위해서는 아래의 패스필터를 적용하면 된다. -->
		<!-- 만약 lt IE 9보다 낮다면 script html5shiv.js를 읽어와 적용하라 -->
		<!-- [if lt IE 9]>
			<script src="../js/html5shiv.js"></script>
		<![endif] -->
		<link rel="shortcut icon" href="/resources/image/icon.png"/>
		<link rel="apple-touch-icon" href="/resources/image/icon.png"/>
		<link rel="stylesheet" href="/resources/include/dist/css/bootstrap.min.css">
		<link rel="stylesheet" href="/resources/include/dist/css/bootstrap-theme.min.css">
		<!--모바일 웹 페이지 설정 끝 -->
		<script type="text/javascript" src="/resources/include/js/jquery-1.12.4.min.js"></script>
		<script type="text/javascript" src="/resources/include/js/common.js"></script>
		<script src="/resources/include/dist/js/bootstrap.min.js"></script>
		<script type="text/javascript">
			$(function() {
				//수정버튼을 눌렀을시 작업
				$("#boardUpdateBtn").click(function() {
					if(!chkSubmit($("#b_title"),"글제목을")) return
					else if(!chkSubmit($("#b_content"),"글내용을")) return
					else {
						$("#f_updateForm").attr({
							"method":"post",
							"action":"/board/boardUpdate"
						});
						$("#f_updateForm").submit();
					}
				});
				//취소버튼을 눌렀을시 작업
				$("#boardCancelBtn").click(function() {
					console.log("나오긴 함?")
					/* history.back(); 이렇게 하면 안되나요? */
					$("#f_updateForm").each(function() {
						this.reset();
					});
				});
				//목록버튼 클릭시
				$("#boardListBtn").click(function() {
					var queryString = "?pageNum="+$("#pageNum").val()+"&amount="+$("#amount").val();
					location.href = "/board/boardList"+queryString;
				})
			})
		</script>
		<!--모바일 웹 페이지 설정 끝 -->
	</head>
	<body>
		<div class="contentContainer container-fiuid">
			<div class="contentTit page-header"><h3 class="text-center">게시판 글수정</h3></div>
			
			<div class="contentTB text-center">
				<form id="f_updateForm" name="f_updateForm">
					<input type="hidden" name="b_num" value="${updateData.b_num}"/>
					<input type="hidden" name="pageNum" id="pageNum" value="${data.pageNum}"/>
					<input type="hidden" name="amount" id="amount" value="${data.amount}"/>
					
					<table class="table table-bordered">
						<colgroup>
							<col width="17%"/>
							<col width="33%"/>
							<col width="17%"/>
							<col width="33%"/>
						</colgroup>
						<tbody>
							<tr>
								<td>글번호</td>
								<td class="text-left">${updateData.b_num}</td>
								<td>작성일</td>
								<td class="text-left">${updateData.b_date}</td>
							</tr>
							<tr>
								<td>작성자</td>
								<td colspan="3" class="text-left">${updateData.b_name}</td>
							</tr>
							<tr>
								<td>글제목</td>
								<td colspan="3" class="text-left">
									<input type="text" value="${updateData.b_title}" id="b_title" name="b_title" class="form-control">
								</td>
							</tr>
							<tr class="table-height">
								<td>내 용</td>
								<td colspan="3" class="text-left">
									<textarea name="b_content" id="b_content" class="form-control" rows="8">${updateData.b_content}</textarea>
								</td>
							</tr>
							<tr>
								<td>비밀번호</td>
								<td colspan="3" class="text-left">
									<input type="password" id="b_pwd" name="b_pwd">
									<label>수정할 비밀번호를 입력해 주세요.</label>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<div class="contentBtn text-right">
				<input type="button" value="수정" id="boardUpdateBtn" class="btn btn-success"/>
				<input type="button" value="취소" id="boardCancelBtn" class="btn btn-success"/>
				<input type="button" value="목록" id="boardListBtn" class="btn btn-success"/>
			</div>
		</div>
	</body>
</html>