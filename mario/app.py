from flask import Flask
from flask import request

app = Flask(__name__)


@app.route('/')
def hello_world():
    return 'Hello World!'

@app.route('/mario/flights', methods=['POST'])
def get_flight():
    json = request.get_json()
    return json

if __name__ == '__main__':
    app.run()
