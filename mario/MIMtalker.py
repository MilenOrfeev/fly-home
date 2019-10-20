import json

import requests
import json

from flask import jsonify


def findLive(origin, destination, departureDate, returnDate):
    api_key = 'skyscanner-guts2019'
    headers = {"api-key": api_key}
    linkRequest = {}
    linkRequest['country'] = "UK"
    linkRequest['currency'] = "GBP"
    linkRequest['originPlace'] = origin
    linkRequest['destinationPlace'] = destination
    linkRequest["locationSchema"] = "iata"
    linkRequest['outboundDate'] = departureDate.split("T")[0]
    linkRequest['inboundDate'] = returnDate.split("T")[0]
    linkRequest['locale'] = "en-EN"
    linkRequest["adults"] = "1"

    response = requests.post('https://www.skyscanner.net/g/chiron/api/v1/flights/search/pricing/v1.0', headers=headers,
                             json=linkRequest)
    session = json.loads(response.content)
    url = "https://www.skyscanner.net/g/chiron/api/v1/flights/search/pricing/v1.0?session_id=" + session['session_id']
    data = json.loads(requests.get(url=url, headers=headers).content)
    minimum = 100000000
    link = "mate for 100m you can buy a plane"
    for itinerary in data['Itineraries']:
        for pricing_option in itinerary['PricingOptions']:
            if pricing_option['Price'] < minimum:
                minimum = pricing_option['Price']
                link = pricing_option['DeeplinkUrl']

    if minimum == 100000000:
        return None

    return link


def find_best_route(origin, destination):
    flightRequest = {}
    flightRequest['originPlace'] = origin
    flightRequest['destinationPlace'] = destination
    flightRequest['currency'] = "GBP"
    flightRequest['locale'] = "en-EN"
    flightRequest['outboundPartialDate'] = "anytime"
    flightRequest['inboundPartialDate'] = "anytime"
    flightRequest['country'] = "UK"

    print(json.dumps(flightRequest, indent=2))
    responseFlight = requests.post('http://localhost:8080/api/v1/flight', json=flightRequest)

    content = responseFlight.content
    if not content:
        return jsonify({'status': "not found"})

    api_key = 'skyscanner-guts2019'
    headers = {}
    headers["api-key"] = api_key
    jsonContent = json.loads(content)
    linkRequest = {}
    linkRequest['country'] = "UK"
    linkRequest['currency'] = "GBP"
    linkRequest['originPlace'] = origin
    linkRequest['destinationPlace'] = destination
    linkRequest["locationSchema"] = "iata"
    linkRequest['outboundDate'] = jsonContent["departureDate"].split("T")[0]
    linkRequest['inboundDate'] = jsonContent["returnDate"].split("T")[0]
    linkRequest['locale'] = "en-EN"
    linkRequest["adults"] = "1"

    response = requests.post('https://www.skyscanner.net/g/chiron/api/v1/flights/search/pricing/v1.0', headers=headers,
                             json=linkRequest)
    print(response.content)
    session = json.loads(response.content)
    url = "https://www.skyscanner.net/g/chiron/api/v1/flights/search/pricing/v1.0?session_id=" + session['session_id']
    data = json.loads(requests.get(url=url, headers=headers).content)
    minimum = 100000000
    link = "mate for 100m you can buy a plane"
    for itinerary in data['Itineraries']:
        for pricing_option in itinerary['PricingOptions']:
            if pricing_option['Price'] < minimum:
                minimum = pricing_option['Price']
                link = pricing_option['DeeplinkUrl']

    if minimum == 100000000:
        print("Didn't find any flights from live version")
        response = jsonify({'status': "not found"})
        return response

    print(link)

    finalResponse = json.loads(responseFlight.content)
    finalResponse['link'] = link
    finalResponse['livePrice'] = minimum

    return json.dumps(finalResponse)
