package com.ahn.test.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Override
	public List<UsersDto> getUsersList(int start, int end, String searchOption, String keyword) throws Exception {
		//검색 옵션, 키워드 맵에 저장
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		//BETWEEN #{start} ,#{end}에 입력될 값을 맵에 저장
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList("admin.selectUserList",map);
	}

	@Override
	public int countUser(String searchOption, String keyword) throws Exception {
		// 검색옵션, 키워드 맵에 저장
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		return sqlSession.selectOne("admin.countUser",map);
	}
}
