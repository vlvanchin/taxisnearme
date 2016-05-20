package com.hackathon.hitaxi.controller;

import org.springframework.data.mongodb.core.geo.GeoJson;

public class Location {
	
	private GeoJson geoJson;
	
	private double longitude;
	private double latitude;
	
	
	public Location(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public GeoJson getGeoJson() {
		return geoJson;
	}
	public void setGeoJson(GeoJson geoJson) {
		this.geoJson = geoJson;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	

}
