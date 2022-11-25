package com.ssaffy.happyapi.Community;

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

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
 
 
 

@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {
	@Autowired
	CommentService service;
	
	@ApiOperation(value = "댓글을 등록한다.")
	@PostMapping(value = "/addComment")
	public void login(@RequestBody HashMap<String, String> map) throws Exception {
		service.addComment(map);
	}
	
	@ApiOperation(value = "게시글 번호를 받아서 그 게시글에 달린 댓글 목록을 반환한다.", response = CommentDto.class)
	@GetMapping(value = "/list/{articleNO}")
	public List<CommentDto> getCommentList(@PathVariable("articleNO") int articleNo) throws Exception { 
		return service.getCommentList(articleNo);
	}
	
	@ApiOperation(value = "댓글을 수정한다.")
	@PostMapping(value = "/updateComment")
	public void updateComment(@RequestBody HashMap<String, String> map) {
		service.updateComment(map);
	}
	
	@ApiOperation(value = "댓글을 삭제한다.")
	@PostMapping(value = "/deleteComment/{commentNo}")
	public void deleteComment(@PathVariable("commentNo") int commentNo) {
		service.deleteComment(commentNo);
	}
}
