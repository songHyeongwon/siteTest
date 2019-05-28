package com.spring.client.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.client.board.dao.BoardDao;
import com.spring.client.board.vo.BoardVO;
import com.spring.client.reply.dao.ReplyDao;
import com.spring.client.reply.vo.ReplyVO;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
@Log4j
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	@Setter(onMethod_=@Autowired)
	private BoardDao boardDao;
	private ReplyDao replyDao;
	
	@Override
	public List<BoardVO> boardList(BoardVO bvo) {
		log.info("boardList.................");
		List<BoardVO> myList = null;
		myList = boardDao.boardList(bvo);
		return myList;
	}

	@Override
	public int boardInsert(BoardVO bvo) {
		int result = 0;
		
		//예외를 발생시킬꺼
		/*if(bvo.getB_num()==0) {
			throw new IllegalArgumentException("0번글은 등록할수 없습니다.");
		}*/
		result = boardDao.boardInsert(bvo);
		return result;
	}

	@Override
	public BoardVO boardDetail(BoardVO bvo) {
		BoardVO detail = new BoardVO();
		detail = boardDao.boardDetail(bvo);
		if(detail!=null) {
			detail.setB_content(detail.getB_content().toString().replaceAll("\n", "<br>"));
		}
		return detail;
	}
	
	//비밀번호 확인 구현
	@Override
	public int pwdConfirm(BoardVO bvo) {
		int result = 0;
		result = boardDao.pwdConfirm(bvo);
		return result;
	}
	
	//창이동시 값 세팅용
	@Override
	public BoardVO updateForm(BoardVO bvo) {
		BoardVO detail = null;
		detail = boardDao.boardDetail(bvo); 
		return detail;
	}
	
	//글 수정 DAO 접속
	@Override
	public int boardUpdate(BoardVO bvo) {
		int result = 0;
		result = boardDao.boardUpdate(bvo);
		return result;
	}

	@Override
	public int boardDelete(int b_num) {
		int result = 0;
		result = boardDao.boardDelete(b_num);
		return result;
	}

	@Override
	public int replyCnt(int b_num) {
		int result = 0;
		List<ReplyVO> list = replyDao.replyList(b_num);
		if(!list.isEmpty()) {
			result = list.size();
		}else {
			result = 0;
		}
		//result = replyDao.replyCnt(b_num);
		return result;
	}

	@Override
	public int boardListCnt(BoardVO bvo) {
		int result = 0;
		result = boardDao.boardListCnt(bvo);
		return result;
	}
}
