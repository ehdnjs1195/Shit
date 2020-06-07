package com.ahn.test.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<BoardDto> getBoardList(int start, int end, String searchOption, String keyword) throws Exception {
		//검색 옵션, 키워드 맵에 저장
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		//BETWEEN #{start} ,#{end}에 입력될 값을 맵에 저장
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList("board.selectBoardList",map);
	}

	@Override
	public int countBoard(String searchOption, String keyword) throws Exception {
		// 검색옵션, 키워드 맵에 저장
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		return sqlSession.selectOne("board.countBoard",map);
	}

}
