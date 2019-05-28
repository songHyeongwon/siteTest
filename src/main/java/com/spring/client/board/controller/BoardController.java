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
	/*어노테이션 @AllArgsConstructor = 모든 필드변수를 의존성 주입시켜준다. 아니면 Setter쓰시던가*/
	private BoardService boardService;
	
	//글목록 구현하기
	@RequestMapping(value="/boardList",method=RequestMethod.GET)
	public String boardList(@ModelAttribute("data") BoardVO bvo,Model model) {
		log.info("boardList 호출 성공");
		//log.info("keyword : "+bvo.getKeyword());
		//log.info("search : "+bvo.getSearch());
		
		
		List<BoardVO> boardList = boardService.boardList(bvo);
		model.addAttribute("boardList",boardList);
		
		//전체 레코드 수 구현
		int total = boardService.boardListCnt(bvo);
		//int to = boardList.size();
		//이래도 되지 않나? = 검색하면 10개 단위로만 가져와서 안돼더라~~
		model.addAttribute("pageMaker",new PageDTO(bvo,total));
		return "board/boardList";
	}
	
	//글쓰기 폼 출력하기
	@RequestMapping(value="/writeForm")
	public String writeForm(@ModelAttribute("data") BoardVO bvo) {
		log.info("writeForm 호출 성공");
		
		return "board/writeForm";
	}
	
	//글 insert 구현하기
	@RequestMapping(value="/boardInsert", method=RequestMethod.POST)
	//@PostMapping("/boardInsert")
	public String boardInsert(@ModelAttribute BoardVO bvo, Model model) {
		log.info("boardInsert 호출 성공");
		
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
		//redirect: 를 쓰면 스프링 내부에서 자동적으로 response.sendRedirect(url)를 호출해준다.
		return "redirect:"+url;
	}
	
	//글 상세정보 구현
	@RequestMapping(value="/boardDetail", method=RequestMethod.GET)
	public String boardDetail(@ModelAttribute("data") BoardVO bvo,Model model) {
		log.info("boardDetail 호출 성공");
		log.info("b_num = "+bvo);
		
		BoardVO detail = boardService.boardDetail(bvo);
		model.addAttribute("detail",detail);
		
		return "board/boardDetail";
	}
	
	@ResponseBody
	@RequestMapping(value="/pwdConfirm", method=RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String pwdConfirm(BoardVO bvo) {
		log.info("pwdConfirm 호출 성공");
		String value = "";
		
		//아래 변수에는 입력 성공에 대한 선택값 저장 (1 or 0)
		int result = boardService.pwdConfirm(bvo);
		if(result==1) {
			value="성공";
		}else {
			value="실패";
		}
		log.info("result = "+result);
		log.info("value = "+value);
		return value;
		
	}
	
	@RequestMapping(value="/updateForm")
	public String updateForm(@ModelAttribute("data") BoardVO bvo, Model model) {
		log.info("updateForm 호출 성공");
		log.info("b_num = "+bvo.getB_num());
		
		BoardVO updateDate = boardService.updateForm(bvo);
		
		model.addAttribute("updateData", updateDate);
		return "board/updateForm";
	}
	
	@RequestMapping(value="/boardUpdate", method=RequestMethod.POST)
	public String updateForm(@ModelAttribute BoardVO bvo, RedirectAttributes ras) {
		log.info("boardUpdate 호출 성공");
		
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
		log.info("boardDelete 호출 성공");
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
		log.info("replyCnt 호출 성공");
		int result = 0;
		result = boardService.replyCnt(b_num);
		return result+"";
	}
}
