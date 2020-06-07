package com.ahn.test.board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahn.test.board.BoardDto;
import com.ahn.test.util.Criteria;
import com.ahn.test.util.SearchCriteria;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private SqlSession sqlSession;

    // 목록 : 기본
    @Override
    public List<BoardDto> list() throws Exception {
        return sqlSession.selectList("board.list");
    }

    // 목록 : 페이징
    @Override
    public List<BoardDto> list(Criteria criteria) throws Exception {
        return sqlSession.selectList("board.listPaging", criteria);
    }

    // 목록 전체 갯수
    @Override
    public int listCount(Criteria criteria) throws Exception {
        return sqlSession.selectOne("board.listCount", criteria);
    }

    // 목록 : 페이징 + 검색
    @Override
    public List<BoardDto> list(SearchCriteria criteria) throws Exception {
        return sqlSession.selectList("board.listPaging", criteria);
    }

    // 목록 : 전체 갯수 or 검색된 갯수
    @Override
    public int listCount(SearchCriteria criteria) throws Exception {
        return sqlSession.selectOne("board.listCount", criteria);
    }

}
