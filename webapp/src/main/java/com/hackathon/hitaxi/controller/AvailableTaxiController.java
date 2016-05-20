package com.hackathon.hitaxi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonGeometryCollection;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.hitaxi.service.TaxiRepository;
import com.hackathon.hitaxi.service.data.TaxiAvailabilityInfo;

@RestController
public class AvailableTaxiController {
	
	@Autowired
	private TaxiRepository taxiRepository;
	
	
	@RequestMapping(value = "/testing", method=RequestMethod.GET)
	public List<GeoJsonPoint> getAllTaxisWithin() {
		
		Circle circle = new Circle(new Point(103.8305, 1.30578), new Distance(5, Metrics.KILOMETERS));
		List<TaxiAvailabilityInfo> taxis = taxiRepository.findByPositionWithin(circle );
		List<GeoJsonPoint> geoJsonPoints = new ArrayList<GeoJsonPoint>();
		for (TaxiAvailabilityInfo taxiAvailabilityInfo : taxis) {
			geoJsonPoints.add(new GeoJsonPoint(new Point(taxiAvailabilityInfo.getPosition()[0], taxiAvailabilityInfo.getPosition()[1])));
		}
		return geoJsonPoints;
		
		
	}
	
	
	@RequestMapping(value = "/testingthree", method=RequestMethod.GET)
	public GeoJsonGeometryCollection getAllTaxisWithinThree() {
		
		Circle circle = new Circle(new Point(103.8305, 1.30578), new Distance(5, Metrics.KILOMETERS));
		List<TaxiAvailabilityInfo> taxis = taxiRepository.findByPositionWithin(circle );
		List<GeoJson<?>> geometries = new ArrayList<GeoJson<?>>();
		
		for (TaxiAvailabilityInfo taxiAvailabilityInfo : taxis) {
			
             GeoJsonPoint jsonPoint = new GeoJsonPoint(new Double(taxiAvailabilityInfo.getPosition()[0]), new Double(taxiAvailabilityInfo.getPosition()[1]));
             geometries.add(jsonPoint);
		}
        
        
        GeoJsonGeometryCollection collection = new GeoJsonGeometryCollection(geometries);
        for (GeoJson<?> geoJson : geometries) {
              System.out.println("Type:" + geoJson.getType());
              System.out.println("Coordinates:" + geoJson.getCoordinates());
        }
		
        return collection;
		
	}
	
	
	@RequestMapping(value = "/testingtwo", method=RequestMethod.GET)
	public List<GeoJsonPoint> getTaxisNearMe(@RequestParam(value="location") Point point, @RequestParam(value="radius") Distance radius) {
		
		Circle circle = new Circle(new Point(103.8305, 1.30578), new Distance(5, Metrics.KILOMETERS));
		List<TaxiAvailabilityInfo> taxis = taxiRepository.findByPositionWithin(circle );
		List<GeoJsonPoint> geoJsonPoints = new ArrayList<GeoJsonPoint>();
		for (TaxiAvailabilityInfo taxiAvailabilityInfo : taxis) {
			geoJsonPoints.add(new GeoJsonPoint(new Point(taxiAvailabilityInfo.getPosition()[0], taxiAvailabilityInfo.getPosition()[1])));
		}
		return geoJsonPoints;
		

		//		taxis.stream().forEach(System.out::println);
//		GeoJson geoJson = new GeoJsonGeometryCollection(geometries)
//		List<? extends GeoResult<GeoJsonPoint>> list = new ArrayList<>();
//		GeoResults<GeoJsonPoint> results = new GeoResults<GeoJsonPoint>(list);
		 
//		for (TaxiAvailabilityInfo taxiAvailabilityInfo : taxis) {
//			System.out.println(taxiAvailabilityInfo.toString());
//			GeoJsonPoint geoJsonPoint = new GeoJsonPoint(taxiAvailabilityInfo.getPosition()[0],taxiAvailabilityInfo.getPosition()[1]);
//			GeoResult<GeoJsonPoint> geoResult = new GeoResult<GeoJsonPoint>(geoJsonPoint, radius);
//			list.add(geoResult);
//			System.out.println("print geoJsonPoint: " + geoJsonPoint.toString());
//		}
		
//		List<TaxiAvailabilityInfo> allTaxis = taxiRepository.findAll();
		
		//allTaxis.stream().forEach(System.out::println);
		
		
	}
	
	

}
