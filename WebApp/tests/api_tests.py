'''
	api_tests.py
	Chris Tordi and Martin Hoffman, 24 April 2017

'''

import unittest
import json
import urllib.request

class QueryTester(unittest.TestCase):
	def setUP(self):
		pass

	def tearDown(self):
		pass

	''' Tests query for empty query return
		Expected QueryValue: mean temp for state
		queryValues: returned empty list for invalid state
	'''
	def testEmpty(self):
		queryValues = []
		expectedDataValue = 72.5
		self.assertIn(expectedDataValue,queryValues)

	''' Tests query for station ids in South Dakota in 2015
		queryValues: list of station ids we would find in state
		knwonStationId: known station id
	'''
	def testStation(self): 
		url = 'http://localhost:5000/stations/<state>/stn_id'
		json_string = url.request.open(url).read()
		result = json.loads(json_string)
		queryValues = ["1234567", "1245643", "7654321"]
		knownStationId = '1234567'
		self.assertIn(knownStationId, queryValues)

	''' Tests query for max temperature in South Dakota in 2015
		queryValues: example return list that we might expect from eventual query
		maxInTestData: Known max temp for south dakota in 2015
	'''
	def testMax(self):
		url = 'http://localhost:5000/weather/max/<stn_ids>'
		json_string = url.request.open(url).read()
		result = json.loads(json_string)
		queryValues = [5,16.9,19.8,20.8,26.2,30.7,31.8,32.5,38.5,38.7,39.7,42.3,42.3,44.8,52.9,72.5]
		maxInTestData = 72.5
		self.assertIn(maxInTestData,queryValues)

	''' Tests query for min temperature in South Dakota in 2015
		queryValues: example return list that we might expect from eventual query
		maxInTestData: Known min temp for south dakota in 2015
	'''
	def testMin(self):
		url = 'http://localhost:5000/weather/min/<stn_ids>'
		json_string = url.request.open(url).read()
		result = json.loads(json_string)
		queryValues = [5,16.9,19.8,20.8,26.2,30.7,31.8,32.5,38.5,38.7,39.7,42.3,42.3,44.8,52.9,72.5]
		minInTestData = 5
		self.assertIn(minInTestData,queryValues)

	''' Tests query for mean temperature in South Dakota in 2015
		queryValues: example return list that we might expect from eventual query
		maxInTestData: Known mean temp for south dakota in 2015
	'''
	def testMean(self):
		url = 'http://localhost:5000/weather/average/<stn_ids>'
		json_string = url.request.open(url).read()
		result = json.loads(json_string)
		queryValues = [5,16.9,19.8,20.8,26.2,30.7,31.8,32.5,38.5,38.7,39.7,42.3,42.3,44.8,52.9,72.5]
		meanInTestData = 34.7
		self.assertEquals(meanInTestData,34.7)

	''' Tests query for the number of rainy days in South Dakota in 2015
		queryValues: example return list of lists for days with rain
		rainyDays: Known number of rainy days for South Dakota in 2015
	'''
	def testRainy(self):
		url = 'http://localhost:5000/weather/rain_drizzle/<stn_ids>'
		json_string = url.request.open(url).read()
		result = json.loads(json_string)
		queryValues = [[2, 3], [7, 26], [5, 29]]
		rainyDays = 3
		self.assertEquals(queryValues.length, rainyDays)

	''' Tests query for the number of snowy days in South Dakota in 2015
		queryValues: example return list of lists for days with snow
		rainyDays: Known number of snowy days for South Dakota in 2015
	'''
	def testSnowy(self):
		url = 'http://localhost:5000/weather/snow_ice_pellets/<stn_ids>'
		json_string = url.request.open(url).read()
		result = json.loads(json_string)
		queryValues = [[2, 3], [7, 26], [5, 29]]
		snowyDays = 3
		self.assertEquals(queryValues.length, snowyDays)

		
if __name__ == '__main__':
	unittest.main()