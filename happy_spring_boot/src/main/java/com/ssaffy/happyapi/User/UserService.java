package com.ssaffy.happyapi.User;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	UserMapper mapper;
	public UserDto login(HashMap<String, String> map) {
		return mapper.login(map);
	}
	public void register(HashMap<String, String> map) {
		mapper.register(map);		
	}
	public void edit(HashMap<String, String> map) {
		mapper.edit(map);
	}
	public void delete(String id) {
		mapper.delete(id);
	}
	public UserDto confirmId(String id) {
		return mapper.confirmId(id);
	}
	public List<String> getLikeArea(String id) {
		return mapper.getLikeArea(id);
	}
	public DongDto getDongInfo(String dongCode) {
		return mapper.getDongInfo(dongCode);
	}
	public void delLikeArea(HashMap<String, String> map) {
		 mapper.delLikeArea(map);
	}
	public int isAdmin(String id) {
		return mapper.isAdmin(id);
	}
	public List<UserDto> userList() {
		return mapper.userList();
	}
	public void promotion(String id) {
		mapper.promotion(id);
	}
	public void writeNotice(HashMap<String, String> map) {
		mapper.writeNotice(map);
	}
	public List<NoticeDto> getNotice() {
		return mapper.getNotice();
	}
	public void noticeDelete(int num) {
		mapper.noticeDelete(num);
	}
	public UserDto idEmail(HashMap<String, String> map) {
		return mapper.idEmail(map);
	}
}
