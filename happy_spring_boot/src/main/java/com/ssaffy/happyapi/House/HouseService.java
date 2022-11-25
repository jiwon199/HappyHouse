package com.ssaffy.happyapi.House;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseService {
	@Autowired
	HouseMapper mapper;
	
	public List<String> sidoList() throws Exception { 
		  List<String> list=mapper.getSido(); 
		  return list; 
	  }

	public List gugunList(String sido) {
		 List<String> list=mapper.getGugun(sido); 
		  return list; 
	}

	public List gugunDong(HashMap<String, String> map) {
		 List<String> list=mapper.getDong(map); 
		  return list; 
	}

	public String getDongCode(AreaVO vo) {
		// TODO Auto-generated method stub
		return mapper.getDongCode(vo);
	}

	public List<HouseVO> getList(HashMap<String, String> map) {
		List <HouseVO> list=mapper.getList(map);
		return list;
	}

	public List<dealVO> getDeal(String aptCode) {
		List<dealVO> list=mapper.getDeal(aptCode);
		return list;
	}

	public int isLikeArea(HashMap<String, String> map) {
		return mapper.isLike(map);
	}

	public void addLikeArea(HashMap<String, String> map) {
		mapper.addLikeArea(map);
	}

	public void deleteLikeArea(HashMap<String, String> map) {
		 mapper.deleteLikeArea(map);
	}

	public String getAvg(String aptCode) {
		// TODO Auto-generated method stub
		return mapper.getAvg(aptCode);
	}

	public List<AvgDongVO> getDongAvg(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return mapper.getDongAvg(map);
	}

	 
}
