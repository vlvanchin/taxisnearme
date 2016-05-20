package com.hackathon.hitaxi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    	private static final String ADDRESS_URL = "http://maps.googleapis.com/maps/api/geocode/json?address={postalCode}&sensor=true";

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
	
	@RequestMapping(value = "/location/{postalCode}", method=RequestMethod.GET)
	public Map<String,Double> getLocation(@PathVariable String postalCode) {
	    Map<String,Double> coordinate = new HashMap<>();
	    RestTemplate restTemplate = new RestTemplate();
	    Map<String, Object> req = new HashMap<String, Object>();
	    req.put("postalCode", postalCode);	    
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
	
}
	