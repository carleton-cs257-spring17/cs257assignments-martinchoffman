/*
 *  Website.js
 *  Martin Hoffman and Chris Tordi, 8 May 2016
 *
 *  A little bit of Javascript
 */

var api_base_url = 'http://thacker.mathcs.carleton.edu:5136/';

function onGetStateButton(key) {
	var url = api_base_url + 'CA';
	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.open('get', url);

    xmlHttpRequest.onreadystatechange = function() {
        if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) { 
            getStateCallback(xmlHttpRequest.responseText, key);
        } 
	};

	xmlHttpRequest.send(null);
}

function getStateCallback(responseText, key) {
	var statesList = JSON.parse(responseText);
	var tableBody = '';
    tableBody += '<tr>';
    tableBody += '<td>';
    if (key == 'Max Temp') { // Dictionary structure in the API is dumb
    	tableBody += statesList[0][key];    	
    } else if (key == 'Min Temp') {
    	tableBody += statesList[1][key];    	
    }
    tableBody += '</td>';
    tableBody += '</tr>';

	var resultsTableElement = document.getElementById('results_table');
	resultsTableElement.innerHTML = tableBody;
}

function onHomeNav() {
	var homeNavButton = document.getElementById('nav_bar');
	nav_bar.innerHTML = '	<li><a class="active" id="home" onclick="onHomeNav()">Home</a></li>\n' +
						'	<li><a id="max" onclick="onMaxNav()">Max Temp</a></li>\n' +
						'	<li><a id="min" onclick="onMinNav()">Min Temp</a></li>\n' +
						'	<li style="float: right"><a id="about" onclick="onAboutNav()">About</a></li>\n';

	var page = document.getElementById('page');
	page.innerHTML = '<h1>NOAA Weather Data n\' Shit</h1>\n' +
					 '<img src="Static/jeff_square_head.jpg">'
}

function onMaxNav() {
	var homeNavButton = document.getElementById('nav_bar');
	nav_bar.innerHTML = '	<li><a id="home" onclick="onHomeNav()">Home</a></li>\n' +
						'	<li><a class="active" id="max" onclick="onMaxNav()">Max Temp</a></li>\n' +
						'	<li><a id="min" onclick="onMinNav()">Min Temp</a></li>\n' +
						'	<li style="float: right"><a id="about" onclick="onAboutNav()">About</a></li>\n';

	var page = document.getElementById('page');
	page.innerHTML = '<h1>Max Temps n\' Shit</h1>\n' +
					 '<button id="authors_button" onclick="onGetStateButton(\'Max Temp\')">Get State</button>\n' + 
					 '<div id="assignment_content">\n' +
					 '	<p><table id="results_table"> </table></p>\n' +
					 '</div>';
}

function onMinNav() {
	var homeNavButton = document.getElementById('nav_bar');
	nav_bar.innerHTML = '	<li><a id="home" onclick="onHomeNav()">Home</a></li>\n' +
						'	<li><a id="max" onclick="onMaxNav()">Max Temp</a></li>\n' +
						'	<li><a class="active" id="min" onclick="onMinNav()">Min Temp</a></li>\n' +
						'	<li style="float: right"><a id="about" onclick="onAboutNav()">About</a></li>\n';

	var page = document.getElementById('page');
	page.innerHTML = '<h1>Min Temps n\' Shit</h1>\n' +
					 '<button id="authors_button" onclick="onGetStateButton(\'Min Temp\')">Get State</button>\n' + 
					 '<div id="assignment_content">\n' +
					 '	<p><table id="results_table"> </table></p>\n' +
					 '</div>';
}

function onAboutNav() {
	var homeNavButton = document.getElementById('nav_bar');
	nav_bar.innerHTML = '	<li><a id="home" onclick="onHomeNav()">Home</a></li>\n' +
						'	<li><a id="max" onclick="onMaxNav()">Max Temp</a></li>\n' +
						'	<li><a id="min" onclick="onMinNav()">Min Temp</a></li>\n' +
						'	<li style="float: right"><a class="active" id="about" onclick="onAboutNav()">About</a></li>\n';

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