/*
 *  Website.js
 *  Martin Hoffman and Chris Tordi, 8 May 2016
 *
 *  A little bit of Javascript
 */

var api_base_url = 'http://localhost:5000/';

function onGetStateButton() {
	var url = api_base_url + 'CA';
	alert(url);
	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.open('get', url);

    xmlHttpRequest.onreadystatechange = function() {
            if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) { 
            	alert("xmlHttpRequest ran")
                getStateCallback(xmlHttpRequest.responseText);
            } 
		
	};

	xmlHttpRequest.send(null);
}

function getStateCallback(responseText) {
	alert("getStateCallback ran");
	var statesList = JSON.parse(responseText);
	var result = '';
	result += '<tr>'

	result += '<td>' + statesList[0]
	result += '</td>'
	result += '</tr> '


	var resultReturn = document.getElementById('results_table');
	resultReturn.innerHTML = result;


}