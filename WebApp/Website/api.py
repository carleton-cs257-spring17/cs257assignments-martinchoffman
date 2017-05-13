#!/usr/bin/env python3
"""
    api.py
    Chris Tordi and Martin Hoffman

    Simple Flask API used in the weather web app for
    CS 257, Spring 2016-2017. 
"""
import sys
import flask
import json
import psycopg2
from operator import itemgetter

app = flask.Flask(__name__, static_folder='Static', template_folder='Templates')


def _fetch_all_rows_for_query(query):
    """
    Returns a list of rows obtained from the books database
    by the specified SQL query. If the query fails for any reason,
    an empty list is returned.
    """
    try:
        connection = psycopg2.connect(database='hoffmanm2', user='hoffmanm2',
                                      password='snail749sunshine')
    except Exception as e:
        print('Connection error:', e, file=sys.stderr)
        return []

    rows = []
    try:
        cursor = connection.cursor()
        cursor.execute(query)
        rows = cursor.fetchall()  # Trouble if query results are really big.
    except Exception as e:
        print('Error querying database:', e, file=sys.stderr)

    connection.close()
    return rows


def get_stations_by_state(state_name):
    """
    Parameter: state
    Returns number of stations in a state
    """
    state_name = state_name.upper()
    query = '''SELECT * FROM stations WHERE stations.state = '{0}' '''.format(
        state_name)
    stations = _fetch_all_rows_for_query(query)
    return stations


def get_stations_by_city(state_name, city_name):
    """
    Parameters: state, city
    Return: returns number of stations in city
    """
    state_name = state_name.upper()
    city_name = city_name.upper()
    query = ("SELECT * FROM stations WHERE stations.name LIKE '%{1}%' "
             "AND stations.state = '{0}'").format(state_name, city_name)
    stations = _fetch_all_rows_for_query(query)
    return stations


def get_max(state_name):
    """
    Parameter: state
    Returns a dictionary containing with information about the max 
    temperature in a state for 2016
    """
    return get_max_min(state_name, 'max')


def get_min(state_name):
    """
    Parameter: state
    Returns a dictionary containing with information about the min 
    temperature in a state for 2016
    """
    return get_max_min(state_name, 'min')


def get_max_min(state_name, max_min):
    """
    Helper function for get_max and get_min
    :param state_name: 
    :param max_min: string, 'min' or 'max'
    :return: 
    """
    state_name = state_name.upper()

    query_order = ''
    if max_min == 'max':
        query_order = 'DESC'
    elif max_min == 'min':
        query_order = 'ASC'

    query = ("SELECT a.{1} FROM weather a WHERE a.stn_id IN "
             "(SELECT b.stn_id FROM stations b WHERE b.state = '{0}') "
             "ORDER BY {1} {2} LIMIT 1").format(state_name, max_min, query_order)

    row = _fetch_all_rows_for_query(query)
    if len(row) == 0:
        return None

    assert len(row) == 1
    temp = {'State': state_name, max_min.capitalize() + ' Temp': row[0]}
    return temp


def get_mean(state_name):
    """
    Parameter: state
    Return: dictionary of average mean temperatures for year in state
    """
    state_name = state_name.upper()
    query = ("SELECT a.temp FROM weather a WHERE a.stn_id IN "
             "(SELECT b.stn_id FROM stations b WHERE b.state = '{}')").format(
        state_name)

    mean_list = []
    if len((_fetch_all_rows_for_query(query))) == 0:
        return None
    for row in _fetch_all_rows_for_query(query):
        mean_list.append(row[0])

    mean = round((sum(mean_list) / len(mean_list)), 1)
    mean_dict = {'State': state_name, 'Mean Temp': mean}
    return mean_dict


def get_rainy_days(state_name):
    """
    Param: state
    Return: rain index for state in form of dictionary
    """
    return get_rainy_snowy_days(state_name, 'rain')


def get_snowy_days(state_name):
    """
    Param: state
    Return: snow index for state in form of dictionary
    """
    return get_rainy_snowy_days(state_name, 'snow')


