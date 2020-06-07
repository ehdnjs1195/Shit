package com.ahn.test.users;

import javax.servlet.http.HttpSession;


public interface UsersService {

	public void addUser(UsersDto dto);	//회원추가
	public void validUser(UsersDto dto, HttpSession session);	//로그인확인
	public String findUserId(UsersDto dto); //아이디 찾기
	public String findUserPwd(UsersDto dto); //비밀번호 찾기
}
