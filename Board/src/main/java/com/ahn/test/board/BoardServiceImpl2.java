package com.ahn.test.board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl2 implements BoardService2{
	@Autowired
	private SqlSession session;
	@Override
	public int getCount(BoardDto dto) {

		return session.selectOne("board2.getCount", dto);
	}

	@Override
	public List<BoardDto> getList(BoardDto dto) {

		return session.selectList("board2.getList", dto);
	}

}
