package com.ssaffy.happyapi.Community.Board;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssaffy.happyapi.Community.CommentDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/board")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BoardController {
	@Autowired
	BoardService service;
	
	//다음 페이지, 이전 페이지를 누른 경우 프론트에서 endPage+1, startPage-1을 currPage로 넣어서 전달 
	@ApiOperation(value = "현재 페이지의 페이징 정보를 가져옵니다.", response = BoardDto.class)
	@GetMapping(value = "/getPage/{currPage}")
	public PageDto getPageInfo(@PathVariable("currPage") int currPage) {
		int totalCnt=service.getTotalBoardCnt();
		PageDto page=new PageDto(currPage, totalCnt);
		 
		return page;
	}
	//다음, 이전 페이지가 있는지 여부는 응답으로 보내는 데이터에 포함
	@ApiOperation(value = "현재 페이지의 게시글을 모두 가져옵니다.", response = BoardDto.class)
	@PostMapping(value = "/getList/{currPage}")
	public List<BoardDto> getList(@PathVariable("currPage") int currPage, @RequestBody HashMap<String, String> map) {
		if (map.get("option").equals("null")) {
			int offset=(currPage - 1) * 5; //limit
			List<BoardDto> list = service.getPageList(offset); 
			return list;
		} else if (map.get("option").equals("title")){
			int offset=(currPage - 1) * 5; //limit
			SearchDto searchDto = new SearchDto(offset, map.get("input"));
			List<BoardDto> list = service.getPageList2(searchDto);
			return list;
		} else {
			int offset=(currPage - 1) * 5; //limit
			SearchDto searchDto = new SearchDto(offset, map.get("input"));
			List<BoardDto> list = service.getPageList3(searchDto);
			return list;
		}
	}
	
	
	@ApiOperation(value = "게시글을 작성합니다.")
	@PostMapping(value = "/write")
	public int write(@RequestBody HashMap<String, String> map) {
		service.write(map);
		return service.loadNo();
	}
	
	@ApiOperation(value = "조회수를 갱신합니다.")
	@PostMapping(value = "/hitting")
	public void hitting(@RequestBody HashMap<String, String> map) {
		service.hitting(map);
	}

	
	@ApiOperation(value = "게시글을 삭제합니다.")
	@PostMapping(value="/delete/{articleNO}")
	public void delete(@PathVariable("articleNO") int articleNO) {
		service.delete(articleNO);
	}
	
	@ApiOperation(value = "게시글을 수정합니다.")
	@PostMapping(value="/update")
	public void update(@RequestBody HashMap<String, String> map) {
		service.update(map);
	}
	
	@GetMapping(value="/totalPage")
	public int totalPage() {
		int page = service.totalPage();
		return page;
	}
	@PostMapping(value="/totalPage")
	public int totalPage2(@RequestBody HashMap<String, String> map) {
		int page = 0;
		if (map.get("option").equals("title")) {
			page = service.totalPage2(map.get("input"));			
		} else if (map.get("option").equals("writer")) {
			page = service.totalPage3(map.get("input"));
		}
		return page;
	}
	@PostMapping(value="/edit")
	public void edit(@RequestBody HashMap<String, String> map) {
//		System.out.println(map);
		service.edit(map);
	}
}
