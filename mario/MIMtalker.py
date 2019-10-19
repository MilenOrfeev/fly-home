import json

import requests


def find_best_route(origin, destination):
    flightRequest = {}
    flightRequest['originPlace'] = origin
    flightRequest['destinationPlace'] = destination
    flightRequest['currency'] = "USD"
    flightRequest['locale'] = "en-EN"
    flightRequest['outboundPartialDate'] = "anytime"
    flightRequest['inboundPartialDate'] = "anytime"
    flightRequest['country'] = "UK"

    print(json.dumps(flightRequest, indent=2))
    response = requests.post('http://localhost:8080/api/v1/flight', json=flightRequest)

    return response.content
