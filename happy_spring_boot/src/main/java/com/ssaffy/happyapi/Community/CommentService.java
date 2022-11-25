package com.ssaffy.happyapi.Community;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 

@Service
public class CommentService {
	@Autowired
	CommentMapper mapper;

	public void addComment(HashMap<String, String> map) {
		mapper.addComment(map);
	}

	public List<CommentDto> getCommentList(int articleNo) {
		 
		return mapper.getCommentList(articleNo);
	}

	public void updateComment(HashMap<String, String> map) {
		mapper.updateComment(map);
		
	}

	public void deleteComment(int commentNo) {
		 mapper.deleteComment(commentNo);
	}
}
