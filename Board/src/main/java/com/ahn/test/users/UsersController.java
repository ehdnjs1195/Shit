package com.ahn.test.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ahn.test.HomeController;

@Controller
public class UsersController {
	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
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
		logger.info("로그인 :: ["+dto.getUser_id()+"] ");
		service.validUser(dto, request.getSession());
		return "redirect:/";
	}
	
	//로그아웃 처리
	@RequestMapping("/users/logout")
	public String logout(HttpSession session) {
		logger.info("로그아웃 :: ["+session.getAttribute("id")+"] ");
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
