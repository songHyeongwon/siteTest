package com.spring.client.gallery.vo;

import org.springframework.web.multipart.MultipartFile;

import com.spring.common.vo.CommonVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GalleryVO extends CommonVO{

	private int g_num= 0;
	private String g_name="";
	private String g_subject="";
	private String g_content="";
	private MultipartFile file=null;
	private String g_thumb="";
	private String g_file="";
	private String g_pwd="";
	private String g_date="";
	private int g_count=0;

}
