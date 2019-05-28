package com.spring.client.gallery.service;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.client.gallery.dao.GalleryDao;
import com.spring.client.gallery.vo.GalleryVO;
import com.spring.common.file.FileUploadUtil;

import lombok.AllArgsConstructor;
//import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class GalleryServiceImpl implements GalleryService{
	//@Setter(onMethod_=@Autowired)
	private GalleryDao galleryDao;

	@Override
	public int galleryInsert(GalleryVO gvo) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			if(gvo.getFile()!=null) {
				String fileName = FileUploadUtil.fileUpload(gvo.getFile(), "gallery");
				
				gvo.setG_file(fileName);
				
				String thumbName = FileUploadUtil.makeThumbnail(fileName);
				gvo.setG_thumb(thumbName);
			}
			result = galleryDao.galleryInsert(gvo);
		}catch(Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}
	//글목록 구현
	@Override
	public String galleryList(GalleryVO gvo) {
		List<GalleryVO> list = null;
		ObjectMapper mapper = new ObjectMapper();
		String listData = "";
		
		list = galleryDao.galleryList(gvo);
		try {
			listData = mapper.writeValueAsString(list);
			log.info(listData);
		}catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return listData;
	}
	@Override
	public int pwdConfirm(GalleryVO gvo) {
		int result =0;
		result = galleryDao.pwdConfirm(gvo);
		return result;
	}
	@Override
	public int galleryDelete(GalleryVO gvo) {
		int result = 0;
		try {
			GalleryVO vo = galleryDao.galleryDetail(gvo);
			FileUploadUtil.fileDelete(vo.getG_file());
			FileUploadUtil.fileDelete(vo.getG_thumb());
			result = galleryDao.galleryDelete(gvo);
		}catch(Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}
	@Override
	public String galleryDetail(GalleryVO gvo) {
		String result = "";
		GalleryVO vo = galleryDao.galleryDetail(gvo);
		try {
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(vo);
		}catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public int galleryUpdate(GalleryVO gvo) {
		int result = 0;
		try {
			//만약 첨부파일이 있다면
			if(gvo.getFile()!=null) {
				if(!gvo.getG_file().isEmpty()) {
					//기존의 첨부파일을 제거함
					FileUploadUtil.fileDelete(gvo.getG_file());
					FileUploadUtil.fileDelete(gvo.getG_thumb());
				}
				//첨부파일을 업로드하고 그 값을 g_file에 입력해준다.
				String fileName = FileUploadUtil.fileUpload(gvo.getFile(), "gallery");
				gvo.setG_file(fileName);
				//썸네일 파일을 제작하고 그 값을 g_thumb에 입력해준다.
				String thumbName = FileUploadUtil.makeThumbnail(fileName);
				gvo.setG_thumb(thumbName);
			}else {
				log.info("첨부파일 없음");
				gvo.setG_file("");
				gvo.setG_thumb("");
			}
			//비밀번호를 받아오지 않았을경우, null값이 아닌 문자 공백으로 바꾼다.
			gvo.setG_pwd((gvo.getG_pwd()==null) ? "" : gvo.getG_pwd());
			//dao Update쿼리 실행, 성공1 실패 0
			result = galleryDao.galleryUpdate(gvo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
