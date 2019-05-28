package com.spring.client.reply.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.client.reply.service.ReplyService;
import com.spring.client.reply.vo.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//Ŭ���� Controller �� ResponseBody�� ����
@RestController
@RequestMapping(value = "/replies/*")

//������
//@AllArgsConstructor
@Log4j
public class ReplyController {
	// ������
	@Setter(onMethod_ = @Autowired)
	private ReplyService replyService;

	// ���� ��ȸ
	@GetMapping(value = "/all/{b_num}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<List<ReplyVO>> replyList(@PathVariable("b_num") Integer b_num) {
		log.info("list ȣ�� ����");
		ResponseEntity<List<ReplyVO>> entity = null;
		entity = new ResponseEntity<>(replyService.replyList(b_num), HttpStatus.OK);
		return entity;
	}

	// ���� �Է�
	@RequestMapping(value = "/replyInsert", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> replyInsert(@RequestBody ReplyVO rvo) {
		log.info("replyInsert ȣ�� ����");
		log.info("ReplyVO : " + rvo);
		int result = 0;

		result = replyService.replyInsert(rvo);
		return result == 1 ? new ResponseEntity<String>("SUCCESS", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ��� ��й�ȣ Ȯ��
	@RequestMapping(value = "/pwdConfirm")
	public ResponseEntity<Integer> pwdConfirm(@ModelAttribute ReplyVO rvo) {
		log.info("pwdConfirm ȣ�� ����");
		ResponseEntity<Integer> entity = null;
		int result = 0;

		result = replyService.pwdConfirm(rvo);
		entity = new ResponseEntity<Integer>(result, HttpStatus.OK);
		log.info(entity);
		return entity;
	}

	// ��� ���� �����ϱ�
	@DeleteMapping(value = "/{r_num}/{b_num}", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> replyDelete(@PathVariable("r_num") Integer r_num, @PathVariable("b_num") Integer b_num) {
		log.info("replyDelete ȣ�� ����");
		log.info("b_num = "+b_num);
		int result = replyService.replyDelete(r_num, b_num);
		return result == 1 ? new ResponseEntity<String>("SUCCESS", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ���� �� ��� ���� ��ȸ�ϱ�
	@GetMapping(value = "/{r_num}", produces = { MediaType.APPLICATION_XHTML_XML_VALUE,
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<ReplyVO> replySelect(@PathVariable("r_num") Integer r_num) {
		log.info("replySelect ȣ�� ����");
		ResponseEntity<ReplyVO> entity = null;
		entity = new ResponseEntity<ReplyVO>(replyService.replySelect(r_num), HttpStatus.OK);
		return entity;
	}

	// ��� ������Ʈ �����ϱ�
	@PutMapping(value = "/{r_num}", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> replyUpdate(@RequestBody ReplyVO rvo, @PathVariable("r_num") Integer r_num) {
		log.info("replyUpdate ȣ�� ����");
		log.info("�Ķ���� �� = " + rvo + " "+r_num);

		int result = replyService.replyUpdate(rvo);
		return result == 1 ? new ResponseEntity<String>("SUCCESS", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
