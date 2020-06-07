package com.ahn.test.board;

import java.util.Date;

// 게시글 도메인 객체 클래스
public class BoardDto {
    // 게시글번호
    private Integer board_num;
    // 제목
    private String board_title;
    // 내용
    private String board_content;
    // 작성자
    private String board_writer;
    // 작성일자
    private Date board_regdate;
    // 조회수
    private int board_view_count;

    public BoardDto() {}
    public BoardDto(Integer board_num, String board_title, String board_content, String board_writer, Date board_regdate,
			int board_view_count) {
		super();
		this.board_num = board_num;
		this.board_title = board_title;
		this.board_content = board_content;
		this.board_writer = board_writer;
		this.board_regdate = board_regdate;
		this.board_view_count = board_view_count;
	}


	public Integer getBoard_num() {
		return board_num;
	}


	public void setBoard_num(Integer board_num) {
		this.board_num = board_num;
	}


	public String getBoard_title() {
		return board_title;
	}


	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}


	public String getBoard_content() {
		return board_content;
	}


	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}


	public String getBoard_writer() {
		return board_writer;
	}


	public void setBoard_writer(String board_writer) {
		this.board_writer = board_writer;
	}


	public Date getBoard_regdate() {
		return board_regdate;
	}


	public void setBoard_regdate(Date board_regdate) {
		this.board_regdate = board_regdate;
	}


	public int getBoard_view_count() {
		return board_view_count;
	}


	public void setBoard_view_count(int board_view_count) {
		this.board_view_count = board_view_count;
	}


	@Override
    public String toString() {
        return "BoardVO{" +
                "bno=" + board_num +
                ", title='" + board_title + '\'' +
                ", content='" + board_content + '\'' +
                ", writer='" + board_writer + '\'' +
                ", regdate=" + board_regdate +
                ", viewcnt=" + board_view_count +
                '}';
    }
}
