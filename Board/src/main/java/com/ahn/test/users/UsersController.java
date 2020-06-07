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
	//회원가입
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
	//아이디 찾기 폼
	@RequestMapping("/users/findUserIdForm")
	public String findIdForm() {
		return "users/findUserIdForm.page";
	}
	//아이디 찾기
	@RequestMapping("/users/findUserId")
	public String findUserId(@ModelAttribute UsersDto dto, HttpServletRequest request) {
		String userId = service.findUserId(dto);
		request.setAttribute("userId", userId);
		return "users/confirmUserInfo.page";
	}
	//비밀번호 찾기 폼
	@RequestMapping("/users/findUserPwdForm")
	public String findPwdForm() {
		return "users/findUserPwdForm.page";
	}
	//비밀번호 찾기
	@RequestMapping("/users/findUserPwd")
	public String findUserPwd(@ModelAttribute UsersDto dto, HttpServletRequest request) {
		String userPwd=service.findUserPwd(dto);
		request.setAttribute("userPwd", userPwd);
		return "users/confirmUserInfo.page";
	}

}
