from flask import Flask, jsonify, request

app = Flask(__name__)

@app.route('/', methods=['POST'])
def print_sms_data():
    print(request.get_json())
    return jsonify({'data': '200'})

app.run(debug=True, host='0.0.0.0', port=9090)
