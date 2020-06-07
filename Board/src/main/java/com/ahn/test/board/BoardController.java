package com.ahn.test.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ahn.test.util.PageModule;
@Controller
public class BoardController {
	@Autowired
	private BoardService service;
	
	@RequestMapping("/board/boardList")
	public ModelAndView usersList(@RequestParam(defaultValue="board_title") String searchOption,
									@RequestParam(defaultValue="" )String keyword,
									@RequestParam(defaultValue="1") int curPage,
									ModelAndView mView) throws Exception {
		// 레코드의 갯수 계산
		int count = service.countBoard(searchOption, keyword);
		// 페이지 나누기 관련 처리
		PageModule pagingDto = new PageModule(count, curPage);
		int start = pagingDto.getPageBegin();
		int end = pagingDto.getPageEnd();
		
		List<BoardDto> list = service.getBoardList(start, end, searchOption, keyword);
		
		// 데이터를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list); // list
		map.put("count", count); // 레코드의 갯수
		map.put("searchOption", searchOption); // 검색옵션
		map.put("keyword", keyword); // 검색키워드
		map.put("pagingDto", pagingDto);
		
	
		/*mav.addObject("list", list); // 데이터를 저장
		mav.addObject("count", count);
		mav.addObject("searchOption", searchOption);
		mav.addObject("keyword", keyword);*/
		mView.addObject("map", map); // 맵에 저장된 데이터를 mav에 저장
		mView.setViewName("board/boardList.page"); // 뷰를 list.jsp로 설정
		return mView; // list.jsp로 List가 전달된다.
	}
}
