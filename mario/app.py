import re

from flask import Flask
from flask import request
from bs4 import BeautifulSoup
from cities import get_cities
from MIMtalker import find_best_route
from flask import jsonify

app = Flask(__name__)
app.config['cities'], app.config['yatas'] = get_cities()


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/mario/flights', methods=['POST'])
def get_flight():
    json = request.get_json()
    dom = json['dom']
    location = json['location']

    soup = BeautifulSoup(dom, features="html.parser")

    # kill all script and style elements
    for script in soup(["script", "style"]):
        script.extract()  # rip it out

    # get text
    text = soup.get_text()
    text = text.replace("\n", " ")

    # Check for a city in DOM
    found_city = False
    for city in app.config['cities']:
        match = re.compile(r'\b({0})\b'.format(city)).search(text)

        if match is not None:
            found_city = True
            print("Found city {}".format(city))
            yatas = app.config['yatas']
            return find_best_route(yatas[location], yatas[city])

    print("Didn't find any cities")
    response = jsonify({'status': 404, 'error': 'not found',
                        'message': ' plane flights'})
    response.status_code = 404
    return response

if __name__ == '__main__':
    app.run(debug=True)
