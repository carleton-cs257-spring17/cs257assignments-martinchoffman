/*
 *  Website.js
 *  Martin Hoffman and Chris Tordi, 8 May 2016
 *
 *  Javascript program with AJAX that runs front-end functions
 */

var api_base_url = 'http://thacker.mathcs.carleton.edu:5136/';

/* Takes values from search bars in city search page, forms correct api query
 * Calls getStateCityCallback and passes JSON response to query
 */
function onGetCityButton() {
	var state = document.getElementById("stateS").value;
	var city  = document.getElementById("cityS").value;

	if (state.length > 2) {
		alert("Please use state abbreviations");
		return;
	}

	if (city.length == 0 || state.length == 0) {
		alert("Please do not leave any forms empty");
		return;
	}

	if (city.length < 4) {
		alert("The city'" + city + "' was not found");
		return;
	}

	var url = api_base_url + state + '/' + city;

	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.open('get', url);

	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) { 
			var responseText = JSON.parse(xmlHttpRequest.responseText);
			if (responseText == ',,,,') {
				alert("The city '" + city + "' was not found.");
				return;
			}
			getStateCityCallback(responseText);
		} 
	};

	xmlHttpRequest.send(null);
}

/* Takes values from search bars in state search page, forms correct api query
 * Calls getStateCityCallback and passes JSON response to query
 */
function onGetStateButton() {
	var state = document.getElementById("stateSearch").value;
	if (state.length != 2) {
		alert("Please use state abbreviations");
		return;
	}

	if (state.length == 0) {
		alert("Please do not leave any forms empty");
		return;
	}
	var url = api_base_url + state;
	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.open('get', url);

	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) { 
			var responseText = JSON.parse(xmlHttpRequest.responseText);
			if (responseText == ',,,,') {
				alert("The state '" + state + "' was not found.");
				return;
			}
			getStateCityCallback(responseText);
		} 
	};

	xmlHttpRequest.send(null);
}

/* Takes values from search bars in comparison search page, forms correct api query
 * Calls getCompareCallback and passes JSON response to query
 */
