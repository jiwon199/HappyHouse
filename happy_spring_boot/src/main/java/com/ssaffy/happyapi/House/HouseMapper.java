package com.ssaffy.happyapi.House;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HouseMapper {

	List<String> getSido();

	List<String> getGugun(String sido);

	List<String> getDong(HashMap<String, String> map);

	String getDongCode(AreaVO vo);

	List<HouseVO> getList(HashMap<String, String> map);

	List<dealVO> getDeal(String aptCode);
	int isLike(HashMap<String, String> map);
	void addLikeArea(HashMap<String, String> map);
	void deleteLikeArea(HashMap<String, String> map);

	String getAvg(String aptCode);

	List<AvgDongVO> getDongAvg(HashMap<String, String> map);

	 
 
}
