package com.ahn.test.admin;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.ahn.test.users.UsersDto;

public interface AdminService {

	public void getList(ModelAndView mView);
	//회원 목록
	public List<UsersDto> getUsersList(int start,int end,String searchOption,String keyword) throws Exception;
	//회원 수
	public int countUser(String searchOption,String keyword) throws Exception;
}