def get_rainy_snowy_days(state_name, rain_snow):
    """
    Helper function for get_snowy_days and get_rainy_days
    :param state_name: 
    :param rain_snow: string, 'rain' or 'snow'
    :return: 
    """
    state_name = state_name.upper()

    column = ''
    if rain_snow == 'snow':
        column = 'snow_ice_pellets'
    elif rain_snow == 'rain':
        column = 'rain_drizzle'

    query = ("SELECT a.{1} FROM weather a WHERE a.stn_id IN "
             "(SELECT b.stn_id FROM stations b WHERE b.state = '{0}')").format(
        state_name, column)
    rows = _fetch_all_rows_for_query(query)

    if len(rows) == 0:
        return None

    prcp_list = []
    for row in rows:
        if len(row) == 0:
            return None
        elif row[0] != '0':
            prcp_list.append(row[0])

    prcp_days = round(
        len(prcp_list) / len(get_stations_by_state(state_name)), 0)
    prcp_dict = {'State': state_name,
                 rain_snow.capitalize() + ' Index': prcp_days}
    return prcp_dict


def get_max_city(state_name, city_name):
    """
    returns max temp for city
    """
    return get_min_max_city(state_name, city_name, 'max')


def get_min_city(state_name, city_name):
    """ 
    returns min temp in city for 2016
    """
    return get_min_max_city(state_name, city_name, 'min')


def get_min_max_city(state_name, city_name, max_min):
    """
    Helper function for get_max_city and get_min_city
    :param state_name: 
    :param city_name: 
    :param max_min: string, 'max' or 'min'
    :return: 
    """
    state_name = state_name.upper()
    city_name = city_name.upper()

    query_order = ''
    if max_min == 'max':
        query_order = 'DESC'
    elif max_min == 'min':
        query_order = 'ASC'

    query = ("SELECT a.{2} FROM weather a WHERE a.stn_id IN "
             "(SELECT b.stn_id FROM stations b WHERE b.name LIKE '%{1}%' "
             "AND b.state = '{0}') ORDER BY {2} {3} LIMIT 1").format(state_name,
                                                                     city_name,
                                                                     max_min,
                                                                     query_order)
    row = _fetch_all_rows_for_query(query)

    if len(row) == 0:
        return None

    assert len(row) == 1
    min_temp = {'State': state_name, 'City': city_name,
                max_min.capitalize() + ' Temp': row[0]}
    return min_temp


def get_mean_city(state_name, city_name):
    """
    gets mean temp for city in 2016
    """
    state_name = state_name.upper()
    city_name = city_name.upper()
    query = ("SELECT a.temp FROM weather a WHERE a.stn_id IN "
             "(SELECT b.stn_id FROM stations b WHERE b.name LIKE '%{1}%' "
             "AND b.state = '{0}')").format(state_name, city_name)
    rows = _fetch_all_rows_for_query(query)

    if len(rows) == 0:
        return None

    mean_list = []
    for row in rows:
        mean_list.append(row[0])

    mean = round(sum(mean_list) / len(mean_list), 1)
    mean_city_dict = {'State': state_name, 'City': city_name, 'Mean Temp': mean}
    return mean_city_dict


def get_rainy_days_city(state_name, city_name):
    """
    gets rain index in city
    """
    return get_rainy_snowy_days_city(state_name, city_name, 'rain')


def get_snowy_days_city(state_name, city_name):
    """
    gets snow index in city
    """
    return get_rainy_snowy_days_city(state_name, city_name, 'snow')


def get_rainy_snowy_days_city(state_name, city_name, rain_snow):  # Combine with state version?
    """
    Helper function for get_snowy_days and get_rainy_days
    :param city_name: 
    :param state_name: 
    :param rain_snow: string, 'rain' or 'snow'
    :return: 
    """
    state_name = state_name.upper()
    city_name = city_name.upper()

    column = ''
    if rain_snow == 'snow':
        column = 'snow_ice_pellets'
    elif rain_snow == 'rain':
        column = 'rain_drizzle'

    query = ("SELECT a.{2} FROM weather a WHERE a.stn_id IN "
             "(SELECT b.stn_id FROM stations b WHERE b.name LIKE '%{1}%' "
             "AND b.state = '{0}')").format(state_name, city_name, column)
    rows = _fetch_all_rows_for_query(query)

    if len(rows) == 0:
        return None

    prcp_list = []
    for row in rows:
        if len(row) == 0:
            return None
        elif row[0] != '0':
            prcp_list.append(row[0])

    prcp_days = round(
        len(prcp_list) / len(get_stations_by_city(state_name, city_name)), 0)
    prcp_dict = {'State': state_name, 'City': city_name,
                 rain_snow.capitalize() + ' Index': prcp_days}
    return prcp_dict


