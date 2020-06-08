package com.ahn.test.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ahn.test.HomeController;
import com.ahn.test.users.UsersDto;
import com.ahn.test.util.PageModule;

@Controller
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private AdminService service;
	
	@RequestMapping("/users/private/admin.do")
	public ModelAndView admin(ModelAndView mView) {
		service.getList(mView);
		mView.setViewName("users/private/admin.page");
		return mView;
	}
	
	@RequestMapping("/users/private/test.do")
	public String testPage() {
		return "users/private/test.page";
	}
	@RequestMapping("/admin/usersList")
	public ModelAndView usersList(@RequestParam(defaultValue="user_id") String searchOption,
									@RequestParam(defaultValue="" )String keyword,
									@RequestParam(defaultValue="1") int curPage,
									ModelAndView mView) throws Exception {
		// 레코드의 갯수 계산
		int count = service.countUser(searchOption, keyword);
		// 페이지 나누기 관련 처리
		PageModule pagingDto = new PageModule(count, curPage);
		int start = pagingDto.getPageBegin();
		int end = pagingDto.getPageEnd();
		
		List<UsersDto> list = service.getUsersList(start, end, searchOption, keyword);
		
		// 데이터를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list); // list
		map.put("count", count); // 레코드의 갯수
		map.put("searchOption", searchOption); // 검색옵션
		map.put("keyword", keyword); // 검색키워드
		map.put("pagingDto", pagingDto);
		
		mView.addObject("map", map); // 맵에 저장된 데이터를 mView에 저장
		mView.setViewName("admin/usersList.page"); // 뷰  설정
		return mView; // list.jsp로 List가 전달된다.
	}
}
