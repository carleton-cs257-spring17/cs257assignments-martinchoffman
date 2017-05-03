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


	def testEmpty(self):
		''' Tests query for empty query return
			Expected QueryValue: mean temp for state
			queryValues: returned empty list for invalid state
		'''
		url = urlStub + 'XZ'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[0]
		expectedDataValue = None
		self.assertEqual(expectedDataValue, queryValues)


	def testStations(self): 
		''' Tests query for station ids in South Dakota in 2015
			queryValues: list of station ids we would find in state
			knwonStationId: known station id
		'''
		url = urlStub + 'stations/FL'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		
		knownStationNum = 270
		self.assertEqual(knownStationNum, result)


	def testMax(self):
		''' Tests query for max temperature in South Dakota in 2015
			queryValues: example return list that we might expect from eventual query
			maxInTestData: Known max temp for south dakota in 2015
		'''
		url = urlStub + 'FL'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[0].get('max FL temp')
		maxInTestData = 124.0
		self.assertEqual(maxInTestData, queryValues)


	def testMin(self):
		''' Tests query for min temperature in South Dakota in 2015
			queryValues: example return list that we might expect from eventual query
			maxInTestData: Known min temp for south dakota in 2015
		'''
		url = urlStub + 'MA'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[1].get('min MA Temp')
		minInTestData = -18.9
		self.assertEqual(minInTestData, queryValues)


	def testMean(self):
		''' Tests query for mean temperature in South Dakota in 2015
			queryValues: example return list that we might expect from eventual query
			maxInTestData: Known mean temp for south dakota in 2015
		'''
		url = urlStub + 'MN'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[2].get('mean MN temp')
		meanInTestData = 45.9
		self.assertEqual(meanInTestData, queryValues)


	def testRainy(self):
		''' Tests query for the number of rainy days in South Dakota in 2015
			queryValues: example return list of lists for days with rain
			rainyDays: Known number of rainy days for South Dakota in 2015
		'''
		url = urlStub + 'CA'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[3].get('Rain Index in CA')
		rainIndex = 17.0
		self.assertEqual(queryValues, rainIndex)


	def testSnowy(self):
		''' Tests query for the snow index in MN in 2016
			queryValues: query return
			SnowIndex: Known index for MN in 2016
		'''
		url = urlStub + 'MN'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[4].get('Snow Index in MN')
		snowIndex = 33.0
		self.assertEqual(queryValues, snowIndex)

		
if __name__ == '__main__':
	unittest.main()