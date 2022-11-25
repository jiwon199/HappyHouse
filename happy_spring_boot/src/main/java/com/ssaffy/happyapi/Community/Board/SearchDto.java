package com.ssaffy.happyapi.Community.Board;

public class SearchDto {
	int offset;
	String input;
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public SearchDto(int offset, String input) {
		super();
		this.offset = offset;
		this.input = input;
	}
	public SearchDto() {
		super();
	}
}
