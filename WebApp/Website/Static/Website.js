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

function getStateCityCallback(responseText) {
	var statesList = JSON.parse(responseText);
	var tableBody = '';
    tableBody += '<tr>';
    tableBody += '<td>' + 'Max Temp' + '</td>';
    tableBody += '<td>' + 'Min Temp' + '</td>';
    tableBody += '<td>' + 'Mean Temp' + '</td>';
    tableBody += '<td>' + 'Rain Index' + '</td>';
    tableBody += '<td>' + 'Snow Index' + '</td>';
    tableBody += '</tr>';

    tableBody += '<tr>';
    tableBody += '<td>' + statesList[0]['Max Temp'] + '</td>';
    tableBody += '<td>' + statesList[1]['Min Temp'] + '</td>';
    tableBody += '<td>' + statesList[2]['Mean Temp'] + '</td>';
    tableBody += '<td>' + statesList[3]['Rain Index'] + '</td>';
    tableBody += '<td>' + statesList[4]['Snow Index'] + '</td>';
    tableBody += '</tr>';

	var resultsTableElement = document.getElementById('results_table');
	resultsTableElement.innerHTML = tableBody;
}

function onHomeNav() {
	var homeNavButton = document.getElementById('nav_bar');
	nav_bar.innerHTML = '	<li><a class="active" id="home" onclick="onHomeNav()">Home</a></li>\n' +
						'	<li><a id="state" onclick="onStateNav()">State</a></li>\n' +
						'	<li><a id="city" onclick="onCityNav()">City</a></li>\n' +
						'   <li><a id="compare" onclick="onCompareNav()">Compare</a></li>\n' +
						'	<li style="float: right"><a id="about" onclick="onAboutNav()">About</a></li>\n';

	var page = document.getElementById('page');
	page.innerHTML = '<h1>Weather Weather Weather</h1>\n' +
					 '<img src="Static/jeff_square_head.jpg">'
}

function onStateNav() {
	var homeNavButton = document.getElementById('nav_bar');
	nav_bar.innerHTML = '	<li><a id="home" onclick="onHomeNav()">Home</a></li>\n' +
						'	<li><a class="active" id="state" onclick="onStateNav()">State</a></li>\n' +
						'	<li><a id="city" onclick="onCityNav()">City</a></li>\n' +
						'   <li><a id="compare" onclick="onCompareNav()">Compare</a></li>\n' +
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
						'	<li style="float: right"><a id="about" onclick="onAboutNav()">About</a></li>\n';

	var page = document.getElementById('page');
	page.innerHTML = '<h1>Compare</h1>\n';
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