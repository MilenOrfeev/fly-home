import json
import operator
import re
from collections import Counter

import requests
from flask import Flask
from flask import request
from bs4 import BeautifulSoup
from cities import get_cities
from MIMtalker import find_best_route, findLive
from flask import jsonify

app = Flask(__name__)
app.config['cities'], app.config['yatas'] = get_cities()


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/mario/range', methods=['POST'])
def get_flight_in_range():
    jsonRequest = request.get_json()
    origin = jsonRequest['location']
    maxPrice = jsonRequest['pLimit']
    given_range = jsonRequest['period']

    flightRequest = {}
    flightRequest['originPlace'] = origin
    flightRequest['destinationPlace'] = "anywhere"
    flightRequest['currency'] = "GBP"
    flightRequest['locale'] = "en-EN"
    flightRequest['outboundPartialDate'] = "anytime"
    flightRequest['inboundPartialDate'] = "anytime"
    flightRequest['country'] = "UK"
    flightRequest['maxPrice'] = maxPrice
    flightRequest['range'] = given_range

    yatas = app.config['yatas']

    responseFlight = requests.post('http://localhost:8080/api/v1/manyFlights', json=flightRequest)
    print(responseFlight.content)
    flights = json.loads(responseFlight.content)
    for flight in flights:
        originFlight = origin
        if flight['destination'] not in yatas:
            flight['link'] = ""
            continue
        destinationFlight = yatas[flight['destination']]
        flight['link'] = findLive(originFlight, destinationFlight, flight['departureDate'], flight['returnDate'])

    return json.dumps(flights)


@app.route('/mario/flights', methods=['POST'])
def get_flight():
    json = request.get_json()
    dom = json['dom']
    location = json['location']

    soup = BeautifulSoup(dom, features="html.parser")
    # get the head of the HTML
    # get the title of the HTML
    title = soup.title.string
    # kill all script and style elements
    for script in soup(["script", "style"]):
        script.extract()  # rip it out

    # get text
    text = soup.get_text()
    # headers = soup.find_all(re.compile('^h[1-6]$')
    text = text.replace("\n", " ")

    # Check for a city in DOM
    found_city_in_title = False
    # Check for most common city neither in the header nor title
    text_cities = {}
    foundCity = None
    for city in app.config['cities']:
        compiled = re.compile(r'\b({0})\b'.format(city))
        if compiled.search(title) is not None:
            print("Found city {}".format(city))
            found_city_in_title = True
            foundCity = city
            break
        else:
            match = compiled.findall(text)
            if len(match) > 0:
                text_cities[city] = len(match)
                print("Found city {}".format(city))

    if not found_city_in_title and len(text_cities.keys()) > 0:
        foundCity = text_city(text_cities)
        print("Most dominant city on page:" + foundCity)

    if foundCity is None:
        print("Didn't find any cities")
        response = jsonify({'status': "not found"})
        return response

    yatas = app.config['yatas']
    return find_best_route(yatas[location], yatas[foundCity])


def text_city(text_cities):
    # find the most common word in the text
    maxV = 0
    bestK = None
    for k, v in text_cities.items():
        if v > maxV:
            maxV = v
            bestK = k

    return bestK


if __name__ == '__main__':
    app.run(debug=True)
