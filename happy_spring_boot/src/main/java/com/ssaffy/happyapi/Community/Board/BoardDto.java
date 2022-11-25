package com.ssaffy.happyapi.Community.Board;

public class BoardDto {
	int articleNo;
	String title;
	String content;
	String writer;
	int hit;
	String regTime;
	public BoardDto() {
		super();
	}
	public BoardDto(int articleNo, String title, String content, String writer, int hit, String regTime) {
		super();
		this.articleNo = articleNo;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.hit = hit;
		this.regTime = regTime;
	}
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	
}