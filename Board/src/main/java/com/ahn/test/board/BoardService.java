package com.ahn.test.board;

import java.util.List;

import com.ahn.test.util.Criteria;
import com.ahn.test.util.SearchCriteria;

public interface BoardService {

	
	// 목록 : 기본
    public List<BoardDto> list() throws Exception;

    // 목록 : 페이징
    public List<BoardDto> list(Criteria criteria) throws Exception;

    // 목록 전체 갯수
    public int listCount(Criteria criteria) throws Exception;

    // 목록 : 페이징 + 검색
    public List<BoardDto> list(SearchCriteria criteria) throws Exception;

    // 목록 : 전체 갯수 or 검색된 갯수
    public int listCount(SearchCriteria criteria) throws Exception;

}
