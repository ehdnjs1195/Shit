package com.ahn.test.board;

public class BoardDto {
	private int board_num;
	private String board_title;
	private String board_writer;
	private String board_content;
	private int board_view_count;
	private String board_regdate;
	private String board_type_code;
	private String board_ori_num;
	private int reply_order_num;
	private int reply_depth_num;
	public BoardDto() {}
	public BoardDto(int board_num, String board_title, String board_writer, String board_content, int board_view_count,
			String board_regdate, String board_type_code, String board_ori_num, int reply_order_num,
			int reply_depth_num) {
		super();
		this.board_num = board_num;
		this.board_title = board_title;
		this.board_writer = board_writer;
		this.board_content = board_content;
		this.board_view_count = board_view_count;
		this.board_regdate = board_regdate;
		this.board_type_code = board_type_code;
		this.board_ori_num = board_ori_num;
		this.reply_order_num = reply_order_num;
		this.reply_depth_num = reply_depth_num;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_writer() {
		return board_writer;
	}
	public void setBoard_writer(String board_writer) {
		this.board_writer = board_writer;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public int getBoard_view_count() {
		return board_view_count;
	}
	public void setBoard_view_count(int board_view_count) {
		this.board_view_count = board_view_count;
	}
	public String getBoard_regdate() {
		return board_regdate;
	}
	public void setBoard_regdate(String board_regdate) {
		this.board_regdate = board_regdate;
	}
	public String getBoard_type_code() {
		return board_type_code;
	}
	public void setBoard_type_code(String board_type_code) {
		this.board_type_code = board_type_code;
	}
	public String getBoard_ori_num() {
		return board_ori_num;
	}
	public void setBoard_ori_num(String board_ori_num) {
		this.board_ori_num = board_ori_num;
	}
	public int getReply_order_num() {
		return reply_order_num;
	}
	public void setReply_order_num(int reply_order_num) {
		this.reply_order_num = reply_order_num;
	}
	public int getReply_depth_num() {
		return reply_depth_num;
	}
	public void setReply_depth_num(int reply_depth_num) {
		this.reply_depth_num = reply_depth_num;
	}
	
}
