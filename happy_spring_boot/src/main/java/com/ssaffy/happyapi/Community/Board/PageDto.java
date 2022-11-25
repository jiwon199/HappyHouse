package com.ssaffy.happyapi.Community.Board;

public class PageDto {

	int limit; // 한 페이지에 몇 개 게시글 보여줄지
	double range; // 선택 가능 페이지를 몇 개 보여줄지
	double startPage; // 선택 그룹의 첫 페이지
	double endPage; // 선택 그룹의 마지막 페이지
	double currPage; // 현재 선택 페이지
	double totalCnt; // 총 게시물 수
	double totalGroup; // 총 그룹 수
	double totalPage; // 총 페이지 수
	int group; // 선택 페이지가 소속된 그룹
	 
	boolean prev; // 이전 그룹으로 이동 가능한지
	boolean next; // 다음 그룹으로 이동 가능한지

	public PageDto(int currPage, int totalCnt) {
		this.currPage = currPage;
		this.totalCnt = totalCnt;
		this.limit = 5;
		this.range = 3;
		setTotalPage();
		setTotalGroup();
		setGroup();
		setStartPage();
		setEndPage();
		 
		setPrev();
		setNext();
		 

	}

	public void setNext() {
		if (group == totalGroup)
			this.next = false;
		else
			this.next = true;
	}

	public void setPrev() {
		if (group == 1)
			this.prev = false;
		else
			this.prev = true;
	}

	 
	public void setEndPage() {
		this.endPage = startPage + range - 1;
		if (endPage > totalPage)
			this.endPage = totalPage;
	}

	public void setStartPage() {
		this.startPage = range * (group - 1) + 1;
	}

	public void setGroup() {
		double temp = currPage / totalGroup;
		this.group = (int)Math.ceil(temp);
	}

	public void setTotalGroup() {
		double temp = totalPage / range;
		this.totalGroup = Math.ceil(temp);
	}

	public void setTotalPage() {
		double temp = totalCnt / limit;
		this.totalPage = Math.ceil(temp);
	}

	 
	public void setLimit(int limit) {
		this.limit = limit;
	}

 
	public void setRange(double range) {
		this.range = range;
	}

	public double getStartPage() {
		return startPage;
	}

	public void setStartPage(double startPage) {
		this.startPage = startPage;
	}

	public double getEndPage() {
		return endPage;
	}

	public void setEndPage(double endPage) {
		this.endPage = endPage;
	}

	public double getCurrPage() {
		return currPage;
	}

	public boolean isPrev() {
		return prev;
	}

	 
	public boolean isNext() {
		return next;
	}

	public int getGroup() {
		return group;
	}

	 
 
	 
}
