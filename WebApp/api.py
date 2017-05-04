#!/usr/bin/env python3
'''
    api.py
    Chris Tordi and Martin Hoffman

    Simple Flask API used in the weather web app for
    CS 257, Spring 2016-2017. 
'''
import sys
import flask
import json
import psycopg2
import urllib

app = flask.Flask(__name__, static_folder='static', template_folder='templates')


def _fetch_all_rows_for_query(query):
    '''
    Returns a list of rows obtained from the books database
    by the specified SQL query. If the query fails for any reason,
    an empty list is returned.
    '''
    try:
        connection = psycopg2.connect(database='tordic', user='tordic', 
        							  password='fork297moon')
    except Exception as e:
        print('Connection error:', e, file=sys.stderr)
        return []

    rows = []
    try:
        cursor = connection.cursor()
        cursor.execute(query)
        rows = cursor.fetchall() # Touble if your query results are really big.
    except Exception as e:
        print('Error querying database:', e, file=sys.stderr)

    connection.close()
    return rows


def get_stations_by_state(state_name):
    '''
    Parameter: state
    Returns number of stations in a state
    '''
    state_name = state_name.upper()

    query = "SELECT * FROM stations WHERE stations.state = '{0}'".format(state_name)
    
    return len(_fetch_all_rows_for_query(query))


def get_stations_by_city(state_name, city_name):
    '''
        Parameters: state, city
        Return: returns number of stations in city
    '''
    state_name = state_name.upper()
    city_name = city_name.upper()

    query = ("SELECT * FROM stations WHERE stations.name LIKE '%' || '{1}' || "
    		"'%' AND stations.state = '{0}'").format(state_name, city_name)
    
    return len(_fetch_all_rows_for_query(query))


def get_max(state_name):
    '''
        Returns a dictionary containing with information about the max 
        temperature in a state for 2016
    '''
    state_name = state_name.upper()

    query = ("SELECT a.max FROM weather a WHERE a.stn_id IN "
    		"(SELECT b.stn_id FROM stations b WHERE b.state = '{0}') "
    		"ORDER BY max DESC LIMIT 1").format(state_name)

    max_list = []
    
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    for row in _fetch_all_rows_for_query(query):
        max = {'max {} temp'.format(state_name):row[0]}
        max_list.append(max)

    return max


def get_min(state_name):
    '''
        Paramter: state
        Returns a dictionary containing with information about the min 
        temperature in a state for 2016
    '''
    state_name = state_name.upper()
    
    query = ("SELECT a.min FROM weather a WHERE a.stn_id IN "
    "(SELECT b.stn_id FROM stations b WHERE b.state = '{0}') "
    "ORDER BY min ASC LIMIT 1").format(state_name)

    min_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    for row in _fetch_all_rows_for_query(query):
        min = {'min {} Temp'.format(state_name):row[0]}
        min_list.append(min)

    return min


def get_mean(state_name):
    '''
        Parameter: state
        Return: dictionary of average mean temperatures for year in state
    '''
    state_name = state_name.upper()
    
    query = ("SELECT a.temp FROM weather a WHERE a.stn_id IN "
    		"(SELECT b.stn_id FROM stations b WHERE b.state = '{0}')").format(state_name)

    mean_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None
    for row in _fetch_all_rows_for_query(query):
        mean_list.append(row[0])

    mean = round((sum(mean_list)/len(mean_list)), 1)
    mean_dict = {'mean {} temp'.format(state_name):mean}

    return mean_dict


def get_rainy_days(state_name):
    '''
    Param: state
    Return: rain index for state in form of dictionary
    '''
    state_name = state_name.upper()

    query = ("SELECT a.rain_drizzle FROM weather a WHERE a.stn_id IN "
    		"(SELECT b.stn_id FROM stations b WHERE b.state = '{0}')").format(state_name)
    rain_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    for row in _fetch_all_rows_for_query(query):
        if len(row) == 0:
            return None
        if row[0] != '0':
            rain_list.append(row[0])

    rainy_days = round(len(rain_list)/get_stations_by_state(state_name), 0)
    rain_dict = {'Rain Index in {}'.format(state_name): rainy_days}

    return rain_dict


def get_snowy_days(state_name):
    '''
    Param: state
    Return: snow index for state in form of dictionary
    '''
    state_name = state_name.upper()
    
    query = ("SELECT a.snow_ice_pellets FROM weather a WHERE a.stn_id IN "
    		"(SELECT b.stn_id FROM stations b WHERE b.state = '{0}')").format(state_name)

    if query == None:
        return None

    snow_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    for row in _fetch_all_rows_for_query(query):
        if len(row) == 0:
            return None
        if row[0] != '0':
            snow_list.append(row[0])

    snowy_days = round(len(snow_list)/get_stations_by_state(state_name), 0)
    snow_dict = {'Snow Index in {}'.format(state_name): snowy_days}

    return snow_dict


def get_max_city(state_name, city_name):
    '''
        returns max temp for city
    '''
    state_name = state_name.upper()
    city_name = city_name.upper()
    
    query = ("SELECT a.max FROM weather a WHERE a.stn_id IN "
		    "(SELECT b.stn_id FROM stations b WHERE b.name LIKE '%' || "
		    "'{1}' || '%' AND b.state = '{0}') ORDER BY max "
		    "DESC LIMIT 1").format(state_name, city_name)

    max_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    for row in _fetch_all_rows_for_query(query):
        max = {'max {} Temp'.format(city_name):row[0]}
        max_list.append(max)

    return max


