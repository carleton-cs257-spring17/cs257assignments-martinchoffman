/*
 *  Website.js
 *  Martin Hoffman and Chris Tordi, 8 May 2016
 *
 *  A little bit of Javascript
 */

var api_base_url = 'http://thacker.mathcs.carleton.edu:5136/';


function onGetCityButton() {
	var state = document.getElementById("stateS").value;
	var city  = document.getElementById("cityS").value;
	var url = api_base_url + state + '/' + city;

	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.open('get', url);

	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) { 
			getStateCityCallback(xmlHttpRequest.responseText);
		} 
	};

	xmlHttpRequest.send(null);
}

function onGetStateButton() {

	var state = document.getElementById("stateSearch").value;
	var url = api_base_url + state;
	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.open('get', url);

	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) { 
			getStateCityCallback(xmlHttpRequest.responseText);
		} 
	};

	xmlHttpRequest.send(null);
}

function onGetCompareButton() {
	var state1 = document.getElementById("stateC1").value;
	var city1  = document.getElementById("cityC1").value;
	var state2 = document.getElementById("stateC2").value;
	var city2  = document.getElementById("cityC2").value;

	if (city1 == '' && city2 == '') {
		var url = api_base_url + 'compare/' + state1 + '/' + state2;
	} else {
		var url = api_base_url + 'compare/' + state1 + '/' + city1 + '/' + state2 + '/' + city2;
	}

	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.open('get', url);

	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) { 
			getCompareCallback(xmlHttpRequest.responseText);
		} 
	};

	xmlHttpRequest.send(null);
}

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

function getStateCityCallback(responseText) {
	var statesList = JSON.parse(responseText);
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

function onFindButton() {
	var min = document.getElementById("min_temp").value;
	var max = document.getElementById("max_temp").value;
	var num_results = document.getElementById("num_results").value;
	var url = api_base_url;

	if (document.getElementById("cities").checked == true) {
		url +=  'range/city/' + min + '/' + max + '/' + num_results;

		if (document.getElementById("days").checked == true) {
			url = api_base_url + 'range/city/days/' + min + '/' + max + '/' + num_results;
		}
	} else {
		url += 'range/' + min + '/' + max;

	}

	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.open('get', url);

	xmlHttpRequest.onreadystatechange = function() {
		if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) { 
			getCityCallback(xmlHttpRequest.responseText);
		} 
	};

	xmlHttpRequest.send(null);
}

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
			tableBody += '<td>'+ '<button' + ' onclick="initialize();codeAddress(\'' + dict['City'] + '\')">' + dict['City'] + '</button>' + '</td>'
			console.log('<td>'+ '<button' + ' onclick="initialize();codeAddress(' + dict['City'] + ')">' + dict['City'] + '</button>' + '</td>');
			//buttonChanger(city, count);

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



	var resultsTableElement = document.getElementById('results_table');
	resultsTableElement.innerHTML = tableBody;

}	

/*function buttonChanger(city, count) {
	 document.getElementsByTagName("BUTTON")[0].setAttribute("id", city);
	 document.getElementById(city).setAttribute("value", city);
	 alert(document.getElementById(city).value)
	 document.getElementById(city).setAttribute("onclick", printer(this.id));
}*/

function printer(id) {
	alert(id);

}




function onHomeNav() {
	var homeNavButton = document.getElementById('nav_bar');
	nav_bar.innerHTML = '	<li><a class="active" id="home" onclick="onHomeNav()">Home</a></li>\n' +
						'	<li><a id="state" onclick="onStateNav()">State</a></li>\n' +
						'	<li><a id="city" onclick="onCityNav()">City</a></li>\n' +
						'   <li><a id="compare" onclick="onCompareNav()">Compare</a></li>\n' +
						'   <li><a id="find_city" onclick="onFindCityNav()">Find City</a></li>\n' +
						'	<li style="float: right"><a id="about" onclick="onAboutNav()">About</a></li>\n';

	var page = document.getElementById('page');
	page.innerHTML = '<h1>Weather Weather Weather</h1>\n' +
					 '<img src="Static/jeff_square_head.jpg">';
}

function onStateNav() {
	var homeNavButton = document.getElementById('nav_bar');
	nav_bar.innerHTML = '	<li><a id="home" onclick="onHomeNav()">Home</a></li>\n' +
						'	<li><a class="active" id="state" onclick="onStateNav()">State</a></li>\n' +
						'	<li><a id="city" onclick="onCityNav()">City</a></li>\n' +
						'   <li><a id="compare" onclick="onCompareNav()">Compare</a></li>\n' +
						'   <li><a id="find_city" onclick="onFindCityNav()">Find City</a></li>\n' +
						'	<li style="float: right"><a id="about" onclick="onAboutNav()">About</a></li>\n';

	var page = document.getElementById('page');
	page.innerHTML = '<h1>State Search</h1>\n' +
					 '<input type="search" id="stateSearch" placeholder="Search for a state..">\n' +
					 '<button id="authors_button" onclick="onGetStateButton(\'Max Temp\')">Get State</button>\n' + 
					 '<div id="assignment_content">\n' +
					 '	<p><table id="results_table"> </table></p>\n' +
					 '</div>';
}

