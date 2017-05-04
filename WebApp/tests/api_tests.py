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
			Expected QueryValue: null
			queryValues: json return from api query
		'''
		url = urlStub + 'XZ'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[0]
		expectedDataValue = None
		self.assertEqual(expectedDataValue, queryValues)


	def testStations(self): 
		''' Tests query for station ids in FL in 2016
			queryValues: json return from api  query
			knwonStationId: known number of stations in state 
		'''
		url = urlStub + 'stations/FL'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		
		knownStationNum = 270
		self.assertEqual(knownStationNum, result)


	def testMax(self):
		''' Tests query for max temperature in FL in 2016
			queryValues: json return from api query
			maxInTestData: Known max temp for FL in 2016
		'''
		url = urlStub + 'FL'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[0].get('max FL temp')
		maxInTestData = 124.0
		self.assertEqual(maxInTestData, queryValues)

	def testMaxCity(self):
		''' Tests query for max temperature in Miami in 2016
			queryValues: example return list that we might expect from eventual query
			maxInTestData: Known max temp for miami in 2016
		'''
		url = urlStub + 'FL/MIAMI'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[0].get('max MIAMI Temp')
		maxInTestData = 97.0
		self.assertEqual(maxInTestData, queryValues)


	def testMin(self):
		''' Tests query for min temperature in mn in 2016
			queryValues: json return from query
			maxInTestData: Known min temp for mn in 2016
		'''
		url = urlStub + 'MA'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[1].get('min MA Temp')
		minInTestData = -18.9
		self.assertEqual(minInTestData, queryValues)


	def testMean(self):
		''' Tests query for mean temperature in MN in 2016
			queryValues: json return from query
			maxInTestData: Known mean temp for mn  in 2016
		'''
		url = urlStub + 'MN'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValues = result[2].get('mean MN temp')
		meanInTestData = 45.9
		self.assertEqual(meanInTestData, queryValues)


	def testRainy(self):
		''' Tests query for the rain index in Mn in CA
			queryValues: query return, json
			rainyIndex: known rain index for CA in 2016
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

	def testStateComparisonMean(self):
		'''
			Tests query for the  comparison of mean temo in MN  and FL in 2016
			queryValues: query return
			SnowIndex: Known mean temp for MN and FL
		'''
		url = urlStub + 'compare/FL/MN'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValuesMean_MN = result[5].get('mean MN temp')
		queryValuesMean_FL = result[4].get('mean FL temp')
		meanTemp_FL = 72.8
		meanTemp_MN = 45.9
		self.assertEqual(queryValuesMean_MN, meanTemp_MN)
		self.assertEqual(queryValuesMean_FL, meanTemp_FL)


	def testStateComparisonSnow(self):
		'''
			Tests query for the  comparison of snow index in MN  and FL in 2016
			queryValues: query return
			SnowIndex: Known index for MN and FL
		'''
		url = urlStub + 'compare/FL/MN'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValuesSnow_MN = result[9].get('Snow Index in MN')
		queryValuesSnow_FL = result[8].get('Snow Index in FL')
		snowIndex_FL = 0.0
		snowIndex_MN = 33.0
		self.assertEqual(queryValuesSnow_MN, snowIndex_MN)
		self.assertEqual(queryValuesSnow_FL, snowIndex_FL)


	def testCityComparisonMax(self):
		'''
			Tests query for the  comparison of max temp in miami  and boston in 2016
			queryValues: query return
			maxTemp: Known max temp for MN and FL
		'''
		url = urlStub + 'compare/FL/MIAMI/MA/BOSTON'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValuesMax_BOSTON = result[1].get('max BOSTON Temp')
		queryValuesMax_MIAMI = result[0].get('max MIAMI Temp')
		maxTemp_MIAMI = 97.0
		maxTemp_BOSTON = 96.1
		self.assertEqual(queryValuesMax_BOSTON, maxTemp_BOSTON)
		self.assertEqual(queryValuesMax_MIAMI, maxTemp_MIAMI)

	def testCityComparisonMin(self):
		'''
			Tests query for the  comparison of max temp in miami  and boston in 2016
			queryValues: query return
			SnowIndex: Known index for MN and FL
		'''
		url = urlStub + 'compare/FL/MIAMI/MN/BOSTON'
		json_string = urllib.request.urlopen(url).read()
		data = json_string.decode('utf-8')
		result = json.loads(data)
		queryValuesMin_BOSTON = result[3].get('min BOSTON Temp')
		queryValuesMin_MIAMI = result[2].get('min MIAMI Temp')
		minTemp_MIAMI = 42.1
		minTemp_BOSTON = -7.2
		self.assertEqual(queryValuesMin_BOSTON, minTemp_BOSTON)
		self.assertEqual(queryValuesMin_MIAMI, minTemp_MIAMI)



		
if __name__ == '__main__':
	unittest.main()