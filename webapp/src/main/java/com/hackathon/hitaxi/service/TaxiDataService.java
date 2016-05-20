package com.hackathon.hitaxi.service;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.hackathon.hitaxi.service.data.AvailableTaxiInfo;
import com.hackathon.hitaxi.service.data.Features;
import com.hackathon.hitaxi.service.data.Geometry;

public class TaxiDataService {
	
	private static final Logger log = LoggerFactory.getLogger(TaxiDataService.class);
	
	public void fetchTaxiData() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://api.data.gov.sg/v1/transport/taxi-availability";
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("api-key", "89QP6Nc2VcB2HlD5JpQGaP4GJhUqUkPV");
		
		Date currentDate = new Date();
		
		String now = "2016-05-19T13:46:00" ;
		URI uri = UriComponentsBuilder.fromHttpUrl(url).queryParam("date-time", now ).build().encode().toUri();
		
		
		AvailableTaxiInfo responseType;
		Map<String,String> urlVariables = new HashMap();
		

		HttpEntity<?> request = new HttpEntity<Object>(headers);
		HttpEntity<AvailableTaxiInfo> taxiInfoEntity =  restTemplate.exchange(uri, HttpMethod.GET, request, AvailableTaxiInfo.class);
		
		AvailableTaxiInfo availableTaxiInfo = taxiInfoEntity.getBody();
		
		urlVariables.put("date-time", "2016-05-19T15:46:00");
//		urlVariables.put("api-key", "89QP6Nc2VcB2HlD5JpQGaP4GJhUqUkPV");
//		responseType = restTemplate.getForObject(url, AvailableTaxiInfo.class, urlVariables);
		System.out.println("Available:::::: Length" + availableTaxiInfo.getFeatures().length);
		Features[] features = availableTaxiInfo.getFeatures();
		
		for (Features feature : features) {
			Geometry geometry = feature.getGeometry();
			String[][] coordinates = geometry.getCoordinates();
			for (int i=0; i < coordinates.length; i++) {
				System.out.println( "Corrr::: " + coordinates[i][0] + ", " + coordinates[i][1]);
				
			}
			
			//System.out.println(geometry);
		}
		
	}
	
	public static void main (String[] args) throws Exception {
//		SpringApplication.run(TaxiDataService.class);
		TaxiDataService app = new TaxiDataService();
		app.fetchTaxiData();
	}

}
