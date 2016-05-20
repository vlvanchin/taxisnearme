package com.hackathon.hitaxi.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.hackathon.hitaxi.service.data.AvailableTaxiInfo;
import com.hackathon.hitaxi.service.data.Features;
import com.hackathon.hitaxi.service.data.Geometry;
import com.hackathon.hitaxi.service.data.TaxiAvailabilityInfo;

@Service
public class TaxiDataService {
	
	private static final Logger log = LoggerFactory.getLogger(TaxiDataService.class);
	
	@Autowired
	private TaxiRepository taxiRepository;
	
	@Scheduled(fixedDelay = 60000)
	public void fetchTaxiData() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://api.data.gov.sg/v1/transport/taxi-availability";
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("api-key", "89QP6Nc2VcB2HlD5JpQGaP4GJhUqUkPV");
		
		Date currentDate = new Date();
		
		String now = "2016-05-20T12:43:00" ;
		URI uri = UriComponentsBuilder.fromHttpUrl(url).queryParam("date-time", now ).build().encode().toUri();
		
		
		AvailableTaxiInfo responseType;
//		Map<String,String> urlVariables = new HashMap();
		

		HttpEntity<?> request = new HttpEntity<Object>(headers);
		HttpEntity<AvailableTaxiInfo> taxiInfoEntity =  restTemplate.exchange(uri, HttpMethod.GET, request, AvailableTaxiInfo.class);
		
		AvailableTaxiInfo availableTaxiInfo = taxiInfoEntity.getBody();
		
//		urlVariables.put("date-time", "2016-05-19T15:46:00");
//		urlVariables.put("api-key", "89QP6Nc2VcB2HlD5JpQGaP4GJhUqUkPV");
//		responseType = restTemplate.getForObject(url, AvailableTaxiInfo.class, urlVariables);
//		System.out.println("Available:::::: Length" + availableTaxiInfo.getFeatures().length);
		Features[] features = availableTaxiInfo.getFeatures();
		Calendar calendar = Calendar.getInstance();
		long currentTime = calendar.getTimeInMillis();
		System.out.println("writing to mongo db : " + currentTime);
		
		// Do removal of old document objects from DB
		calendar.add(Calendar.MINUTE, -1);
		taxiRepository.deleteByTimeLessThan(calendar.getTimeInMillis());
		
		// do REST query 
		for (Features feature : features) {
			Geometry geometry = feature.getGeometry();
			String[][] coordinates = geometry.getCoordinates();
			//List<Point> points = new ArrayList<Point>();

			List<TaxiAvailabilityInfo> taxis = new ArrayList<TaxiAvailabilityInfo>();
			for (int i=0; i < coordinates.length; i++) {
//				System.out.println( "Corrr::: " + coordinates[i][0] + ", " + coordinates[i][1]);
				//points.add(new Point(new Double(coordinates[i][0]),new Double(coordinates[i][1])));
				
				double[] position = { new Double (coordinates[i][0]).doubleValue(),new Double(coordinates[i][1]).doubleValue()};

				TaxiAvailabilityInfo aTaxi = new TaxiAvailabilityInfo();
				aTaxi.setPosition(position);
				aTaxi.setTime(currentTime);
				taxis.add(aTaxi);
				
				
			}
			
			
			//GeoJsonMultiPoint geoPoint = new GeoJsonMultiPoint(points);
			
			
//			taxiInfo.setPosition(position);
			taxiRepository.save(taxis);
			//taxiRepository.save(taxiInfo);
			
			//System.out.println(geometry);
		}
		
	}
	
	
	//@Scheduled(fixedDelay = 1000)
	public void runAtFixedTime() {
		System.out.println(" THis is running at " + new Date());
	}
	
	
	public static void main (String[] args) throws Exception {
//		SpringApplication.run(TaxiDataService.class);
		TaxiDataService app = new TaxiDataService();
		app.fetchTaxiData();
	}

}
