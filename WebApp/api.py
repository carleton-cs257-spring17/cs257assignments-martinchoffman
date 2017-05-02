#!/usr/bin/env python3
'''
    books_api.py
    Jeff Ondich, 25 April 2016

    Simple Flask API used in the sample web app for
    CS 257, Spring 2016-2017. This is the Flask app for the
    "books and authors" API only. There's a separate Flask app
    for the books/authors website.
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
        connection = psycopg2.connect(database='NOAA_Data', user='alextordi', password='')
    except Exception as e:
        print('Connection error:', e, file=sys.stderr)
        return []

    rows = []
    try:
        cursor = connection.cursor()
        cursor.execute(query)
        rows = cursor.fetchall() # This can be trouble if your query results are really big.
    except Exception as e:
        print('Error querying database:', e, file=sys.stderr)

    connection.close()
    return rows

def get_stations_by_state(state_name):
    '''
        get all stn_ids for a state
    '''
    query = '''SELECT * FROM stations WHERE stations.state = '{0}' '''.format(state_name)
    
    return len(_fetch_all_rows_for_query(query))

def get_stations_by_city(state_name, city_name):
    '''
        get all stn_ids for a state
    '''
    query = '''SELECT * FROM stations WHERE stations.name LIKE '%' || '{1}' || '%' AND stations.state = '{0}' '''.format(state_name, city_name)
    
    return len(_fetch_all_rows_for_query(query))

'''
    get max temp of state
'''
def get_max(state_name):
    query = '''SELECT a.max FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.state = '{0}') ORDER BY max DESC LIMIT 1'''.format(state_name)
    max_list = []

    for row in _fetch_all_rows_for_query(query):
    #url = flask.url_for('get_author_by_id', author_id=row[0], _external=True)
        max = {'max {} temp'.format(state_name):row[0]}
        max_list.append(max)

    print(max_list)

    return max


'''
    get min temp of state
'''
def get_min(state_name):
    

    query = '''SELECT a.min FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.state = '{0}') ORDER BY min ASC LIMIT 1'''.format(state_name)

    min_list = []

    for row in _fetch_all_rows_for_query(query):
    #url = flask.url_for('get_author_by_id', author_id=row[0], _external=True)
        min = {'min {} Temp'.format(state_name):row[0]}
        min_list.append(min)

    print(min_list)

    return min

def get_mean(state_name):
    query = '''SELECT a.temp FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.state = '{0}')'''.format(state_name)

    mean_list = []

    for row in _fetch_all_rows_for_query(query):
        mean_list.append(row[0])

    mean = round((sum(mean_list)/len(mean_list)), 1)

    mean_dict = {'mean {} temp'.format(state_name):mean}

    return mean_dict

def get_rainy_days(state_name):
    query = '''SELECT a.rain_drizzle FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.state = '{0}')'''.format(state_name)

    rain_list = []

    for row in _fetch_all_rows_for_query(query):
        if row[0] != '0':
            rain_list.append(row[0])

    print(rain_list)
    rainy_days = round(len(rain_list)/get_stations_by_state(state_name), 0)
    rain_dict = {'Total Rainy Days': rainy_days}

    return rain_dict


def get_snowy_days(state_name):
    query = '''SELECT a.snow_ice_pellets FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.state = '{0}')'''.format(state_name)

    snow_list = []

    for row in _fetch_all_rows_for_query(query):
        if row[0] != '0':
            snow_list.append(row[0])

    snowy_days = round(len(snow_list)/get_stations_by_state(state_name), 0)
    snow_dict = {'Total Snow Days': snowy_days}

    return snow_dict

'''
    returns max temp for city
