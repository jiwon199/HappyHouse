package com.ssaffy.happyapi.User;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

	UserDto login(HashMap<String, String> map);

	void register(HashMap<String, String> map);

	void edit(HashMap<String, String> map);

	void delete(String id);

	UserDto confirmId(String id);

	List<String> getLikeArea(String id);

	DongDto getDongInfo(String dongCode);

	void delLikeArea(HashMap<String, String> map);

	int isAdmin(String id);

	List<UserDto> userList();

	void promotion(String id);

	void writeNotice(HashMap<String, String> map);

	List<NoticeDto> getNotice();

	void noticeDelete(int num);

	UserDto idEmail(HashMap<String, String> map);

}
