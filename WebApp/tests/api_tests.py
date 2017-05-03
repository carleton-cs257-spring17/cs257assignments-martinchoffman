'''
	api_tests.py
	Chris Tordi and Martin Hoffman, 24 April 2017

'''

import unittest
import json
import urllib.request

urlStub = 'http://localhost:5000/'

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
		url = urlStub + 'FL'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result.get('FL')
		expectedDataValue = 72.5
		self.assertIn(expectedDataValue,queryValues)

	''' Tests query for station ids in South Dakota in 2015
		queryValues: list of station ids we would find in state
		knwonStationId: known station id
	'''
	def testStation(self): 
		url = urlStub + 'FL'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[].get('FL')
		knownStationId = '1234567'
		self.assertIn(knownStationId, queryValues)

	''' Tests query for max temperature in South Dakota in 2015
		queryValues: example return list that we might expect from eventual query
		maxInTestData: Known max temp for south dakota in 2015
	'''
	def testMax(self):
		url = urlStub + 'FL'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[0].get('max FL temp')
		maxInTestData = 124.0
		self.assertEquals(maxInTestData, queryValues)

	''' Tests query for min temperature in South Dakota in 2015
		queryValues: example return list that we might expect from eventual query
		maxInTestData: Known min temp for south dakota in 2015
	'''
	def testMin(self):
		url = urlStub + 'MA'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[1].get('min MA Temp')
		minInTestData = -18.9
		self.assertEquals(minInTestData,queryValues)

	''' Tests query for mean temperature in South Dakota in 2015
		queryValues: example return list that we might expect from eventual query
		maxInTestData: Known mean temp for south dakota in 2015
	'''
	def testMean(self):
		url = urlStub + 'MN'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[2].get('mean MN temp')
		meanInTestData = 45.9
		self.assertEquals(meanInTestData, queryValues)

	''' Tests query for the number of rainy days in South Dakota in 2015
		queryValues: example return list of lists for days with rain
		rainyDays: Known number of rainy days for South Dakota in 2015
	'''
	def testRainy(self):
		url = urlStub + 'CA'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[3].get('Rain Index in CA')
		rainIndex = 17.0
		self.assertEquals(queryValues, rainIndex)

	''' Tests query for the number of snowy days in South Dakota in 2015
		queryValues: example return list of lists for days with snow
		rainyDays: Known number of snowy days for South Dakota in 2015
	'''
	def testSnowy(self):
		url = urlStub + 'weather/snow_ice_pellets/<stn_ids>'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result.get('FL')
		snowyDays = 3
		self.assertEquals(queryValues.length, snowyDays)

		
if __name__ == '__main__':
	unittest.main()