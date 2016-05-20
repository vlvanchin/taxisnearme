package com.hackathon.hitaxi.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hackathon.hitaxi.util.JsonUtils;

@RestController
public class SimpleController {
    
    	private static final String ADDRESS_URL = "http://maps.googleapis.com/maps/api/geocode/json?address={address}&sensor=true";
    	private static final String BUS_ARRIVAL_URL = "http://datamall2.mytransport.sg/ltaodataservice/BusArrival?BusStopID={busStopId}";
    	private static final String DATAMALL_ACCOUNT_KEY = "+5j3LmFcSR+5tx9LX6coJA==";
    	private static final String DATAMALL_UNIQUUE_USER_ID = "5240d3de-258a-4b8b-8fe5-c6329332b0b7";
    	private static final String DATAMALL_ACCEPT = "application/json";

	@RequestMapping("/helloworld")
	public String helloworld(Model model) {
			//model.addAttribute("message","Hello World! Server is running...");
			return "Hello World! Server is running...";
		
	}

	@RequestMapping(value = "/taxisnearbyforparams", method=RequestMethod.GET)
	public List<Location> fetchAllTaxiPoints(@RequestParam Location location, @RequestParam int radius) {
		List<Location> taxiLoctions = new ArrayList<Location>();
		taxiLoctions.add(new Location(103.62536, 1.29883));
		taxiLoctions.add(new Location(103.75956, 1.31258));
		taxiLoctions.add(new Location(103.76001, 1.3847));
		taxiLoctions.add(new Location(103.771, 1.319609));
		taxiLoctions.add(new Location(103.78572, 1.29522));
		taxiLoctions.add(new Location(103.8012, 1.283376));
		taxiLoctions.add(new Location(103.82791, 1.29041));
		taxiLoctions.add(new Location(103.84595, 1.37014));	
		
		return taxiLoctions;
		
	}
	
	@RequestMapping(value = "/taxisnearby", method=RequestMethod.GET)
	public List<Location> fetchAllTaxiPoints() {
		List<Location> taxiLoctions = new ArrayList<Location>();
		taxiLoctions.add(new Location(103.62536, 1.29883));
		taxiLoctions.add(new Location(103.75956, 1.31258));
		taxiLoctions.add(new Location(103.76001, 1.3847));
		taxiLoctions.add(new Location(103.771, 1.319609));
		taxiLoctions.add(new Location(103.78572, 1.29522));
		taxiLoctions.add(new Location(103.8012, 1.283376));
		taxiLoctions.add(new Location(103.82791, 1.29041));
		taxiLoctions.add(new Location(103.84595, 1.37014));	
		
		return taxiLoctions;
		
	}
	
	@RequestMapping(value = "/location/{address}", method=RequestMethod.GET)
	public Map<String,Double> getLocation(@PathVariable String address) {
	    Map<String,Double> coordinate = new HashMap<>();
	    RestTemplate restTemplate = new RestTemplate();
	    Map<String, Object> req = new HashMap<String, Object>();
	    req.put("address", address);	    
	    String response = restTemplate.getForObject(ADDRESS_URL, String.class, req);
	    Map<String, Object> responseMap = JsonUtils.convertJsonToMap(response);
	    List results = (List) responseMap.get("results");
	    Map<String,Object> result = (Map<String,Object>) results.get(0);
	    Map<String,Object> geometry = (Map<String,Object>) result.get("geometry");
	    Map<String,Object> location = (Map<String,Object>) geometry.get("location");
	    Double latitude = (Double) location.get("lat");
	    Double longitude = (Double) location.get("lng");
	    coordinate.put("latitude", latitude);
	    coordinate.put("longitude", longitude);
	    return coordinate;
	}
	
	@RequestMapping(value = "/busArrival/{busStopId}", method=RequestMethod.GET)
	public Map<String,Long> getBusArrival(@PathVariable String busStopId) throws ParseException {
	    Map<String,Long> resultMap = new HashMap<>();
	    
	    RestTemplate restTemplate = new RestTemplate();
	    Map<String, Object> req = new HashMap<String, Object>();
	    req.put("busStopId", busStopId);	    
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("AccountKey", DATAMALL_ACCOUNT_KEY);
	    headers.add("UniqueUserId", DATAMALL_UNIQUUE_USER_ID);
	    headers.add("Accept", DATAMALL_ACCEPT);

	    ResponseEntity<String> response = restTemplate.exchange(BUS_ARRIVAL_URL, HttpMethod.GET, new HttpEntity(headers),
		    String.class, req);
	    
	    Map<String, Object> responseMap = JsonUtils.convertJsonToMap(response.getBody());
	    List<Map<String,Object>> services = (List<Map<String,Object>>) responseMap.get("Services");
	    
	    for(Map<String,Object> service : services)
	    {
		String serviceNo = service.get("ServiceNo").toString();
		Map<String,String> nextBus = (Map<String,String>) service.get("NextBus");
		String nextArrivalTime = nextBus.get("EstimatedArrival");
		if(!nextArrivalTime.isEmpty())
		{
        		Integer length = nextArrivalTime.length();		
        		nextArrivalTime = nextArrivalTime.substring(0, length-6);
        		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        		Date nextArrivalTimeDate = sdf.parse(nextArrivalTime);
        		Date now = new Date();		
        		Long arrivalMins =  nextArrivalTimeDate.getTime()/60000 - now.getTime()/60000;
        		resultMap.put(serviceNo, arrivalMins);
		}
	    }	    

	    return resultMap;
	}
	
}
	