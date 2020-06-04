package com.ahn.test.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsersController {
	@Autowired
	private UsersService service;
	
	@RequestMapping("/users/signup.do")
	public String signup(UsersDto dto) {
		service.addUser(dto);
		return "home.page";
	}
	
	//로그인
	@RequestMapping("/users/login.do")
	public String login(@ModelAttribute UsersDto dto, HttpServletRequest request) {
		
		service.validUser(dto, request.getSession());
		return "redirect:/";
	}
	
	//로그아웃 처리
	@RequestMapping("/users/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

}
