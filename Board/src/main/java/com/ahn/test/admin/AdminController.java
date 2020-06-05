package com.ahn.test.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
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
}
