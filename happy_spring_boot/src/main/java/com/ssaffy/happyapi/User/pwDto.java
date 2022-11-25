package com.ssaffy.happyapi.User;

public class pwDto {
	int status;
	String pw;
	public pwDto() {
		super();
	}
	public pwDto(int status, String pw) {
		super();
		this.status = status;
		this.pw = pw;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	
}
