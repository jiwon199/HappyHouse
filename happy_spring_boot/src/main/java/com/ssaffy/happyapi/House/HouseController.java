package com.ssaffy.happyapi.House;

import java.util.ArrayList;
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

@RestController
@RequestMapping("/house")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HouseController {
	@Autowired
	HouseService service;
	
	@GetMapping(value = "/optionSido")
	public List option() throws Exception{
		return service.sidoList();		
	}
	@GetMapping("/optionGugun/{sido}") 
	public List optionGugun(@PathVariable("sido") String sido) throws Exception {
		return service.gugunList(sido);
	} 
	//겹치는 동이 있어서 시도, 구군 둘 다 받아야
	@GetMapping("/optionDong/{gugun}/{sido}") 
	public List optionDong(@PathVariable("gugun") String gugun,@PathVariable("sido") String sido) throws Exception {
		HashMap<String, String> map = new HashMap<>();
		map.put("gugun", gugun);
		map.put("sido", sido);
		return service.gugunDong(map);
	} 
	
	@GetMapping("/dealInfo/{aptCode}") 
	public List<dealVO> dealInfo(@PathVariable("aptCode") String aptCode) throws Exception {
		return service.getDeal(aptCode);
	} 
	
	@GetMapping("/dongCode/{sido}/{gugun}/{dong}")
	public String getDongCode(@PathVariable("sido") String sido,@PathVariable("gugun") String gugun,@PathVariable("dong") String dong) throws Exception{
		AreaVO vo=new AreaVO();
		vo.setSido(sido);
		vo.setGugun(gugun);
		vo.setDong(dong);
		String dongCode=service.getDongCode(vo);
		 
		return dongCode;
	}
	@GetMapping("/dongAvg/{sido}/{gugun}")
	public List<AvgDongVO> getDongAvg(@PathVariable("sido") String sido,@PathVariable("gugun") String gugun ) throws Exception{
		HashMap <String,String> map=new HashMap<>();
		map.put("sido", sido);
		map.put("gugun", gugun);
		//System.out.println("컨트롤러 호출");
		List<AvgDongVO> list=service.getDongAvg(map);
		 
		return list;
	}
	 
	
	
	@GetMapping("/avgDeal/{aptCode}")
	public String getAvg(@PathVariable("aptCode") String aptCode) throws Exception{
		return service.getAvg(aptCode);
	}
	@PostMapping(value = "/list")
	public List<HouseVO> getHouseList(@RequestBody AreaVO vo) throws Exception { 
		String dongCode=service.getDongCode(vo);
		HashMap<String, String> map = new HashMap<>();
		map.put("dongCode", dongCode);
		map.put("dealYear", Integer.toString(vo.getYear()));
		map.put("dealMonth", Integer.toString(vo.getMonth()));
		List<HouseVO> list=service.getList(map);
		return list;
	}
	//관심지역이면 1, 관심지역 아니면 0 반환
	@GetMapping("/isLike/{id}/{dongCode}")
	public String isLikeArea(@PathVariable("id") String id,@PathVariable("dongCode") String dongCode) {
		HashMap<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("dongCode", dongCode);
		int count=service.isLikeArea(map);
		if(count==0) return "0";
		else return "1";
	}
	
	@PostMapping(value = "/addLikeArea")
	public void addLikeArea(@RequestBody HashMap<String, String> map) {
		service.addLikeArea(map);
	}
	@PostMapping(value = "/deleteLikeArea")
	public void deleteLikeArea(@RequestBody HashMap<String, String> map) {
		service.deleteLikeArea(map);
	}
	
	
}