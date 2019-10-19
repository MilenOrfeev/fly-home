import requests

def get_cities():
    api_key = 'skyscanner-guts2019'
    headers = {}
    headers["api-key"] = api_key

    response = requests.get('https://www.skyscanner.net/g/chiron/api/v1/places/geo/v1.0', headers=headers)
    geo_data_json = response.json()

    city_names = []
    for continent in geo_data_json['Continents']:  # continent
        for country in continent['Countries']:
            for city in country['Cities']:
                city_names.append(city['Name'])

    return city_names
