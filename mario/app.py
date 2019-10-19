import re

from flask import Flask
from flask import request
from bs4 import BeautifulSoup
from cities import get_cities

app = Flask(__name__)
app.config['cities'] = get_cities()


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
    text = text + " Varna."
    for city in app.config['cities']:
        #if text.find(" " + city + " ") != -1:
        match =  re.compile(r'\b({0})\b'.format(city)).search(text)

        if match is not None:
            found_city = True
            print("Found city {}".format(city))
            print(text)
            break

    if not found_city:
        print("Didn't find any cities")
    return json

if __name__ == '__main__':
    app.run(debug=True)
