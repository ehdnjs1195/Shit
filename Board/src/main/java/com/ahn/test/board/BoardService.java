package com.ahn.test.board;

import java.util.List;

public interface BoardService {

	public List<BoardVO> listAll(int start, int end, String searchOption, String keyword);
	public int countArticle(String searchOption, String keyword) throws Exception;

}
