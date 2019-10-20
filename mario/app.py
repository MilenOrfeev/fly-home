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
    # soup.find_all(re.compile('^h[1-6]$')
    # kill all script and style elements
    for script in soup(["script", "style"]):
        script.extract()  # rip it out

    # get text
    text = soup.get_text()
    # headers = soup.find_all(re.compile('^h[1-6]$')
    text = text.replace("\n", " ")
    print (type(text))

    # Check for a city in DOM
    found_city = False
    text = text + "Madrid"
    for city in app.config['cities']:
        match = re.compile(r'\b({0})\b'.format(city)).search(text)

        if match is not None:
            found_city = True
            print("Found city {}".format(city))
            yatas = app.config['yatas']
            # for header in headers:

            return find_best_route(yatas[location], yatas[city])

    print("Didn't find any cities")
    response = jsonify({'status': "not found"})
    return response

if __name__ == '__main__':
    app.run(debug=True)
