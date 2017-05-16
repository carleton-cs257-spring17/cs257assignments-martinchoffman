#!/usr/bin/env python3
'''
    books_api.py
    Jeff Ondich, 25 April 2016

    Simple Flask app used in the sample web app for
    CS 257, Spring 2016. This is the Flask app for the
    "books and authors" API and website. The API offers
    JSON access to the data, while the website (at
    route '/') offers end-user browsing of the data.
'''
import sys
import flask
import json
import psycopg2

app = flask.Flask(__name__, static_folder='Static', template_folder='Templates')

@app.route('/') 
def get_main_page():
    ''' This is the only route intended for human users '''
    return flask.render_template('Website.html')

@app.route('/state/')
def get_state_page():

    return flask.render_template('State.html')

@app.route('/city/')
def get_city_page():

    return flask.render_template('City.html')

@app.route('/compare/')
def get_compare_page():

    return flask.render_template('Compare.html')

@app.route('/find/')
def get_find_page():

    return flask.render_template('Find.html')

@app.route('/about/')
def get_about_page():

    return flask.render_template('About.html')


if __name__ == '__main__':
    if len(sys.argv) != 3:
        print('Usage: {0} host port'.format(sys.argv[0]), file=sys.stderr)
        exit()

    host = sys.argv[1]
    port = sys.argv[2]
    app.run(host=host, port=port)

