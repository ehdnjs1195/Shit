package com.ahn.test.admin;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.ahn.test.users.UsersDto;

@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void getList(ModelAndView mView) {
		List<UsersDto> list = sqlSession.selectList("admin.getList");
		mView.addObject("list",list);
	}
}