function onGetCompareButton() {
	var state1 = document.getElementById("stateC1").value;
	var city1  = document.getElementById("cityC1").value;
	var state2 = document.getElementById("stateC2").value;
	var city2  = document.getElementById("cityC2").value;

	if(state1.length != 2 || state2.length != 2) {
		alert("Please use state abbreviations");
		return;
	}

	if (city1 == '' && city2 == '') {
		var url = api_base_url + 'compare/' + state1 + '/' + state2;
	} else {
		var url = api_base_url + 'compare/' + state1 + '/' + city1 + '/' + state2 + '/' + city2;
	}

	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.open('get', url);

	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) { 
			var responseText = JSON.parse(xmlHttpRequest.responseText);
	
			if (responseText == ",,,,,,,,,") {
				alert("The cities '" + city1 + "', '" + city2 + "' were not found.");
				return;
			}

			if (responseText[3] == null) {
				alert("The city '" + city2 + "' was not found.");
				return;
			}

			if (responseText[2] == null) {
				alert("The city '" + city1 + "' was not found.");
				return;
			}

			if (responseText[1] == null) {
				alert("The state '" + state2 + "' was not found.");
				return;
			}

			if (responseText[0] == null) {
				alert("The state '" + state1 + "' was not found.");
				return;
			}

			getCompareCallback(xmlHttpRequest.responseText);
		} 
	};

	xmlHttpRequest.send(null);
}
/* Param: JSON response to query from getcCompareButton
 * Creates html comparison table with response text
 * Displays table on compare page
*/
function getCompareCallback(responseText) {
	var compareList = JSON.parse(responseText);
	var tableBody = '';
	tableBody += '<tr>';
	tableBody += '<td>' + 'State' + '</td>';
	if (compareList[0]['City'] != null && compareList[0]['City'] != null ){
		tableBody += '<td>' + 'City' + '</td>';
	}
	
	tableBody += '<td>' + 'Max Temp' + '</td>';
	tableBody += '<td>' + 'Min Temp' + '</td>';
	tableBody += '<td>' + 'Mean Temp' + '</td>';
	tableBody += '<td>' + 'Rain Index' + '</td>';
	tableBody += '<td>' + 'Snow Index' + '</td>';
	tableBody += '</tr>';

	tableBody += '<tr>';
	tableBody += '<td>' + compareList[0]['State'] + '</td>';
	if (compareList[0]['City'] != null) {
		tableBody += '<td>' + compareList[0]['City'] + '</td>';
	}
	
	tableBody += '<td>' + compareList[0]['Max Temp'] + '</td>';
	tableBody += '<td>' + compareList[2]['Min Temp'] + '</td>';
	tableBody += '<td>' + compareList[4]['Mean Temp'] + '</td>';
	tableBody += '<td>' + compareList[6]['Rain Index'] + '</td>';
	tableBody += '<td>' + compareList[8]['Snow Index'] + '</td>';
	tableBody += '</tr>';

	tableBody += '<tr>';
	tableBody += '<td>' + compareList[1]['State'] + '</td>';
	if (compareList[1]['City'] != null) {
		tableBody += '<td>' + compareList[1]['City'] + '</td>';
	}
 
	tableBody += '<td>' + compareList[1]['Max Temp'] + '</td>';
	tableBody += '<td>' + compareList[3]['Min Temp'] + '</td>';
	tableBody += '<td>' + compareList[5]['Mean Temp'] + '</td>';
	tableBody += '<td>' + compareList[7]['Rain Index'] + '</td>';
	tableBody += '<td>' + compareList[9]['Snow Index'] + '</td>';
	tableBody += '</tr>';


	var resultsTableElement = document.getElementById('results_table');
	resultsTableElement.innerHTML = tableBody;
}
/* Param: JSON query response to getCitybutton  and getStatebutton
 * uses query response to create html table of results from query
 * Displays table on city or state page depending on what function calls it
*/
function getStateCityCallback(responseText) {
	var statesList = responseText;

	var tableBody = '';
	tableBody += '<tr>';
	tableBody += '<td>' + 'State' + '</td>';

	if (statesList[0]['City'] != null) {
		tableBody += '<td>' + 'City' + '</td>';
		
	}

	tableBody += '<td>' + 'Max Temp' + '</td>';
	tableBody += '<td>' + 'Min Temp' + '</td>';
	tableBody += '<td>' + 'Mean Temp' + '</td>';
	tableBody += '<td>' + 'Rain Index' + '</td>';
	tableBody += '<td>' + 'Snow Index' + '</td>';
	tableBody += '</tr>';

	tableBody += '<tr>';
	tableBody += '<td>' + statesList[0]['State'] + '</td>';

	if (statesList[0]['City'] != null) {
		tableBody += '<td>' + statesList[0]['City'] + '</td>';
	}

	tableBody += '<td>' + statesList[0]['Max Temp'] + '</td>';
	tableBody += '<td>' + statesList[1]['Min Temp'] + '</td>';
	tableBody += '<td>' + statesList[2]['Mean Temp'] + '</td>';
	tableBody += '<td>' + statesList[3]['Rain Index'] + '</td>';
	tableBody += '<td>' + statesList[4]['Snow Index'] + '</td>';
	tableBody += '</tr>';

	var resultsTableElement = document.getElementById('results_table');
	resultsTableElement.innerHTML = tableBody;
}
/* Takes values from search bars and buttons on find search page, forms correct api query
 * Calls getCityCallback and passes JSON response to query
 */
function onFindButton() {
	var min = document.getElementById("min_temp").value;
	var max = document.getElementById("max_temp").value;
	var button = document.getElementById("find_button");
	var check = max - min;

	button.innerHTML = '<i class="fa fa-refresh fa-spin"></i> Find';

	if (check < 1) {
		alert("Please make sure max is at least 1 degree higher than min");
		button.innerHTML = 'Find';
		return;
	}

	var num_results = document.getElementById("num_results").value;
	if (num_results < 1) {
		num_results = 5;
	}

	var url = api_base_url;

	if (document.getElementById("cities").checked == true) {
		url +=  'range/city/' + min + '/' + max + '/' + num_results;

		if (document.getElementById("days").checked == true) {
			url = api_base_url + 'range/city/days/' + min + '/' + max + '/' + num_results;
		}
	} else {
		url += 'range/state/' + min + '/' + max + '/' + num_results;
	}

	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.open('get', url);

	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) { 
			if (JSON.parse(xmlHttpRequest.responseText) == '') {
				button.innerHTML = 'Find';
				alert("No results found for range '" + min + "-" + max + "'.");
				return;
			}
			getCityCallback(xmlHttpRequest.responseText);
		} 
	};

	xmlHttpRequest.send(null);
}

