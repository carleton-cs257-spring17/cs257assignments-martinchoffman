/*
 *  Website.js
 *  Martin Hoffman and Chris Tordi, 8 May 2016
 *
 *  A little bit of Javascript
 */

var api_base_url = 'thacker.mathcs.carleton.edu:5136/';

function onGetStateButton() {
	var url = api_base_url + 'CA/';
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
	for (var k = 0; k < statesList.length; k++) {
		tableBody += '<tr>';

		tableBody += '<td><a onclick="getAuthor(' + statesList[k]['max FL temp'] + ",'"
							+ statesList[k]['first_name'] + ' ' + statesList[k]['last_name'] + "')\">"
							+ statesList[k]['last_name'] + ', '
							+ statesList[k]['first_name'] + '</a></td>';

		tableBody += '<td>' + statesList[k]['birth_year'] + '-';
		if (statesList[k]['death_year'] != 0) {
			tableBody += statesList[k]['death_year'];
		}
		tableBody += '</td>';
		tableBody += '</tr>';
	}


	var resultsTableElement = document.getElementById('results_table');
	resultsTableElement.innerHTML = tableBody;
}