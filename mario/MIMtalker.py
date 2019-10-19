import json

import requests
from flask import jsonify


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
    response = requests.post('http://localhost:8080/api/v1/flight', json=flightRequest)

    content = response.content
    if not content:
        return jsonify({'status': "not found"})

    return response.content
