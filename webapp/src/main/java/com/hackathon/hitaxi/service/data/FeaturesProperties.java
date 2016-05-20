package com.hackathon.hitaxi.service.data;

public class FeaturesProperties {

        private String timestamp;

        private String taxi_count;

        private Api_info api_info;

        public String getTimestamp ()
        {
            return timestamp;
        }

        public void setTimestamp (String timestamp)
        {
            this.timestamp = timestamp;
        }

        public String getTaxi_count ()
        {
            return taxi_count;
        }

        public void setTaxi_count (String taxi_count)
        {
            this.taxi_count = taxi_count;
        }

        public Api_info getApi_info ()
        {
            return api_info;
        }

        public void setApi_info (Api_info api_info)
        {
            this.api_info = api_info;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [timestamp = "+timestamp+", taxi_count = "+taxi_count+", api_info = "+api_info+"]";
        }
  

}
