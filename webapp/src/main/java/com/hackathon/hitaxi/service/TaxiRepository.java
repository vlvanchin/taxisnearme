package com.hackathon.hitaxi.service;

import java.util.List;

import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.hackathon.hitaxi.service.data.TaxiAvailabilityInfo;

public interface TaxiRepository extends MongoRepository<TaxiAvailabilityInfo, String>{
	
	public TaxiAvailabilityInfo findAllByTime(Long time); 
	
	public List<TaxiAvailabilityInfo> findByPositionWithin(Circle circle);
	
	public void deleteByTimeLessThan(Long time);
	
}
