package com.ssaffy.happyapi.House;

public class AvgDongVO {
	int avg;
	String dongName;
	public AvgDongVO() {
		
	}
	
	public AvgDongVO(int avg, String dongName) {
		super();
		this.avg = avg;
		this.dongName = dongName;
	}

	public int getAvg() {
		return avg;
	}
	public void setAvg(int avg) {
		this.avg = avg;
	}
	public String getDongName() {
		return dongName;
	}
	public void setDongName(String dongName) {
		this.dongName = dongName;
	}
}