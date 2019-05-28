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
<!--모바일 웹 페이지 설정 끝 -->
<script type="text/javascript">
	$(function() {
		var b_num = ${detail.b_num};
		var btnNum = 0;
		var r_num = 0;
		var r_content = "";
		var r_name = "";
		
		//글 입력을 위한 ajax 연동 처리
		/* $(document).on("click","button[data-button='upBtn']", function() {
			//여기에 ajax 내용 정리
			$(".btn").parents("div.panel .panel-heading .pwdArea").remove();
			$(this).parents("div.panel .paenl-heading").append(pwdView());
			btnKind = $(this).attr("data-btn")
			console.log("클릭버튼 btnKind : "+btnKind);
		}); */
		
		//비밀번호 확인 버튼 클릭시 처리 이벤트
		$(document).on("click",".pwdCheckBut", function() {
			var r_num = $(this).paents("div.panel").attr("data-num");
			var form = $(this).parents(".inline");
			var pwd = form.find(".passwd");
			var msg = form.find(".msg");
			var value = 0;
			if(!formCheck(pwd,msg, "비밀번호를")) return;
			else{
				pwdCheck(r_num, pwd,msg).then(function(data) {
					console.log("data: "+data);
					if(data==1){
						console.log("비밀번호 확인 후 btnKind : "+btnKind);
						if(btnKind="upBtn"){
							console.log("수정 폼 출력");
						}else if(btnKind=="delBtn"){
							console.log("삭제 처리");
						}
					}
					btnKind="";
				});
			}
		});
		
		//업데이트 버튼 클릭시
		$("#updateBtn").click(function() {
			if(!checkForm("#r_content2","댓글 내용을")) return;
			else{
				//var insertUrl = "/replies/replyUpdate";
				
				$.ajax({
					url : "/replies/"+r_num,
					type : "put",
					headers : {
						"Content-Type":"application/json",
						"X-HTTP-Method-Override" : "PUT"
					},
					dataType:"text",
					data: JSON.stringify({
						r_num : r_num,
						r_pwd: $("#r_pwd2").val(),
						r_content:$("#r_content2").val()
					}),
					error : function() {
						alert("시스템 오류입니다. 관리자에게 문의 하세요");
					},
					success : function(result) {
						if(result=="SUCCESS"){
							alert("댓글 수정이 완료되었습니다.");
							dataReset();
							$("#updateModalForm").modal('hide');
							listAll(b_num);
						}
						
					}
				});
			}
		});
		$("#replyInsertFormBtn").click(function() {
			$("#replyMadel").modal();
		});
		
		//addItem("12","송현권","이 게시판은 제껍니다.","2019-05-10");
		//수정클릭시
		$(document).on("click","input[data-upbtn]",function(){
			r_num = $(this).parent("div").parent("div").attr("data-num");
			
			r_name = $(this).parent("div").children().eq(0).html();
			r_content = $(this).parent("div").parent("div").children().eq(1).html();
			r_content = r_content.replace(/<br>/g,"\n");
			$("#chkForm").modal();
			btnNum = 1;
			
			var form = $(".modal-body").children("form");
			form.each(function() {
				this.reset();
			});
		});
		//삭제 클릭시
		$(document).on("click","input[data-delbtn]",function(){
			r_num = $(this).parent("div").parent("div").attr("data-num");
			$("#chkForm").modal();
			btnNum = 2;
			
			var form = $(".modal-body").children("form");
			form.each(function() {
				this.reset();
			});
		});
		
		$('button[data-dismiss="modal"]').click(function() {
			dataReset();
		});
		//교수님꺼 수정 예시 교수님은 수정버튼 클릭시 r_num으로 db에서 해당 댓글의 VO를 받아와 업데이트 폼에가 값을 입력하였다.
		//모달역시 기존의 insert 모달을 변형하여 사용하였다.
		/* $(document).on("click",".btn", function() {
			if($(this).attr("data-update")){
				$.ajax({
					url : "replise/"+r_num+".json",
					type: "get",
					dataType : "json",
					error : function() {
						alert("시스템 오류입니다. 관리자에게 문의하세요");
					},
					success : function(data) {
						$("#r_name1").val(data.r_name);
						$("#r_content1").val(data.r_content);
						
						var num_input = $("<input>");
						num_input.attr({"type" : "hidden","name":"r_num"});
						num_input.val(r_num);
						$("#comment_form").append(num_input);
						
						$("#replyModalLabel").html("댓글수정");
						$("#replyInsertBtn").attr("data-button", "updateBtn");
						$("#replyInsertBtn").html("수정");
						
						$("#replyModal").modal();
					}
				});
			}
		}) */
		//비밀번호 확인 처리
		$("#replypwdConfirm").click(function() {
			if(!checkForm("#r_pwd1", "확인용 비밀번호를")) return;
			else{
				var pwdConUrl = "/replies/pwdConfirm";
				
				$.ajax({
					url : pwdConUrl,
					type : "post",
					/* headers : {
						"Content-Type":"application/json",
						"X-HTTP-Method-Override" : "POST"
					}, */
					dataType:"text",
					data: ({
						r_num : r_num,
						r_pwd : $("#r_pwd1").val()
					}),
					error : function() {
						alert("시스템 오류입니다. 관리자에게 문의 하세요");
					},
					success : function(result) {
						console.log(result);
						if(result=="<Integer>1</Integer>"){
							//alert("비밀번호가 일치합니다.");
							dataReset();
							$("#replyMadel").modal('hide');
							
							if(btnNum==1){
								//업데이트
								$("#r_name2").html(r_name);
								$("#r_content2").val(r_content);
								$("#updateModalForm").modal();
							} else if(btnNum==2){
								//삭제
								if(confirm("선택하신 댓글을 삭제하시겠습니까?")){
									console.log("b_num = "+b_num)
									$.ajax({
										url: '/replies/'+r_num+"/"+b_num,
										type: "delete",
										headers : {
											"X-HTTP-Method-Override":"DELETE"
										},
										dateType : "text",
										error : function() {
											alert("댓글을 삭제하는중 예기치못한 오류가 발생하였습니다.");
										},
										success : function(result) {
											console.log("result = "+result)
											if(result=="SUCCESS"){
												alert("댓글 삭제가 완료되었습니다.");
											}
										}
									});
								}
							}
							
							listAll(b_num);
						}else{
							alert("비밀번호를 다시 확인해주세요.");
						}
						$("#r_pwd1").val("");
					}
				})
			}
			$("#chkForm").modal('hide');
		});
		
		$("#replyInsertBtn").click(function() {
			if(!checkForm("#r_name","작성자명을")) return;
			else if(!checkForm("#r_content","댓글 내용을")) return;
			else if(!checkForm("#r_pwd","비밀번호를")) return;
			else{
				var insertUrl = "/replies/replyInsert";
				
				$.ajax({
					url : insertUrl,
					type : "post",
					headers : {
						"Content-Type":"application/json",
						"X-HTTP-Method-Override" : "POST"
					},
					dataType:"text",
					data: JSON.stringify({
						b_num : b_num,
						r_name : $("#r_name").val(),
						r_pwd: $("#r_pwd").val(),
						r_content:$("#r_content").val()
					}),
					error : function() {
						alert("시스템 오류입니다. 관리자에게 문의 하세요");
					},
					success : function(result) {
						if(result=="SUCCESS"){
							alert("댓글 등록이 완료되었습니다.");
							dataReset();
							$("#replyMadel").modal('hide');
							listAll(b_num);
						}
						
					}
				});
			}
		});
		//문서 시작시 댓글리스트 반환
		listAll(b_num);
	});
	
	function addItem(r_num, r_name, r_content, r_date) {//새로운 댓글 객체 추가
		
		
		
		//새로운 글이 추가될 div 태그 객체
		var wrapper_div = $("<div>");
		wrapper_div.attr("data-num",r_num);
		wrapper_div.addClass("panel panel-default");
		
		//작성자 정보가 지정될 <div>태그
		var new_div = $("<div>");
		new_div.addClass("panel-heading");
		
		//작성자 정보의 이름
		var name_span = $("<span>");
		name_span.addClass("name");
		name_span.html(r_name+"님");
		
		//작성일자
		var date_span = $("<span>");
		date_span.html("/"+r_date+" ");
		
		//수정하기 버튼
		var upBtn = $("<input>");
		upBtn.attr({"type" : "button", "value" : "수정하기"});
		upBtn.attr("data-upbtn","upBtn");
		upBtn.addClass("btn btn-primary gap");
		
		//삭제하기 버튼
		var delBtn = $("<input>");
		delBtn.attr({"type":"button", "value" : "삭제하기"});
		delBtn.attr("data-delbtn","delBtn");
		delBtn.addClass("btn btn-default gap");
		
		//내용
		var content_div = $("<div>");
		content_div.html(r_content);
		content_div.addClass("panel-body");
		
		//조립하기
		new_div.append(name_span).append(date_span).append(upBtn).append(delBtn);
		wrapper_div.append(new_div).append(content_div);
		$("#replyList").append(wrapper_div);
	}
	
	// 입력 폼 초기화
	function dataReset() {
		$("#r_name").val("");
		$("#r_pwd").val("");
		$("#r_content").val("");
		/* $("#replyForm").each(function() {
			this.reset();
		}); */
	}
	
	function listAll(b_num) {
		$("#replyList").html("");
		var url = "/replies/all/"+b_num+".json";
		
		$.getJSON(url, function(data) {
			console.log("list count : "+data.length);
			replyCnt = data.length;
			$(data).each(function() {
				var r_num = this.r_num;
				var r_name = this.r_name;
				var r_content = this.r_content;
				var r_date = this.r_date;
				r_content = r_content.replace(/(\r\n|\r|\n)/g,"<br/>");
				addItem(r_num,r_name,r_content,r_date);
			})
		}).fail(function() {
			alert("댓글목록을 불러오는데 실패하였습니다. 잠시후에 다시 시도해 주세요")
		});
	}
	
	//댓글삭제
	/* function deleteBtn(b_num, r_num) {
		if(confirm("댓글삭제 각?")){
			$.ajax({
				url: '/replies/'+r_num,
				type: "delete",
				headers : {
					"X-HTTP-Method-Override":"DELETE"
				},
				dateType: "text",
				error : function() {
					alert("댓글을 삭제하는중 예기치못한 오류가 발생하였습니다.");
				},
				success : function(result) {
					console.log("result = "+result)
					if(result=="SUCCESS"){
						alert("댓글 삭제가 완료되었습니다.");
					}
				}
			});
		}
	} *///생성
	
	//댓글수정
	/* function updateForm(r_num) {
		$.ajax({
			url : "replise/"+r_num+".json",
			type: "get",
			dataType : "json",
			error : function() {
				alert("시스템 오류입니다. 관리자에게 문의하세요");
			},
			success : function(data) {
				$("#r_name1").val(data.r_name);
				$("#r_content1").val(data.r_content);
				
				var num_input = $("<input>");
				num_input.attr({"type" : "hidden","name":"r_num"});
				num_input.val(r_num);
				$("#comment_form").append(num_input);
				
				$("#replyModalLabel").html("댓글수정");
				$("#replyInsertBtn").attr("data-button", "updateBtn");
				$("#replyInsertBtn").html("수정");
				
				$("#replyModal").modal();
			}
		});
	} */
	/* 비밀번호 확인 버튼 클릭시 실질적인 처리 함수
	참고 : Promise는 비동기 처리가 성공 하였는지 실패 하였는지 드의 상태 정보와 처리 종료 후 실행될 콜백함수(then,catch)담고 있는 객체이다.
	jQuery Deferred 는 객체의 비동기식 처리에 Promise 객체를 연계하여 그 상태를 전파하는 것으로 Promise를 구현한 jQuery 객체이다.
	Deferred 객체는 상태를 가지고 있는데 이는 비동기식 처리의 상태가 변경되는 시점에 특정 함수(resolve(),reject())를 호출하여
	Deferred 객체에 상태를 부여하기 때문이다. */
	
	//비밀번호 체크
	function pwdCheck(r_num, pwd, msg) {
		var def = new $.Deferred();
		
		$.ajax({
			url: "/replies/pwdConfirm.json",
			type: "POST",
			data : "r_num="+r_num+"&r_pwd="+pwd.val(),
			dataType: "text",
			error : function() {
				alert("시스템 오류 입니다. 관리자에게 문의 하세요.")
			},
			success : function(resultData) {
				console.log("resultData : "+resultData);
				if(resultData==0){
					msg.addClass("msg_error");
					msg.text("입력한 비밀번호가 일치하지 않습니다.");
					pwd.select();
				}else if(resultData==1){
					def.resolve(resultData);
					$(pwd).parents("div.panel .panel-heading . pwdArea").remove();
				}
				
			}
		});
		return def.promise();
	}
