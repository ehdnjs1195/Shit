package com.ahn.test.board;

import java.util.List;


public interface BoardService {

	//회원 목록
	public List<BoardDto> getBoardList(int start,int end,String searchOption,String keyword) throws Exception;
	//회원 수
	public int countBoard(String searchOption,String keyword) throws Exception;
}