def get_min_city(state_name, city_name):
    ''' 
        returns min temp in city for 2016
    '''
    state_name = state_name.upper()
    city_name = city_name.upper()

    query = ("SELECT a.min FROM weather a WHERE a.stn_id IN " 
		    "(SELECT b.stn_id FROM stations b WHERE b.name LIKE '%' || '{1}' " 
		    "|| '%' AND b.state = '{0}') ORDER BY min "
		    "ASC LIMIT 1").format(state_name, city_name)

    min_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    for row in _fetch_all_rows_for_query(query):
        min = {'min {} Temp'.format(city_name):row[0]}
        min_list.append(min)

    return min


def get_mean_city(state_name, city_name):
    '''
        gets mean temp for city in 2016
    '''
    state_name = state_name.upper()
    city_name = city_name.upper()

    query = ("SELECT a.temp FROM weather a WHERE a.stn_id IN "
		    "(SELECT b.stn_id FROM stations b WHERE b.name LIKE '%' || "
		    "'{1}' || '%' AND b.state = '{0}')").format(state_name, city_name)

    mean_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    for row in _fetch_all_rows_for_query(query):
        mean_list.append(row[0])

    mean = round((sum(mean_list)/len(mean_list)), 1)
    mean_city_dict = {'mean {} Temp'.format(city_name):mean}

    return mean_city_dict


def get_rainy_days_city(state_name, city_name):
    '''
        gets rain index in city
    '''
    state_name = state_name.upper()
    city_name = city_name.upper()

    query = ("SELECT a.rain_drizzle FROM weather a WHERE a.stn_id IN "
    		"(SELECT b.stn_id FROM stations b WHERE b.name LIKE '%' || "
    		"'{1}' || '%' AND b.state = '{0}')").format(state_name, city_name)

    rain_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    for row in _fetch_all_rows_for_query(query):
        if row[0] != '0':
            rain_list.append(row[0])

    rainy_days = round(len(rain_list)/get_stations_by_city(state_name, city_name), 0)
    rain_city_dict = {'Rain Index in {}'.format(city_name): rainy_days}

    return rain_city_dict


def get_snowy_days_city(state_name, city_name):
    '''
        gets snow index in city
    '''
    state_name = state_name.upper()
    city_name = city_name.upper()

    query = ("SELECT a.snow_ice_pellets FROM weather a WHERE a.stn_id IN "
    		"(SELECT b.stn_id FROM stations b WHERE b.name LIKE '%' || "
    		"'{1}' || '%' AND b.state = '{0}')").format(state_name, city_name)

    snow_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    for row in _fetch_all_rows_for_query(query):
        
        if row[0] != '0':
            snow_list.append(row[0])

    snowy_days = round(len(snow_list)/get_stations_by_city(state_name, city_name), 0)
    snow_city_dict = {'Snow Index in {}'.format(city_name): snowy_days}

    return snow_city_dict


@app.route('/stations/<state_name>')
def get_num_stations(state_name):
    '''
        returns json dump with number of stations in state
    '''
    return json.dumps(get_stations_by_state(state_name))


@app.route('/<state_name>')
def get_all_state(state_name):
    '''
        returns json dump with max,min,mean,rain index, and snow index for state
    '''
    main_list = []

    main_list.append(get_max(state_name))
    main_list.append(get_min(state_name))
    main_list.append(get_mean(state_name))
    main_list.append(get_rainy_days(state_name))
    main_list.append(get_snowy_days(state_name))

    return json.dumps(main_list)


@app.route('/<state_name>/<city_name>')
def get_all_city(state_name, city_name):
    '''
        returns json dump with max,min,mean,rain index, and snow index for state
    '''
    city_list = []
    city_list.append(get_max_city(state_name, city_name))
    city_list.append(get_min_city(state_name, city_name))
    city_list.append(get_mean_city(state_name, city_name))
    city_list.append(get_rainy_days_city(state_name, city_name))
    city_list.append(get_snowy_days_city(state_name, city_name))

    return json.dumps(city_list)


@app.route('/compare/<state_name1>/<state_name2>')
def compare_states(state_name1, state_name2):
    '''
        returns json dump with information for both states in query
    '''
    
    compare = []
    compare.append(get_max(state_name1))
    compare.append(get_max(state_name2))
    compare.append(get_min(state_name1))
    compare.append(get_min(state_name2))
    compare.append(get_mean(state_name1))
    compare.append(get_mean(state_name2))
    compare.append(get_rainy_days(state_name1))
    compare.append(get_rainy_days(state_name2))
    compare.append(get_snowy_days(state_name1))
    compare.append(get_snowy_days(state_name2))

    return json.dumps(compare)


@app.route('/compare/<state_name1>/<city_name1>/<state_name2>/<city_name2>')
def compare_cities(state_name1, city_name1, state_name2, city_name2):
    '''
        returns json dump with information for both cities in query
    '''
    city_compare = []
    city_compare.append(get_max_city(state_name1, city_name1))
    city_compare.append(get_max_city(state_name2, city_name2))
    city_compare.append(get_min_city(state_name1, city_name1))
    city_compare.append(get_min_city(state_name2, city_name2))
    city_compare.append(get_mean_city(state_name1, city_name1))
    city_compare.append(get_mean_city(state_name2, city_name2))
    city_compare.append(get_rainy_days_city(state_name1, city_name1))
    city_compare.append(get_rainy_days_city(state_name2, city_name2))
    city_compare.append(get_snowy_days_city(state_name1, city_name1))
    city_compare.append(get_snowy_days_city(state_name2, city_name2))
    
    return json.dumps(city_compare)


if __name__ == '__main__':

    app.run(host='thacker.mathcs.carleton.edu', port=5136, debug=True)