package com.ssaffy.happyapi.House;

public class AreaVO {
	String sido;
	String gugun;
	String dong;
	int year;
	int month;
	public AreaVO( ) {
		 
	}
	public AreaVO(String sido, String gugun, String dong,int year,int month) {
		super();
		this.sido = sido;
		this.gugun = gugun;
		this.dong = dong;
		this.year=year;
		this.month=month;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getGugun() {
		return gugun;
	}
	public void setGugun(String gugun) {
		this.gugun = gugun;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	
	
}
