package com.hackathon.hitaxi.service.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailableTaxiInfo {

	private Crs crs;

    private Features[] features;

    private String type;

    public Crs getCrs ()
    {
        return crs;
    }

    public void setCrs (Crs crs)
    {
        this.crs = crs;
    }

    public Features[] getFeatures ()
    {
        return features;
    }

    public void setFeatures (Features[] features)
    {
        this.features = features;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [crs = "+crs+", features = "+features+", type = "+type+"]";
    }
}
