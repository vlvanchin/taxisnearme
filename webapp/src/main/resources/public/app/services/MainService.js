app.factory('MainService', function($http) {
	var factory = {};

	
	factory.getLocation = function(address) {
	  return $http.get('location/' + address).then(function (result) {
	        return result.data;
      });
	}
	
	factory.submitQuery = function(query) {
	  return $http.get('taxisnearby').then(function (result) {
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