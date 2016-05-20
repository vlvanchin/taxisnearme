app.factory('MainService', function($http) {
	var factory = {};

	
	factory.getLocation = function(postalCode) {
	  return $http.get('location/' + postalCode).then(function (result) {
	        return result.data;
      });
	}
	
	factory.submitQuery = function(query) {
	  return $http.get('taxisnearby').then(function (result) {
        return result.data;
      });
	}

	return factory;
})