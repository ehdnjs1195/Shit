package com.ahn.test.board;

import java.util.List;

public interface BoardService2 {

	//글의 갯수 
	public int getCount(BoardDto dto);
	//글의 목록
	public List<BoardDto> getList(BoardDto dto);
}
