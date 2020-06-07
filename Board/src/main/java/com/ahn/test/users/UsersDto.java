package com.ahn.test.users;

import java.io.Serializable;

public class UsersDto implements Serializable{
	private String user_id;
	private String user_pwd;
	private String user_email;
	private String user_name;
	private String user_state_code;
	private String user_authority_code;
	private String user_regdate;
	private int login_count;
	private int startRowNum;
	private int endRowNum;
	private int prevNum; //이전글의 글번호를 담을 필드
	private int nextNum; //다음글의 글번호를 담을 필드
	public UsersDto() {}
	public UsersDto(String user_id, String user_pwd, String user_email, String user_name, String user_state_code,
			String user_authority_code, String user_regdate, int login_count, int startRowNum, int endRowNum,
			int prevNum, int nextNum) {
		super();
		this.user_id = user_id;
		this.user_pwd = user_pwd;
		this.user_email = user_email;
		this.user_name = user_name;
		this.user_state_code = user_state_code;
		this.user_authority_code = user_authority_code;
		this.user_regdate = user_regdate;
		this.login_count = login_count;
		this.startRowNum = startRowNum;
		this.endRowNum = endRowNum;
		this.prevNum = prevNum;
		this.nextNum = nextNum;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_state_code() {
		return user_state_code;
	}
	public void setUser_state_code(String user_state_code) {
		this.user_state_code = user_state_code;
	}
	public String getUser_authority_code() {
		return user_authority_code;
	}
	public void setUser_authority_code(String user_authority_code) {
		this.user_authority_code = user_authority_code;
	}
	public String getUser_regdate() {
		return user_regdate;
	}
	public void setUser_regdate(String user_regdate) {
		this.user_regdate = user_regdate;
	}
	public int getLogin_count() {
		return login_count;
	}
	public void setLogin_count(int login_count) {
		this.login_count = login_count;
	}
	public int getStartRowNum() {
		return startRowNum;
	}
	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}
	public int getEndRowNum() {
		return endRowNum;
	}
	public void setEndRowNum(int endRowNum) {
		this.endRowNum = endRowNum;
	}
	public int getPrevNum() {
		return prevNum;
	}
	public void setPrevNum(int prevNum) {
		this.prevNum = prevNum;
	}
	public int getNextNum() {
		return nextNum;
	}
	public void setNextNum(int nextNum) {
		this.nextNum = nextNum;
	}
	
}
