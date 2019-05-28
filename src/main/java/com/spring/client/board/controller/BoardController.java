package com.spring.client.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.client.board.service.BoardService;
import com.spring.client.board.vo.BoardVO;
import com.spring.common.vo.PageDTO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*")
@Log4j
@AllArgsConstructor
public class BoardController{
	/*������̼� @AllArgsConstructor = ��� �ʵ庯���� ������ ���Խ����ش�. �ƴϸ� Setter���ô���*/
	private BoardService boardService;
	
	//�۸�� �����ϱ�
	@RequestMapping(value="/boardList",method=RequestMethod.GET)
	public String boardList(@ModelAttribute("data") BoardVO bvo,Model model) {
		log.info("boardList ȣ�� ����");
		//log.info("keyword : "+bvo.getKeyword());
		//log.info("search : "+bvo.getSearch());
		
		
		List<BoardVO> boardList = boardService.boardList(bvo);
		model.addAttribute("boardList",boardList);
		
		//��ü ���ڵ� �� ����
		int total = boardService.boardListCnt(bvo);
		//int to = boardList.size();
		//�̷��� ���� �ʳ�? = �˻��ϸ� 10�� �����θ� �����ͼ� �ȵŴ���~~
		model.addAttribute("pageMaker",new PageDTO(bvo,total));
		return "board/boardList";
	}
	
	//�۾��� �� ����ϱ�
	@RequestMapping(value="/writeForm")
	public String writeForm(@ModelAttribute("data") BoardVO bvo) {
		log.info("writeForm ȣ�� ����");
		
		return "board/writeForm";
	}
	
	//�� insert �����ϱ�
	@RequestMapping(value="/boardInsert", method=RequestMethod.POST)
	//@PostMapping("/boardInsert")
	public String boardInsert(@ModelAttribute BoardVO bvo, Model model) {
		log.info("boardInsert ȣ�� ����");
		
		int result = 0;
		String url ="";
		
		result = boardService.boardInsert(bvo);
		/*if(result == 1) {
			url ="/board/boardList";
		}else {
			model.addAttribute("code", 1);
			url = "/board/writeForm";
		}*/
		if(result == 1) {
			url ="/board/boardList";
		}
		//redirect: �� ���� ������ ���ο��� �ڵ������� response.sendRedirect(url)�� ȣ�����ش�.
		return "redirect:"+url;
	}
	
	//�� ������ ����
	@RequestMapping(value="/boardDetail", method=RequestMethod.GET)
	public String boardDetail(@ModelAttribute("data") BoardVO bvo,Model model) {
		log.info("boardDetail ȣ�� ����");
		log.info("b_num = "+bvo);
		
		BoardVO detail = boardService.boardDetail(bvo);
		model.addAttribute("detail",detail);
		
		return "board/boardDetail";
	}
	
	@ResponseBody
	@RequestMapping(value="/pwdConfirm", method=RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String pwdConfirm(BoardVO bvo) {
		log.info("pwdConfirm ȣ�� ����");
		String value = "";
		
		//�Ʒ� �������� �Է� ������ ���� ���ð� ���� (1 or 0)
		int result = boardService.pwdConfirm(bvo);
		if(result==1) {
			value="����";
		}else {
			value="����";
		}
		log.info("result = "+result);
		log.info("value = "+value);
		return value;
		
	}
	
	@RequestMapping(value="/updateForm")
	public String updateForm(@ModelAttribute("data") BoardVO bvo, Model model) {
		log.info("updateForm ȣ�� ����");
		log.info("b_num = "+bvo.getB_num());
		
		BoardVO updateDate = boardService.updateForm(bvo);
		
		model.addAttribute("updateData", updateDate);
		return "board/updateForm";
	}
	
	@RequestMapping(value="/boardUpdate", method=RequestMethod.POST)
	public String updateForm(@ModelAttribute BoardVO bvo, RedirectAttributes ras) {
		log.info("boardUpdate ȣ�� ����");
		
		int result = 0;
		String url = "";
		
		result = boardService.boardUpdate(bvo);
		ras.addFlashAttribute("data",bvo);
		
		if(result==1) {
			//url="/board/boardDetail?b_num="+bvo.getB_num();
			url="/board/boardDetail";
		}else {
			//url="/board/updateForm?b_num="+bvo.getB_num();
			url="/board/updateForm";
		}
		return "redirect:"+url;
	}
	
	@RequestMapping(value="/boardDelete")
	public String boardDelete(@ModelAttribute BoardVO bvo) {
		log.info("boardDelete ȣ�� ����");
		int result = 0;
		String url = "";
		result = boardService.boardDelete(bvo.getB_num());
		
		if(result ==1) {
			url="/board/boardList";
		}else {
			url="/board/boardDetail?b_num="+bvo.getB_num();
		}
		
		return "redirect:"+url;
	}
	
	@ResponseBody
	@RequestMapping(value="/replyCnt")
	public  String replyCnt(@RequestParam("b_num") int b_num) {
		log.info("replyCnt ȣ�� ����");
		int result = 0;
		result = boardService.replyCnt(b_num);
		return result+"";
	}
}
