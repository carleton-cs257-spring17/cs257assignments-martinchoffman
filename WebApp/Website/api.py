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
from operator import itemgetter


app = flask.Flask(__name__, static_folder='Static', template_folder='Templates')


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
    Parameter: state
    Returns number of stations in a state
    '''
    state_name = state_name.upper()
    query = '''SELECT * FROM stations WHERE stations.state = '{0}' '''.format(state_name)
    
    return len(_fetch_all_rows_for_query(query))


def get_stations_by_city(state_name, city_name):
    '''
        Parameters: state, city
        Return: returns number of stations in city
    '''
    state_name = state_name.upper()
    city_name = city_name.upper()
    query = '''SELECT * FROM stations WHERE stations.name LIKE '%' || '{1}' || '%' AND stations.state = '{0}' '''.format(state_name, city_name)
    
    return len(_fetch_all_rows_for_query(query))


def get_max(state_name):
    '''
        Returns a dictionary containing with information about the max temperature in a state for 2016
    '''
    state_name = state_name.upper()
    query = '''SELECT a.max FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.state = '{0}') ORDER BY max DESC LIMIT 1'''.format(state_name)

    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    row = _fetch_all_rows_for_query(query)
    max = {'State': '{}'.format(state_name),'Max Temp':row[0]}
    return max


def get_min(state_name):
    '''
        Paramter: state
        Returns a dictionary containing with information about the min temperature in a state for 2016
    '''
    state_name = state_name.upper()
    query = '''SELECT a.min FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.state = '{0}') ORDER BY min ASC LIMIT 1'''.format(state_name)

    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    row = _fetch_all_rows_for_query(query)
    min = {'State': '{}'.format(state_name),'Min Temp':row[0]}
    return min


def get_mean(state_name):
    '''
        Parameter: state
        Return: dictionary of average mean temperatures for year in state
    '''
    state_name = state_name.upper()
    query = '''SELECT a.temp FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.state = '{0}')'''.format(state_name)

    mean_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None
    for row in _fetch_all_rows_for_query(query):
        mean_list.append(row[0])

    mean = round((sum(mean_list)/len(mean_list)), 1)
    mean_dict = {'State': '{}'.format(state_name),'Mean Temp':mean}
    return mean_dict


def get_rainy_days(state_name):
    '''
    Param: state
    Return: rain index for state in form of dictionary
    '''
    state_name = state_name.upper()
    query = '''SELECT a.rain_drizzle FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.state = '{0}')'''.format(state_name)
    
    rain_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    for row in _fetch_all_rows_for_query(query):
        if len(row) == 0:
            return None
        if row[0] != '0':
            rain_list.append(row[0])

    rainy_days = round(len(rain_list)/get_stations_by_state(state_name), 0)
    rain_dict = {'State': '{}'.format(state_name),'Rain Index':rainy_days}
    return rain_dict


def get_snowy_days(state_name):
    '''
    Param: state
    Return: snow index for state in form of dictionary
    '''
    state_name = state_name.upper()
    query = '''SELECT a.snow_ice_pellets FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.state = '{0}')'''.format(state_name)

    snow_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    for row in _fetch_all_rows_for_query(query):
        if len(row) == 0:
            return None
        if row[0] != '0':
            snow_list.append(row[0])

    snowy_days = round(len(snow_list)/get_stations_by_state(state_name), 0)
    snow_dict = {'State': '{}'.format(state_name),'Snow Index':snowy_days}
    return snow_dict


def get_max_city(state_name, city_name):
    '''
        returns max temp for city
    '''
    state_name = state_name.upper()
    city_name = city_name.upper()
    query = '''SELECT a.max FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.name LIKE '%' || '{1}' || '%' AND b.state = '{0}') ORDER BY max DESC LIMIT 1'''.format(state_name,city_name)

    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    row = _fetch_all_rows_for_query(query)
    max = {'State': '{}'.format(state_name), 'City':'{}'.format(city_name), 'Max Temp':row[0]}
    return max


def get_min_city(state_name, city_name):
    ''' 
        returns min temp in city for 2016
    '''
    state_name = state_name.upper()
    city_name = city_name.upper()
    query = '''SELECT a.min FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.name LIKE '%' || '{1}' || '%' AND b.state = '{0}') ORDER BY min ASC LIMIT 1'''.format(state_name,city_name)

    min_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    row = _fetch_all_rows_for_query(query)
    min = {'State': '{}'.format(state_name), 'City':'{}'.format(city_name), 'Min Temp':row[0]}
    return min


def get_mean_city(state_name, city_name):
    '''
        gets mean temp for city in 2016
    '''
    state_name = state_name.upper()
    city_name = city_name.upper()
    query = '''SELECT a.temp FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.name LIKE '%' || '{1}' || '%' AND b.state = '{0}')'''.format(state_name,city_name)

    mean_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    for row in _fetch_all_rows_for_query(query):
        mean_list.append(row[0])

    mean = round((sum(mean_list)/len(mean_list)), 1)
    mean_city_dict = {'State': '{}'.format(state_name), 'City':'{}'.format(city_name), 'Mean Temp':mean}
    return mean_city_dict


def get_rainy_days_city(state_name, city_name):
    '''
        gets rain index in city
    '''
    state_name = state_name.upper()
    city_name = city_name.upper()
    query = '''SELECT a.rain_drizzle FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.name LIKE '%' || '{1}' || '%' AND b.state = '{0}')'''.format(state_name,city_name)

    rain_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    for row in _fetch_all_rows_for_query(query):
        if row[0] != '0':
            rain_list.append(row[0])

    rainy_days = round(len(rain_list)/get_stations_by_city(state_name,city_name), 0)
    rain_city_dict = {'State': '{}'.format(state_name), 'City':'{}'.format(city_name), 'Rain Index': rainy_days}
    return rain_city_dict


def get_snowy_days_city(state_name, city_name):
    '''
        gets snow index in city
    '''
    state_name = state_name.upper()
    city_name = city_name.upper()
    query = '''SELECT a.snow_ice_pellets FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.name LIKE '%' || '{1}' || '%' AND b.state = '{0}')'''.format(state_name,city_name)

    snow_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None

    for row in _fetch_all_rows_for_query(query):
        if row[0] != '0':
            snow_list.append(row[0])

    snowy_days = round(len(snow_list)/get_stations_by_city(state_name,city_name), 0)
    snow_city_dict = {'State': '{}'.format(state_name), 'City':'{}'.format(city_name), 'Snow Index': snowy_days}
    return snow_city_dict


def get_cities(temp1, temp2):
    '''
        returns list of cities whose mean yearly temperature fits the range inputed by user
    '''
    temp1 = float(temp1)
    temp2 = float(temp2)

    final_city_list = []
    city_list = get_city_info()
    for dictionary in city_list:
        city = dictionary.get('city')
        query = '''SELECT a.temp FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.name LIKE '%' || '{0}' || '%')'''.format(city)
        mean_list = []
        
        for row in _fetch_all_rows_for_query(query):
            mean_list.append(row[0])

        if (len(mean_list) != 0):
            mean = round((sum(mean_list)/len(mean_list)),1)
        
            if mean >= temp1 and mean <= temp2:
                city_dict = {'State':'{}'.format(dictionary.get('state')),'City': '{}'.format(city), 'Mean Temp': mean}
                final_city_list.append(city_dict)
            
    
    return(final_city_list)

def get_cities_days(temp1, temp2):
    '''
        returns list of dictionaries whose values contain number of days where mean temp fits user range
    '''
    temp1 = float(temp1)
    temp2 = float(temp2)

    final_city_list = []
    city_list = get_city_info()
    for dictionary in city_list:
        city = dictionary.get('city')
        state = dictionary.get('state')
        query = '''SELECT a.temp FROM weather a WHERE a.stn_id IN (select b.stn_id from stations b where b.name LIKE '%' || '{0}' || '%' AND b.state = '{1}')'''.format(city,state)
        mean_list = []
        
        for row in _fetch_all_rows_for_query(query):
            if row[0] >= temp1 and row[0] <= temp2:
                mean_list.append(row[0])

        if (len(mean_list) != 0) and (get_stations_by_city(dictionary.get('state'), dictionary.get('city')) != 0):
            days = int(len(mean_list)/(get_stations_by_city(dictionary.get('state'), dictionary.get('city'))))
        
            city_dict = {'State':'{}'.format(dictionary.get('state')),'City': '{}'.format(city), "Days": days}
            final_city_list.append(city_dict)
            
    final_city_list = sort_by_dictionary(final_city_list)
    return(final_city_list)

def sort_by_dictionary(list_of_dictionaries):
    '''
        Parameter: list of dictionaries
        Returns: sorted list based on dictionary value
    '''
    sorted_list = sorted(list_of_dictionaries, key=itemgetter('Days'), reverse = True) 
    return sorted_list

def get_city_info():
    '''
        queries cities table and returns list of dictionaries with keys: city and state
    '''
    query = '''SELECT city, state FROM cities'''
    city_list = []
    for row in _fetch_all_rows_for_query(query):
        city_dict = {'city': row[0].upper(), 'state': row[1]}
        city_list.append(city_dict)
    
    return(city_list)


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
    city_list.append(get_max_city(state_name,city_name))
    city_list.append(get_min_city(state_name,city_name))
    city_list.append(get_mean_city(state_name,city_name))
    city_list.append(get_rainy_days_city(state_name,city_name))
    city_list.append(get_snowy_days_city(state_name,city_name))

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

@app.route('/range/<temp1>/<temp2>')
def find_states(temp1, temp2):
    '''
        returns json dump with information on mean temperature of states that fit user input range
    '''
    temp1 = float(temp1)
    temp2 = float(temp2)
    state_mean_temp_list = [{"AL": 66.6}, {"AK": 37.8}, {"AZ": 66.5}, {"AR": 62.7}, {"CA": 61.7}, {"CO": 47.5}, {"CT": 53.2}, {"DC": 59.7}, {"DE": 57.5}, {"FL": 72.8}, {"GA": 66.2}, {"HI": 75.1}, {"ID": 47.2}, {"IL": 54.7}, {"IN": 54.0}, {"IA": 51.3}, {"KS": 57.7}, {"KY": 58.0}, {"LA": 70.5}, {"ME": 46.2}, {"MD": 58.0}, {"MA": 52.1}, {"MI": 48.3}, {"MN": 45.9}, {"MS": 66.5}, {"MO": 58.7}, {"MT": 46.0}, {"NE": 51.9}, {"NV": 58.4}, {"NH": 47.2}, {"NJ": 56.6}, {"NM": 57.3}, {"NY": 51.6}, {"NC": 62.0}, {"ND": 43.6}, {"OH": 53.6}, {"OK": 62.3}, {"OR": 52.5}, {"PA": 53.3}, {"RI": 53.1}, {"SC": 64.7}, {"SD": 50.0}, {"TN": 61.4}, {"TX": 69.1}, {"UT": 52.4}, {"VT": 46.6}, {"VA": 58.9}, {"WA": 52.4}, {"WV": 54.7}, {"WI": 47.4}, {"WY": 45.1}]
    temp_range = []
    for state in state_mean_temp_list:
        for key in state:
            if state[key] >= temp1 and state[key] <= temp2:
                temp_range.append({'{}'.format(key): state[key]})

    return json.dumps(temp_range)


@app.route('/range/city/<temp1>/<temp2>/<numCities>')
def find_cities(temp1, temp2, numCities):
    '''
        returns json dump with cities whose mean temperature for 2016 fits range set by user
    '''
    temp1 = float(temp1)
    temp2 = float(temp2)
   
    numCities = int(numCities)

    return json.dumps(get_cities(temp1,temp2)[:numCities])

@app.route('/range/city/days/<temp1>/<temp2>/<numCities>')
def get_days(temp1, temp2, numCities):
    '''
        returns json dump with number of days where mean temp meets temp range.
        List is in descending order and capped at 10 cities
    ''' 
    numCities = int(numCities)
    return json.dumps(get_cities_days(temp1,temp2)[:numCities])

@app.after_request
def set_headers(response):
    response.headers['Access-Control-Allow-Origin'] = '*'
    return response


if __name__ == '__main__':
    app.run(host='localhost', port=5000, debug=True)