/*
 *  Website.js
 *  Martin Hoffman and Chris Tordi, 8 May 2016
 *
 *  A little bit of Javascript
 */

var api_base_url = 'thacker.mathcs.carleton.edu:5136/';

function onGetStateButton() {
	var url = api_base_url + 'CA';
	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.open('get', url);

    xmlHttpRequest.onreadystatechange = function() {
        if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) { 
            getStateCallback(xmlHttpRequest.responseText);
        } 
		
	};

	xmlHttpRequest.send(null);
}

function getStateCallback(responseText) {
	var statesList = JSON.parse(responseText);
	var tableBody = '';
    tableBody += '<tr>';
    tableBody += '<td>';
    tableBody += statesList[0]['max CA temp'];
    tableBody += '</td>';
    tableBody += '</tr>';

	var resultsTableElement = document.getElementById('results_table');
	resultsTableElement.innerHTML = tableBody;
}