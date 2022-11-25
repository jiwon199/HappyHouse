package com.ssaffy.happyapi.Community.Board;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

	List<BoardDto> getList();

	void write(HashMap<String, String> map);

	void hitting(HashMap<String, String> map);

	void delete(int articleNO);

	void update(HashMap<String, String> map);

	int getTotalBoardCnt();

	List<BoardDto> getPageList(int offset);

	int loadNo();

	int totalPage();

	List<BoardDto> getPageList2(SearchDto searchDto);

	List<BoardDto> getPageList3(SearchDto searchDto);

	int totalPage2(String input);

	int totalPage3(String input);

	void edit(HashMap<String, String> map);

}
