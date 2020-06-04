package com.ahn.test.users;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class UsersServiceImpl implements UsersService{
	@Autowired
	private SqlSession sqlSession;
	@Override
	public void addUser(UsersDto dto) {
		//비밀번호 암호화.
		String encodedPwd=new BCryptPasswordEncoder().encode(dto.getUser_pwd());
		//암호화된 비밀번호를 UsersDto 에 다시 넣어준다.
		dto.setUser_pwd(encodedPwd);
		sqlSession.insert("users.insert", dto);
	}
	
	@Override
	public void validUser(UsersDto dto, HttpSession session) {
		//아이디 비밀번호가 유효한지 여부 
		boolean isValid=false;
		//아이디를 이용해서 저장된 비밀 번호를 읽어온다. 
		UsersDto dto2 = sqlSession.selectOne("users.getPwdHash", dto.getUser_id());
		String pwdHash = dto2.getUser_pwd();
		if(pwdHash != null) {//비밀번호가 존재하고 
			//입력한 비밀번호와 일치 하다면 로그인 성공
			isValid=BCrypt.checkpw(dto.getUser_pwd(), pwdHash);
		}
		if(isValid) {
			//로그인 처리를 한다.
			session.setAttribute("id", dto.getUser_id());
			session.setAttribute("auth", dto2.getUser_authority_code());
		}
	}
}
