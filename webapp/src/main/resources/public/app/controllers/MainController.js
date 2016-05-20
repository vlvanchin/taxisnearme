app.controller("MainController", function ($scope, MainService) {

  $scope.data = data1;
  $scope.map = {};
  $scope.query = {
    radius : 500,
    postalCode : "573943"
  };  
  
  $scope.baseLayer = {};
  $scope.markers = new L.layerGroup();

  $scope.init = function(){

	$scope.baseLayer = L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw', {
		maxZoom: 18,
		attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
			'<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery © <a href="http://mapbox.com">Mapbox</a>',
		id: 'mapbox.streets'
	})

	//singapore map coordinates, zoom level
	var mymap = L.map('mapid',{
		layers : [$scope.baseLayer]
	}).setView([1.290270, 103.851959], 15);
	
	$scope.map = mymap;
  }
 
  $scope.submitQuery = function(){
	var markers = $scope.markers;
	var map = $scope.map;
	
	MainService.getLocation($scope.query.postalCode).then(function(myLocation){
	    MainService.submitQuery($scope.query).then(function(data) {
	    	markers.clearLayers();
	    	var radius = $scope.query.radius;
	    	for(var i=0; i < data.length; i++)
	    	{
	    		var c = data[i];
		    	var lat = c.latitude; 
		    	var long = c.longitude;
		    	var marker = L.marker([lat,long]);
		    	markers.addLayer(marker).addTo(map);
	    	}   
	    	
	    	L.circle([myLocation.latitude, myLocation.longitude], radius, {
	    		color: 'red',
	    		fillColor: '#f03',
	    		fillOpacity: 0.5
	    	}).addTo(markers);
	    	$scope.map.panTo([myLocation.latitude, myLocation.longitude]);	
	    });
	    
	});


/*  	var radius = $scope.query.radius;
  	var cs = $scope.data.features[0].geometry.coordinates;
	var c1 = cs[0];
	var x = c1[1];
	var y = c1[0];
	//L.marker([c1[1],c1[0]]).addTo($scope.map);

	L.circle([x, y], radius, {
		color: 'red',
		fillColor: '#f03',
		fillOpacity: 0.5
	}).addTo($scope.map);

	$scope.map.panTo([x,y]);*/
  }


});

/*L.marker([51.5, -0.09]).addTo(mymap)
	.bindPopup("<b>Hello world!</b><br />I am a popup.").openPopup();

L.circle([51.508, -.011], 500, {
	color: 'red',
	fillColor: '#f03',
	fillOpacity: 0.5
}).addTo(mymap).bindPopup("I am a circle.");

L.polygon([
	[51.509, -0.08],
	[51.503, -0.06],
	[51.51, -0.047]
]).addTo(mymap).bindPopup("I am a polygon.");


var popup = L.popup();

function onMapClick(e) {
	popup
		.setLatLng(e.latlng)
		.setContent("You clicked the map at " + e.latlng.toString())
		.openOn(mymap);
}

mymap.on('click', onMapClick);*/
