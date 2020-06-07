package com.ahn.test.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ahn.test.util.Pagging;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private Pagging pagging;
	
	@RequestMapping("/users/private/list.do")
	// @RequestParam(defaultValue="") ==> 기본값 할당
	public ModelAndView list(@RequestParam(defaultValue="title") String searchOption,
							@RequestParam(defaultValue="") String keyword,
							@RequestParam(defaultValue="1") int curPage) throws Exception{
		// 레코드의 갯수 계산
		int count = boardService.countArticle(searchOption, keyword);
		// 페이지 나누기 관련 처리
		BoardPager boardPager = new BoardPager(count, curPage);
		int start = boardPager.getPageBegin();
		int end = boardPager.getPageEnd();
		
		List<BoardVO> list = boardService.listAll(start, end, searchOption, keyword);
		
		// 데이터를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list); // list
		map.put("count", count); // 레코드의 갯수
		map.put("searchOption", searchOption); // 검색옵션
		map.put("keyword", keyword); // 검색키워드
		map.put("boardPager", boardPager);
		// ModelAndView - 모델과 뷰
		ModelAndView mav = new ModelAndView();
		/*mav.addObject("list", list); // 데이터를 저장
		mav.addObject("count", count);
		mav.addObject("searchOption", searchOption);
		mav.addObject("keyword", keyword);*/
		mav.addObject("map", map); // 맵에 저장된 데이터를 mav에 저장
		mav.setViewName("users/board/list.page"); // 뷰를 list.jsp로 설정
		return mav; // list.jsp로 List가 전달된다.
	}
	
	//글목록 요청처리
	@RequestMapping("/users/private/list2.do")
	public ModelAndView list(HttpServletRequest request){
		// HttpServletRequest 객체를 서비스에 넘겨 주면서
		// 비즈니스 로직을 수행하고 
		pagging.getList(request);
		
		// view page 로 forward 이동해서 글 목록 출력하기 
		return new ModelAndView("users/private/list2.page");
	}
}
