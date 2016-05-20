app.factory('MainService', function($http) {
	var factory = {};

	
	factory.getLocation = function(address) {
	  return $http.get('location/' + address).then(function (result) {
	        return result.data;
      });
	}
	
	factory.submitQuery = function(query, location) {
	  //return $http.get('taxisnearby').then(function (result) {
	  var data = {
			  "location" : {
				  "x" : location.latitude,
				  "y" : location.longitude	 
			  },
			  "radius" : {
				  "value" : query.radius
				  "metric" : {
					  "multiplier" : 6378.137, 
					  "abbreviation" : "km"
				  }
			  }
	  }
		
	  return $http.get('testingtwo', data).then(function (result) {				
        return result.data;
      });
	}
	
	factory.submitBusQuery = function(busStopId) {
	  return $http.get('busArrival/' + busStopId).then(function (result) {
        return result.data;
      });
	}

	return factory;
})