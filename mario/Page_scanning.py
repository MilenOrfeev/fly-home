import re

from flask import Flask
from flask import request
from bs4 import BeautifulSoup
from cities import get_cities
from MIMtalker import find_best_route
from flask import jsonify
from collections import Counter

app = Flask(__name__)
app.config['cities'], app.config['yatas'] = get_cities()

@app.route('/mario/flights', methods=['POST'])
def get_flight(self):
    json = request.get_json()
    dom = json['dom']
    location = json['location']

    soup = BeautifulSoup(dom, features="html.parser")
    # get the head of the HTML
    # get the title of the HTML
    head = soup.head.string
    title = soup.title.string
    # kill all script and style elements
    for script in soup(["script", "style"]):
        script.extract()  # rip it out

    # get text
    text = soup.get_text()
    # headers = soup.find_all(re.compile('^h[1-6]$')
    text = text.replace("\n", " ")

    # Check for a city in DOM
    found_city = False
    # Check for most common city neither in the header nor title
    text_city = False
    text_cities_list = []
    for city in app.config['cities']:
        match = re.compile(r'\b({0})\b'.format(city)).search(text)
        if match is not None:
            if (city in head):
                print("Found city {}".format(city))
                yatas = app.config['yatas']
            elif (city not in head and city in title):
                print("Found city {}".format(city))
                yatas = app.config['yatas']
            else:
                text_city = True
                text_cities_list.append(city)
                print("Found city {}".format(city))
        return find_best_route(yatas[location], yatas[city])

    print("Didn't find any cities")
    response = jsonify({'status': "not found"})
    return response

if __name__ == '__main__':
    app.run(debug=True)









#######################   LANDMARK_CODE ################################

# dict with the European capitals landmarks
# landmark = {'Sofia': 'Alexander Nevsky Cathedral',
#             'Paris': 'Eiffel Tower',
#             'Lisbon ': 'Belem Tower',
#             'Madrid ': 'Alcala Gate',
#             'Andorra ': 'Sant Esteve',
#             'Monaco ': 'Monte Carlo Casino',
#             'San Marino': 'Palazzo Pubblico',
#             'Rome ': ['Colosseum', 'Saint Peter\'s Basilica'],
#             'Valletta': 'Co-Cathedral',
#             'Bern ': 'Zytglogge',
#             'Liechtenstein ': 'Castle',
#             'Vienna ': 'St. Stephen\'s Cathedral',
#             'Berlin ': 'Brandenburg Gate',
#             'Amsterdam ': 'De Gooyer Windmill',
#             'Brussels ': 'Town Hall',
#             'Luxembourg ': 'Adolphe Bridge',
#             'Dublin ': 'Norman Tower',
#             'London ': ['Big Ben', 'London eye'],
#             'Copenhagen ': 'Frederik\'s Church',
#             'Oslo ': 'Radhous',
#             'Stockholm ': 'City Hall',
#             'Helsinki ': 'Helsinki Cathedral',
#             'Reykjavík ': 'Hallgrímskirkja',
#             'Tallinn ': 'Kiek in de Kök',
#             'Riga  ': 'Dome cathedral',
#             'Vilnius ': 'Cathedral Tower',
#             'Minsk ': 'City Gate Towers',
#             'Warsaw  ': 'Royal Castle',
#             'Prague ': 'Charles Bridge',
#             'Bratislava': 'Castle',
#             'Budapest': 'Parliament',
#             'Zagreb': 'Cathedral',
#             'Belgrade': 'Temple Saint Sava',
#             'Sarajevo': 'Sebilj',
#             'Podgorica': 'Sahat Kula',
#             'Moscow': 'Saint Basil\'s Cathedral',
#             'Kiev ': 'Sofia Cathedral',
#             'Chisinau': 'Cathedral Bell Tower',
#             'Bucharest': 'Athenaeum',
#             'Skopje': 'Stone Bridge',
#             'Tirana': 'Clock Tower',
#             'Athens': 'Parthenon',
#             'Nicosia': 'Buyuk Han',
#             'Pristina': 'Clock Tower'}
# landmark = app.confing['landmark']

# def get_landmarks(self,text_city, ):
# #     if(text_city):
# #         # find the most common word in the text
# #         cnt_ = Counter(text_cities_list)
# #         most_common_citie = cnt_.most_common(1)
# #     for eu_landmark in landmark.values():
# #         match_landmark = re.search(text)
# #         if(match_landmark in eu_landmark):
# #             return landmark.keys()