/* Param: JSON query response to onFindButton
 * uses query response to create html table of results from query
 * Displays table on city or state page depending on what function calls it
 * Table displays mean temperature or days depending on query
*/
function getCityCallback(responseText) {
	var finderList = JSON.parse(responseText);
	var tableBody = '';
	tableBody += '<tr>'; 
	tableBody += '<td>' + 'State' + '</td>';
	if (finderList[0]['City'] != null) {
		tableBody += '<td>' + 'City' + '</td>';

		if (finderList[0]['Days'] != null) {
			tableBody += '<td>' + 'Days' + '</td>';
		} else {
			tableBody += '<td>' + 'Mean Temp' + '</td>';
		}
	} else {
		tableBody += '<td>' + 'Mean Temp' + '</td>';
	}
	tableBody += '</tr>'
	
	var count = 0;
	for (var dict of finderList) {
		tableBody += '<tr>'; 
		tableBody += '<td>' + dict['State'] + '</td>'
		if (dict['City'] != null) {
			var city = dict['City'];
			tableBody += '<td>'+ '<button' + ' onclick="initialize();codeAddress(\'' + dict['City'] + ',' + dict['State'] + '\')">' + dict['City'] + '</button>' + '</td>';

			if (dict['Days'] != null) {
				tableBody += '<td>' + dict['Days'] + '</td>';
			} else {
				tableBody += '<td>' + dict['Mean Temp'] + '</td>';
			}
		} else {
			tableBody += '<td>' + dict['Mean Temp'] + '</td>';
		}
		tableBody += '</tr>'
		count ++;
	}

	var resultsTableElement = document.getElementById('found_results_table');
	resultsTableElement.innerHTML = tableBody;

	var button = document.getElementById("find_button");
	button.innerHTML = 'Find';
}

/* Disables days button if state button is selected on find search page
* Restricts user from selecting both city and state button
*/
function setDaysCheck() {
	var citiesRadio = document.getElementById("cities");
	if(citiesRadio.checked) {
		document.getElementById("days").disabled = false;
	}
	else{
		document.getElementById("days").checked = false;
		document.getElementById("days").disabled = true;
	}
}

var geocoder;
var map;
/* Initializes google map
*/
function initialize() {
	geocoder = new google.maps.Geocoder();
	var latlng = new google.maps.LatLng(100, 40);
	var mapOptions = {
		zoom: 5,
		center: latlng
	}
	map = new google.maps.Map(document.getElementById('map'), mapOptions);
}
/* Param: city and state from finder query
 * creates longitude and latitude coordinates and plots marker at city location on google maps
*/
function codeAddress(city) {
	var address = city;
	geocoder.geocode( { 'address': address}, function(results, status) {
		if (status == 'OK') {
			map.setCenter(results[0].geometry.location);
			var marker = new google.maps.Marker({
				map: map,
				position: results[0].geometry.location
			});
		} else {
			alert('Geocode was not successful for the following reason: ' + status);
		}
	});
}	


function onHomeNav() {
	onNav('home');
}

function onStateNav() {
	onNav('state');
}

function onCityNav() {
	onNav('city');
}

function onCompareNav() {
	onNav('compare');
}

function onFindCityNav() {
	onNav('find_city');

	 var head = document.head;
	 head.innerHTML += '<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAjbWfUeCBmVzvJT4Na9cCPAWnB7XllbNo&callback=initMap"></script>\n'
}

function onAboutNav() {
	onNav('about');				
}

/* Param: the id of the nav bar button to "activate"
 * Changes the activation status of the nav bar buttons as necessary
*/
function onNav(id) {
	var home, state, city, compare, find_city, about;
	switch (id) {
	    case 'home':
	        home = 'class="active"'
	        break;
	    case 'state':
			state = 'class="active"'
	        break;
	    case 'city':
			city = 'class="active"'
	        break;
	    case 'compare':
			compare = 'class="active"'
	        break;
	    case 'find_city':
			find_city = 'class="active"'
	        break;
	    case 'about':
			about = 'class="active"'
	        break;
	    default:
	        alert('Incorret id:' + id);
	}

	var nav = '<li><a {0} id="home" onclick="onHomeNav()" href="http://localhost:8080/">Home</a></li>\n' +
			  '<li><a {1} id="state" onclick="onStateNav()">State</a></li>\n' +
			  '<li><a {2} id="city" onclick="onCityNav()">City</a></li>\n' +
			  '<li><a {3} id="compare" onclick="onCompareNav()">Compare</a></li>\n' +
			  '<li><a {4} id="find_city" onclick="onFindCityNav()">Find City</a></li>\n' +
			  '<li style="float: right"><a {5} id="about" onclick="onAboutNav()">About</a></li>\n'.format(home, state, city, compare, find_city, about);
}
