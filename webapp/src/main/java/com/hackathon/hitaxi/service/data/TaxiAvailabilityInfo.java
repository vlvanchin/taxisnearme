package com.hackathon.hitaxi.service.data;

import java.sql.Timestamp;
import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TaxiAvailabilityInfo {
	
	@Id private String id;
	
	private long time;
	
	//private GeoJson taxiResults;
	
	private double[] position;
	
	
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	/*public Timestamp getLastFetchedTime() {
		return lastFetchedTime;
	}

	public void setLastFetchedTime(Timestamp lastFetchedTime) {
		this.lastFetchedTime = lastFetchedTime;
	}
*/
	/*public GeoJson getTaxiResults() {
		return taxiResults;
	}

	public void setTaxiResults(GeoJson taxiResults) {
		this.taxiResults = taxiResults;
	}*/
	
	


	public double[] getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return "TaxiAvailabilityInfo [id=" + id + ", time=" + time + ", position=" + Arrays.toString(position) + "]";
	}

	public void setPosition(double[] position) {
		this.position = position;
	}

	

	

}
