package com.ssaffy.happyapi.House;

public class HouseVO {

	String aptCode;
	String apartmentName;
	String dealAmount;
	String area;
	String floor;
	int buildYear;
	String roadName;
	String lng;
	String lat;
	

	public HouseVO(String aptCode, String apartmentName, String dealAmount, String area, String floor, int buildYear,
			String roadName, String lng, String lat ) {
		super();
		this.aptCode = aptCode;
		this.apartmentName = apartmentName;
		this.dealAmount = dealAmount;
		this.area = area;
		this.floor = floor;
		this.buildYear = buildYear;
		this.roadName = roadName;
		this.lng = lng;
		this.lat = lat;
		 
	}

	public HouseVO() {

	}

	public String getAptCode() {
		return aptCode;
	}

	public void setAptCode(String aptCode) {
		this.aptCode = aptCode;
	}

	public String getApartmentName() {
		return apartmentName;
	}

	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}

	public String getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public int getBuildYear() {
		return buildYear;
	}

	public void setBuildYear(int buildYear) {
		this.buildYear = buildYear;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

}
