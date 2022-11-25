package com.ssaffy.happyapi.File;

public class FileDto {
	String id;
	String fileName;
	public FileDto() {
		super();
	}
	public FileDto(String id, String fileName) {
		super();
		this.id = id;
		this.fileName = fileName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
