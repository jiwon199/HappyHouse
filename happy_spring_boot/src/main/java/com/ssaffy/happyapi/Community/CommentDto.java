package com.ssaffy.happyapi.Community;

public class CommentDto {
	int commentNO;
	String writer;
	String comment;
	int articleNO;
	
	public CommentDto() {
		super();
	}

	public CommentDto(int commentNO, String comment, int articleNO,String writer) {
		super();
		this.commentNO = commentNO;
		this.comment = comment;
		this.articleNO = articleNO;
	}

	public int getCommentNO() {
		return commentNO;
	}

	public void setCommentNO(int commentNO) {
		this.commentNO = commentNO;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

 
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getArticleNO() {
		return articleNO;
	}

	public void setArticleNO(int articleNO) {
		this.articleNO = articleNO;
	}
 
	
}
