package com.ahn.test.users;

import javax.servlet.http.HttpSession;


public interface UsersService {

	public void addUser(UsersDto dto);
	public void validUser(UsersDto dto, HttpSession session);
}