'''

def get_max_city(state_name, city_name):
    query = '''SELECT a.max FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.name LIKE '%' || '{1}' || '%' AND b.state = '{0}') ORDER BY max DESC LIMIT 1'''.format(state_name,city_name)

    max_list = []

    for row in _fetch_all_rows_for_query(query):
    #url = flask.url_for('get_author_by_id', author_id=row[0], _external=True)
        max = {'max {} Temp'.format(city_name):row[0]}
        max_list.append(max)

    

    return max

def get_min_city(state_name, city_name):
    query = '''SELECT a.min FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.name LIKE '%' || '{1}' || '%' AND b.state = '{0}') ORDER BY min ASC LIMIT 1'''.format(state_name,city_name)

    min_list = []

    for row in _fetch_all_rows_for_query(query):
    #url = flask.url_for('get_author_by_id', author_id=row[0], _external=True)
        min = {'min {} Temp'.format(city_name):row[0]}
        min_list.append(min)

    return min


'''
    gets mean temp for city
'''
def get_mean_city(state_name, city_name):
    query = '''SELECT a.temp FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.name LIKE '%' || '{1}' || '%' AND b.state = '{0}')'''.format(state_name,city_name)
    mean_list = []

    for row in _fetch_all_rows_for_query(query):
        mean_list.append(row[0])

    mean = round((sum(mean_list)/len(mean_list)), 1)

    mean_city_dict = {'mean {} Temp'.format(city_name):mean}

    return mean_city_dict

'''
    gets total rainy days in city
'''
def get_rainy_days_city(state_name, city_name):
    query = '''SELECT a.rain_drizzle FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.name LIKE '%' || '{1}' || '%' AND b.state = '{0}')'''.format(state_name,city_name)

    rain_list = []
    station_counter = 0

    for row in _fetch_all_rows_for_query(query):

        if row[0] != '0':
            rain_list.append(row[0])

    rainy_days = round(len(rain_list)/get_stations_by_city(state_name,city_name), 0)
    rain_city_dict = {'Total Rainy Days in {}'.format(city_name): rainy_days}

    return rain_city_dict


'''
    gets total snowy days in city
'''
def get_snowy_days_city(state_name, city_name):
    query = '''SELECT a.snow_ice_pellets FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.name LIKE '%' || '{1}' || '%' AND b.state = '{0}')'''.format(state_name,city_name)

    snow_list = []
    station_counter = 0

    for row in _fetch_all_rows_for_query(query):
        
        if row[0] != '0':
            snow_list.append(row[0])

    snowy_days = round(len(snow_list)/get_stations_by_city(state_name,city_name), 0)
    snow_city_dict = {'Total Snowy Days in {}'.format(city_name): snowy_days}

    return snow_city_dict


'''
    State main
'''

@app.route('/<state_name>')
def get_all_state(state_name):
    main_list = []

    main_list.append(get_max(state_name))
    main_list.append(get_min(state_name))
    main_list.append(get_mean(state_name))
    main_list.append(get_rainy_days(state_name))
    main_list.append(get_snowy_days(state_name))

    return json.dumps(main_list)


@app.route('/<state_name>/<city_name>')
def get_all_city(state_name, city_name):
    city_list = []
    city_list.append(get_min_city(state_name,city_name))
    city_list.append(get_max_city(state_name,city_name))
    city_list.append(get_mean_city(state_name,city_name))
    city_list.append(get_rainy_days_city(state_name,city_name))
    city_list.append(get_snowy_days_city(state_name,city_name))

    return json.dumps(city_list)


@app.route('/compare/<state_name1>/<state_name2>')
def compare_states(state_name1, state_name2):
    
    compare = []
    compare.append(get_max(state_name1))
    compare.append(get_max(state_name2))
    compare.append(get_min(state_name1))
    compare.append(get_min(state_name2))
    compare.append(get_mean(state_name1))
    compare.append(get_mean(state_name2))
    compare.append(get_rainy_days(state_name1))
    compare.append(get_snowy_days(state_name2))

    return json.dumps(compare)


@app.route('/compare/<state_name1>/<city_name1>/<state_name2>/<city_name2>')
def compare_cities(state_name1, city_name1, state_name2, city_name2):
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

    app.run(host='thacker.mathcs.carleton.edu', port=5136)
