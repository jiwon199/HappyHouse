package com.ssaffy.happyapi.Community.Board;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
	@Autowired
	BoardMapper mapper;

	public List<BoardDto> getList() {
		return mapper.getList();
	}

	public void write(HashMap<String, String> map) {
		mapper.write(map);
	}

	public void hitting(HashMap<String, String> map) {
		mapper.hitting(map);
	}

	public void delete(int articleNO) {
		mapper.delete(articleNO);
	}

	public void update(HashMap<String, String> map) {
		mapper.update(map);
	}

	public int getTotalBoardCnt() {
		// TODO Auto-generated method stub
		return mapper.getTotalBoardCnt();
	}

	public List<BoardDto> getPageList(int offset) {
		return mapper.getPageList(offset);
	}

	public int loadNo() {
		return mapper.loadNo();
	}

	public int totalPage() {
		return mapper.totalPage();
	}

	public List<BoardDto> getPageList2(SearchDto searchDto) {
		return mapper.getPageList2(searchDto);
	}

	public List<BoardDto> getPageList3(SearchDto searchDto) {
		// TODO Auto-generated method stub
		return mapper.getPageList3(searchDto);
	}

	public int totalPage2(String input) {
		return mapper.totalPage2(input);
	}

	public int totalPage3(String input) {
		return mapper.totalPage3(input);
	}

	public void edit(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		mapper.edit(map);
	}
	
	 
	
	

}
