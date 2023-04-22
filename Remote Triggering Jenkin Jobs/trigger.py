import json
import os
import http.client
import base64

dir_path = os.path.dirname(os.path.realpath(__file__))

configFile = open(dir_path + "/config.json", "r")

configContent = configFile.read()

# convert content str into json dictionary
jsonConfigContent = json.loads(configContent)

username = jsonConfigContent['username']
token = jsonConfigContent['token']
jenkins_url = jsonConfigContent['jenkins_url']
job_name = jsonConfigContent['job_name']
job_token = jsonConfigContent['job_token']

conn = http.client.HTTPConnection(jenkins_url)

string_to_encode = username + ':' + token
encoded_bytes = base64.b64encode(string_to_encode.encode('utf-8'))
encoded_string = encoded_bytes.decode('utf-8')

headers = { 'Authorization': "Basic " + encoded_string }
payload = ""

conn.request("GET", '/job/' + job_name + '/build?token=' + job_token, payload, headers)

print(conn.getresponse().getcode())