</script>
</head>
<body>
	<div id="replyContainer">

		<p>
			<button type="button" class="btn btn-primary" id="replyInsertFormBtn">댓글
				등록</button>
		</p>
		<%--리스트 영역 --%>
		<div id="replyList">
		</div>

		<%--등록 화면 영역(modal) --%>
		<div class="modal fade" id="replyMadel" tabindex="-1" role="dialog"
			aria-labelledby="replyModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="replyMadalLabel">댓글 등록</h4>
					</div>
					<div class="modal-body">
						<form id="replyForm" name="replyForm">
							<div class="form-group">
								<label for="recipient-name" class="control-label">작성자: </label>
								<input type="text" class="form-control" id="r_name"
									name="r_name" maxlength="5" placeholder=""/>
							</div>
							<div class="form-group">
								<label for="message-text" class="control-label">글내용: </label>
								<textarea class="form-control" id="r_content" name="r_content"
									rows="5"></textarea>
							</div>
							<div class="form-group">
								<label for="message-text" class="control-label">비밀번호: </label> <input
									type="password" class="form-control" id="r_pwd" name="r_pwd" />
							</div>
						</form>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
						<button type="button" class="btn btn-primary" id="replyInsertBtn">등록</button>
					</div>
				</div>
			</div>
		</div>
		<%--등록 화면 영역(modal) 종료--%>
		
		<%--수정 삭제 클릭시 비밀번호 재확인 모달폼 --%>
		<div class="modal fade" id="chkForm" tabindex="-1" role="dialog"
			aria-labelledby="replyModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="replyMadalLabel">비밀번호 재확인</h4>
					</div>
					<div class="modal-body">
						<form id="replyForm2" name="replyForm2">
							<label>비밀번호를 재입력해주세요.</label>&nbsp;&nbsp;
							<input type="password" id="r_pwd1" name="r_pwd" maxlength="18">
							<label id="pwdMsg"></label>
						</form>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
						<button type="button" class="btn btn-primary" id="replypwdConfirm">확인</button>
					</div>
				</div>
			</div>
		</div>
		<%--수정 삭제 클릭시 비밀번호 재확인 모달폼 종료 --%>
		
		<%--업데이트용 모달폼 --%>
		<div class="modal fade" id="updateModalForm" tabindex="-1" role="dialog"
			aria-labelledby="replyModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="replyMadalLabel">수정 폼입니다.</h4>
					</div>
					<div class="modal-body">
						<form id="updateForm" name="updateForm">
							<div class="form-group">
								<label for="recipient-name" class="control-label">작성자: <span id="r_name2"></span></label>
							</div>
							<div class="form-group">
								<label for="message-text" class="control-label">글내용: </label>
								<textarea class="form-control" id="r_content2" name="r_content1"
									rows="5"></textarea>
							</div>
							<div class="form-group">
								<label for="message-text" class="control-label">비밀번호: </label> <input
									type="password" class="form-control" id="r_pwd2" name="r_pwd1" />
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
						<button type="button" class="btn btn-primary" id="updateBtn">수정</button>
					</div>
				</div>
			</div>
		</div>
		<%--업데이트용 모달폼  종료--%>
	</div>
</body>
</html>