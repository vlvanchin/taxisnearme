app.factory('MainService', function($http) {
	var factory = {};

	
	factory.getLocation = function(address) {
	  return $http.get('location/' + address).then(function (result) {
	        return result.data;
      });
	}
	
	factory.submitQuery = function(query, location) {
	  //return $http.get('taxisnearby').then(function (result) {

	  
/*	  return $http({
		  "method" : "POST",
		  "url" : "testingtwo",
		  "data": data
	  })*/
		var radKm  = query.radius;
	  
	  return $http.get('testingtwo' + "/" + location.latitude + "/" + location.longitude + "/" + radKm).then(function (result) {				
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