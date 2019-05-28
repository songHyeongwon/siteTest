package com.spring.client.gallery.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.client.gallery.service.GalleryService;
import com.spring.client.gallery.vo.GalleryVO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping(value="/gallery/*")
@Log4j
@AllArgsConstructor
public class GalleryController {
	private GalleryService galleryService;
	//파라미터를 바인딩할 때 자동으로 호출되는 @InitBinder를 이용해서 변환을 처리할수 있다.
	@InitBinder
	public void initBiner(WebDataBinder binder) {
		binder.registerCustomEditor(MultipartFile.class, "file", new StringTrimmerEditor(true));
	}
	
	@GetMapping("/galleryList")
	public String gelleryList() {
		log.info("galleryList 호출 성공");
		return "gallery/galleryList";
	}
	
	//갤러리 게시판 글쓰기 구현하기
	@ResponseBody
	@RequestMapping(value="/galleryInsert", 
	method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String galleryInsert(@ModelAttribute GalleryVO gvo) {
		log.info("galleryInsert 호출 성공");
		
		log.info("file name : "+gvo.getFile().getOriginalFilename());
		log.info("들어가는 값 ==============="+gvo);
		String value = "";
		int result = 0;
		
		result = galleryService.galleryInsert(gvo);
		if(result ==1) {
			value = "성공";
		}else {
			value = "실패";
		}
		return value;
	}
	
	//갤러리 목록 구현하기
	@ResponseBody
	@RequestMapping(value="/galleryData", produces="text/plain; charset=UTF-8")
	public String galleryData(GalleryVO gvo) {
		log.info("galleryData 호출 성공");
		String listData = galleryService.galleryList(gvo);
		return listData;
	}
	
	//비밀번호 확인 컨트롤러
	@ResponseBody
	@RequestMapping(value="/pwdConfirm" , method=RequestMethod.POST, produces=MediaType.TEXT_PLAIN_VALUE)
	public String pwdConfirm(@ModelAttribute GalleryVO gvo) {
		log.info("pwdConfirm 호출 성공");
		//아래 변수에는 입력 성공에 대한 상태값 저장(1 or 0)
		int result =0;
		result = galleryService.pwdConfirm(gvo);
		log.info("result = "+result);
		
		return String.valueOf(result);
	}
	
	//삭제컨트롤러
	@ResponseBody
	@RequestMapping(value="/galleryDelect",produces="text/plain; charset=UTF-8", method=RequestMethod.POST)
	public String galleryDelect(@ModelAttribute GalleryVO gvo) {
		int result = 0;
		String value = "";
		result = galleryService.galleryDelete(gvo);
		if(result==1) {
			value="성공";
		}else {
			value="실패";
		}
		return value;
	}
	//갤러리의 세부내역을 뽑아냄
	@ResponseBody
	@GetMapping(value="/galleryDetail", produces="text/plain; charset=UTF-8")
	public String galleryDetail(@ModelAttribute GalleryVO gvo) {
		log.info("galleryDetail 호출 성공");
		
		String detail = galleryService.galleryDetail(gvo);
		return detail;
	}
	
	@ResponseBody
	@PostMapping(value="/galleryUpdate", produces="text/plain; charset=UTF-8")
	public String galleryUpdate(@ModelAttribute GalleryVO gvo) {
		log.info("galleryUpdate 호출 성공");
		
		String value = "";
		int result = 0;
		result = galleryService.galleryUpdate(gvo);
		if(result==1) {
			value = "성공";
		}else {
			value = "실패";
		}
		return value;
	}
}