function onCityNav() {
	var homeNavButton = document.getElementById('nav_bar');
	nav_bar.innerHTML = '	<li><a id="home" onclick="onHomeNav()">Home</a></li>\n' +
						'	<li><a id="state" onclick="onStateNav()">State</a></li>\n' +
						'	<li><a class="active" id="city" onclick="onCityNav()">City</a></li>\n' +
						'   <li><a id="compare" onclick="onCompareNav()">Compare</a></li>\n' +
						'   <li><a id="find_city" onclick="onFindCityNav()">Find City</a></li>\n' +
						'	<li style="float: right"><a id="about" onclick="onAboutNav()">About</a></li>\n';

	var page = document.getElementById('page');
	page.innerHTML = '<h1>City Search</h1>\n' +
					 '<input type="search" id="stateS" placeholder="Enter a state..">\n' +
					 '<input type="search" id="cityS" placeholder="Enter a city..">\n' +
					 '<button id="authors_button" onclick="onGetCityButton(\'Min Temp\')">Get City</button>\n' + 
					 '<div id="assignment_content">\n' +
					 '	<p><table id="results_table"> </table></p>\n' +
					 '</div>';
}

function onCompareNav() {
	var homeNavButton = document.getElementById('nav_bar');
	nav_bar.innerHTML = '	<li><a id="home" onclick="onHomeNav()">Home</a></li>\n' +
						'	<li><a id="state" onclick="onStateNav()">State</a></li>\n' +	
						'	<li><a id="city" onclick="onCityNav()">City</a></li>\n' +
						'   <li><a class="active" id="compare" onclick="onCompareNav()">Compare</a></li>\n' +
						'   <li><a id="find_city" onclick="onFindCityNav()">Find City</a></li>\n' +
						'	<li style="float: right"><a id="about" onclick="onAboutNav()">About</a></li>\n';

	var page = document.getElementById('page');
	page.innerHTML = '      <h1>Compare</h1>\n' +
					 '<input type="search" id="stateC1" placeholder="Enter a state..">\n' +
					 '<input type="search" id="cityC1" placeholder="Enter a city..">\n' +
					 '<p style="display:inline"> vs. </p>\n' + 
					 '<input type="search" id="stateC2" placeholder="Enter a state..">\n' +
					 '<input type="search" id="cityC2" placeholder="Enter a city..">\n' +
					 '<button id="authors_button" onclick="onGetCompareButton(\'Min Temp\')">Compare</button>\n' + 
					 '<div id="assignment_content">\n' +
					 '	<p><table id="results_table"> </table></p>\n' +
					 '</div>';
}

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
function initialize() {
	geocoder = new google.maps.Geocoder();
	var latlng = new google.maps.LatLng(100, 40);
	var mapOptions = {
		zoom: 5,
		center: latlng
	}
	map = new google.maps.Map(document.getElementById('map'), mapOptions);
}

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

function onFindCityNav() {
	var homeNavButton = document.getElementById('nav_bar');
	nav_bar.innerHTML = '	<li><a id="home" onclick="onHomeNav()">Home</a></li>\n' +
						'	<li><a id="state" onclick="onStateNav()">State</a></li>\n' +	
						'	<li><a id="city" onclick="onCityNav()">City</a></li>\n' +
						'   <li><a id="compare" onclick="onCompareNav()">Compare</a></li>\n' +
						'   <li><a class="active" id="find_city" onclick="onFindCityNav()">Find City</a></li>\n' +
						'	<li style="float: right"><a id="about" onclick="onAboutNav()">About</a></li>\n';

	var page = document.getElementById('page');
	page.innerHTML = '<h1>Find a Happy Home</h1>\n' +
					 '<div>\n' +
					 '<form>\n' +
					 '<input id = "min_temp" style="width: 5em" type="search" name="min_temp" placeholder="Low (F)...">\n' +
					 '<input id = "max_temp" style="width: 5em" type="search" name="max_temp" placeholder="High (F)...">\n' +
					 '<input id = "num_results" style="width: 5em" type="search" name="num_results" placeholder="Results..."><br>\n' +
					 '<input id = "cities" onchange="setDaysCheck()" type="radio" name="query" value="stuff" checked> Cities\n' +
					 '<input id = "states" onchange="setDaysCheck()" type="radio" name="query" value="stuff2"> States<br>\n' +
					 '<input id = "days" type="checkbox" name="days" value="stuff"> Days\n' +
					 '</form>\n' + 


					 '<button style="width: 10%" id="authors_button" onclick="onFindButton()">Find</button>\n' + 
					 '</div>\n' +
					 '<style> #map {width: 60%;height: 400px;float: right;}</style>\n' + 
					 '<div id="assignment_content" style="width=30%; float:left">\n' +
					 
					 '	<p><table id="results_table"> </table></p>\n' +
					 '</div>\n' +
					 '<div id="map" ></div>';

	 var head = document.head;
	 head.innerHTML += '<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAjbWfUeCBmVzvJT4Na9cCPAWnB7XllbNo&callback=initMap"></script>\n'
}

function onAboutNav() {
	var homeNavButton = document.getElementById('nav_bar');
	nav_bar.innerHTML = '	<li><a id="home" onclick="onHomeNav()">Home</a></li>\n' +
						'	<li><a id="state" onclick="onStateNav()">State</a></li>\n' +
						'	<li><a id="city" onclick="onCityNav()">City</a></li>\n' +
						'   <li><a id="compare" onclick="onCompareNav()">Compare</a></li>\n' +
						'	<li style="float: right"><a class ="active" id="about" onclick="onAboutNav()">About</a></li>\n';

	var page = document.getElementById('page');
	page.innerHTML = '<h1>Info about collection,features, etc\n' +
					 '<button id="authors_button" onclick="jeffe()">Get Picture</button>\n' + 
					 '<div id="assignment_content">\n' +
					 '	<p><table id="results_table"> </table></p>\n' +
					 '</div>';
}

function jeffe() {
	var resultsTableElement = document.getElementById('results_table');
	resultsTableElement.innerHTML = '<img src="Static/jeff_square_head.jpg">\n' +
									'<img style="float: right" src="Static/jeff_square_head.jpg">'; 
}