def get_cities(temp1, temp2):
    """
    returns list of cities whose mean yearly temperature fits the range 
    input by user
    """
    temp1 = float(temp1)
    temp2 = float(temp2)

    city_list = get_city_info()
    final_city_list = []
    for dictionary in city_list:
        city = dictionary.get('City')
        query = ("SELECT a.temp FROM weather a WHERE a.stn_id "
                 "IN (select b.stn_id from stations b where b.name "
                 "LIKE '%{}%')").format(city)
        mean_list = []

        for row in _fetch_all_rows_for_query(query):
            mean_list.append(row[0])

        if len(mean_list) != 0:
            mean = float(round((sum(mean_list) / len(mean_list)), 1))

            if temp1 <= mean <= temp2:
                city_dict = {'State': dictionary.get('State'),
                             'City': city,
                             'Mean Temp': mean}
                final_city_list.append(city_dict)

    return final_city_list


def get_cities_days(temp1, temp2):
    """
    returns list of dictionaries whose values contain number of days where 
    mean temp fits user range
    """
    temp1 = float(temp1)
    temp2 = float(temp2)

    final_city_list = []
    city_list = get_city_info()
    for dictionary in city_list:
        city = dictionary.get('City')
        state = dictionary.get('State')
        query = ("SELECT a.temp FROM weather a WHERE a.stn_id "
                 "IN (select b.stn_id from stations b where b.name "
                 "LIKE '%{0}%' AND b.state = '{1}')").format(city, state)

        mean_list = []
        for row in _fetch_all_rows_for_query(query):
            if temp1 <= row[0] <= temp2:
                mean_list.append(row[0])

        if (len(mean_list) != 0) and (
            len(get_stations_by_city(state, city)) != 0):
            days = int(len(mean_list) / len(get_stations_by_city(state, city)))
            city_dict = {'State': state, 'City': city, 'Days': days}
            final_city_list.append(city_dict)

    final_city_list = sort_by_dictionary(final_city_list)
    return final_city_list


def sort_by_dictionary(list_of_dictionaries):  # Only used once, do we need separate function?
    """
    Parameter: list of dictionaries
    Returns: sorted list based on dictionary value
    """
    sorted_list = sorted(list_of_dictionaries,
                         key=itemgetter('Days'),
                         reverse=True)
    return sorted_list


def get_city_info():
    """
    queries cities table and returns list of dictionaries with keys: 
    city and state
    """
    query = '''SELECT city, state FROM cities'''
    city_list = []
    for row in _fetch_all_rows_for_query(query):
        city_dict = {'City': row[0].upper(), 'State': row[1]}
        city_list.append(city_dict)

    return city_list


@app.route('/<state_name>')
def get_all_state(state_name):
    """
    returns json dump with max,min,mean,rain index, and snow index for state
    """

    main_list = [get_max(state_name),
                 get_min(state_name),
                 get_mean(state_name),
                 get_rainy_days(state_name),
                 get_snowy_days(state_name)]

    return json.dumps(main_list)


@app.route('/<state_name>/<city_name>')
def get_all_city(state_name, city_name):
    """
    returns json dump with max,min,mean,rain index, and snow index for state
    """
    city_list = [get_max_city(state_name, city_name),
                 get_min_city(state_name, city_name),
                 get_mean_city(state_name, city_name),
                 get_rainy_days_city(state_name, city_name),
                 get_snowy_days_city(state_name, city_name)]

    return json.dumps(city_list)


@app.route('/stations/<state_name>')  # We never use this?
def get_num_stations(state_name):
    """
    returns json dump with number of stations in state
    """
    return json.dumps(len(get_stations_by_state(state_name)))


@app.route('/compare/<state_name1>/<state_name2>')  # Changed from '/compare/state/?$state_name1=<state_name1>$state_name2=<state_name2>'
def compare_states(state_name1, state_name2):
    """
    returns json dump with information for both states in query
    """
    compare = [get_max(state_name1), get_max(state_name2),
               get_min(state_name1), get_min(state_name2),
               get_mean(state_name1), get_mean(state_name2),
               get_rainy_days(state_name1), get_rainy_days(state_name2),
               get_snowy_days(state_name1), get_snowy_days(state_name2)]

    return json.dumps(compare)


