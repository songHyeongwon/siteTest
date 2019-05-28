package com.spring.client.persistence;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.client.board.dao.BoardDao;
import com.spring.client.gallery.dao.GalleryDao;
import com.spring.client.gallery.vo.GalleryVO;
import com.spring.client.reply.dao.ReplyDao;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MybatisTests {
	@Setter(onMethod_= @Autowired)
	private BoardDao boardDao;
	@Setter(onMethod_= @Autowired)
	private ReplyDao replyDao;
	@Setter(onMethod_= @Autowired)
	private GalleryDao galleryDao;
	
	/*@Test
	public void testBoardList() {
		BoardVO bvo = new BoardVO();
		log.info(boardDao.getClass().getName());
		log.info("boardList() �޼��� ����");
		//log.info(boardDao.boardList());
		
		bvo.setPageNum(1);
		bvo.setAmount(10);
		bvo.setSearch("b_title");
		bvo.setKeyword("�׽�Ʈ");
		log.info(boardDao.boardList(bvo));
		log.info("--------------------------------------");
	}*/
	
	/*@Test
	public void testBoardInsert1() {
		BoardVO bvo = new BoardVO();
		bvo.setB_name("�ۼ���");
		bvo.setB_title("���鶧 ���� �Ǵ� ���2");
		bvo.setB_content("�ҽ��ϰ� �����̴� �ڿ��Դ� ��� ���� �Ұ����ϴ�. �ֳ��ϸ� ������ �Ұ����ϰ� ���̱� �����̴�.");
		bvo.setB_pwd("1234");
		
		log.info("BoarVO : "+bvo);
		log.info("------------------------------");
		log.info("��ȯ��: "+boardDao.boardInsert(bvo));
	}*/
	/*@Test
	public void testBoardDetail() {
		BoardVO vo = new BoardVO();
		vo.setB_num(1);
		log.info("BoarVO : "+vo);
		log.info("------------------------------");
		log.info("��ȯ��: "+boardDao.boardDetail(vo));
	}*/
	
	/*@Test
	public void testPwdConfirm() {
		BoardVO bvo = new BoardVO();
		bvo.setB_num(1);
		bvo.setB_pwd("1234");
		log.info("BoardVO = "+bvo);
		log.info("-------------------------------");
		log.info("��ȯ�� = "+boardDao.pwdConfirm(bvo));
	}*/
	
	/*@Test
	public void testBoardUpdate() {
		BoardVO bvo = new BoardVO();
		bvo.setB_content("������Ʈ ����1");
		bvo.setB_num(9999);
		bvo.setB_title("������Ʈ ����1");
		//bvo.setB_pwd("1234");
		log.info("BoardVO = "+bvo);
		log.info("-------------------------------------");
		log.info("��ȯ�� = "+boardDao.boardUpdate(bvo));
	}*/
	
	/*@Test
	public void testBoardDelect() {
		log.info("-------------------");
		log.info("��ȯ�� = "+ boardDao.boardDelete(9999));
	}*/
	
	/*@Test
	public void testReplyList() {
		log.info("------------------------");
		log.info("��ȯ�� : "+replyDao.replyList(9999));
	}*/
	/*@Test
	public void testPwdConfirm() {
		ReplyVO rvo = new ReplyVO();
		rvo.setR_pwd("1234");
		rvo.setR_num(3);
		log.info("-------------------------------");
		log.info("��ȯ�� : "+replyDao.pwdConfirm(rvo));
	}*/
	
	/*@Test
	public void testReplyDelect() {
		int r_num = 9999;
		log.info("============================================");
		log.info("��ȯ�� : "+replyDao.replyDelete(r_num));
	}*/
	
	/*@Test
	public void testReplySelect() {
		Integer r_num = 9999;
		log.info("=============================");
		ReplyVO rvo = replyDao.replySelect(r_num);
		log.info("��ȯ�� : "+rvo);
	}*/
	
	/*@Test
	public void testReplyUpdate() {
		ReplyVO rvo = new ReplyVO();
		rvo.setR_content("�����ѳ���22");
		rvo.setR_num(9999);
		//rvo.setR_pwd("1234");
		log.info("========================================");
		log.info("��ȯ�� : "+replyDao.replyUpdate(rvo));
	}*/
	
	/*@Test
	public void testBoardListCnt() {
		BoardVO bvo = new BoardVO();
		bvo.setSearch("b_title");
		bvo.setKeyword("������");
		log.info("====================================");
		log.info(boardDao.boardListCnt(bvo));
	}*/
	
	@Test
	public void testGalleryList() {
		GalleryVO gvo = null;
		List<GalleryVO> list = galleryDao.galleryList(gvo);
		log.info(list);
	}
}
