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
	private SqlSession session;
	
	@Override
	public List<BoardVO> listAll(int start, int end, String searchOption, String keyword) {
		// 검색옵션, 키워드 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		// BETWEEN #{start}, #{end}에 입력될 값
		map.put("start", start);
		map.put("end", end);
		return session.selectList("board.listAll", map);
	}
	
	// 07. 게시글 레코드 갯수
	@Override
	public int countArticle(String searchOption, String keyword) throws Exception {
		// 검색옵션, 키워드 맵에 저장
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		return session.selectOne("board.countArticle", map);
	}
}
