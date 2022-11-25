package com.ssaffy.happyapi.Community;

import java.util.HashMap;
import java.util.List;

public interface CommentMapper {
	void addComment(HashMap<String, String> map);

	List<CommentDto> getCommentList(int articleNo);

	void updateComment(HashMap<String, String> map);

	void deleteComment(int commentNo);
}