@app.route('/compare/<state_name1>/<city_name1>/<state_name2>/<city_name2>')  # Changed from '/compare/city/?$state_name1=<state_name1>$city_name1=<city_name1>$state_name2=<state_name2>$city_name2=<city_name2>'
def compare_cities(state_name1, city_name1, state_name2, city_name2):
    """
    returns json dump with information for both cities in query
    """
    city_compare = [get_max_city(state_name1, city_name1),
                    get_max_city(state_name2, city_name2),
                    get_min_city(state_name1, city_name1),
                    get_min_city(state_name2, city_name2),
                    get_mean_city(state_name1, city_name1),
                    get_mean_city(state_name2, city_name2),
                    get_rainy_days_city(state_name1, city_name1),
                    get_rainy_days_city(state_name2, city_name2),
                    get_snowy_days_city(state_name1, city_name1),
                    get_snowy_days_city(state_name2, city_name2)]

    return json.dumps(city_compare)


@app.route('/range/state/<low>/<high>/<num_states>')  # CHANGED from '/range/state/?$low=<low>$high=<high>&limit=<num_states>'
def find_states(low, high, num_states=0):
    """
    returns json dump with information on mean temperature of states that 
    fit user input range
    """
    low = float(low)
    high = float(high)
    state_mean_temp_list = [{"AL": 66.6}, {"AK": 37.8}, {"AZ": 66.5},
                            {"AR": 62.7}, {"CA": 61.7}, {"CO": 47.5},
                            {"CT": 53.2}, {"DC": 59.7}, {"DE": 57.5},
                            {"FL": 72.8}, {"GA": 66.2}, {"HI": 75.1},
                            {"ID": 47.2}, {"IL": 54.7}, {"IN": 54.0},
                            {"IA": 51.3}, {"KS": 57.7}, {"KY": 58.0},
                            {"LA": 70.5}, {"ME": 46.2}, {"MD": 58.0},
                            {"MA": 52.1}, {"MI": 48.3}, {"MN": 45.9},
                            {"MS": 66.5}, {"MO": 58.7}, {"MT": 46.0},
                            {"NE": 51.9}, {"NV": 58.4}, {"NH": 47.2},
                            {"NJ": 56.6}, {"NM": 57.3}, {"NY": 51.6},
                            {"NC": 62.0}, {"ND": 43.6}, {"OH": 53.6},
                            {"OK": 62.3}, {"OR": 52.5}, {"PA": 53.3},
                            {"RI": 53.1}, {"SC": 64.7}, {"SD": 50.0},
                            {"TN": 61.4}, {"TX": 69.1}, {"UT": 52.4},
                            {"VT": 46.6}, {"VA": 58.9}, {"WA": 52.4},
                            {"WV": 54.7}, {"WI": 47.4}, {"WY": 45.1}]
    temp_range = []
    for state in state_mean_temp_list:
        for key in state:
            if low <= state[key] <= high:
                temp_range.append({'State': key, 'Mean Temp': state[key]})

    num_states = int(num_states)
    if num_states == 0:
        return json.dumps(temp_range)
    else:
        return json.dumps(temp_range[:num_states])


@app.route('/range/city/<low>/<high>/<num_cities>')  # Changed from '/range/city/?$low=<low>$high=<high>&limit=<num_cities>'
def find_cities(low, high, num_cities=5):
    """
    returns json dump with cities whose mean temperature for 2016 fits 
    range set by user
    """
    low = float(low)
    high = float(high)

    num_cities = int(num_cities)
    return json.dumps(get_cities(low, high)[:num_cities])


@app.route('/range/city/days/<low>/<high>/<num_cities>')  # Changed from '/range/city/?$low=<low>$high=<high>&limit=<num_cities>&type=days'
def get_days(low, high, num_cities=5):
    """
    returns json dump with number of days where mean temp meets temp range.
    List is in descending order and capped at 10 cities
    """
    num_cities = int(num_cities)
    return json.dumps(get_cities_days(low, high)[:num_cities])


@app.after_request
def set_headers(response):
    response.headers['Access-Control-Allow-Origin'] = '*'
    return response


if __name__ == '__main__':
    app.run(host='thacker.mathcs.carleton.edu', port=5136, debug=True)
