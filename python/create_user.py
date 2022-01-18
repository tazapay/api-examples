# Sample python program to call Tazapay sandbox API
​
import hashlib
import base64
import json
import requests
from datetime import datetime
import calendar
from random import *
import hmac
​
access_key = 'your-access-key'
secret_key = 'your-secret-key'
salt = 'Tif6r2ZqVn' # any salt input
​
http_method = 'POST' # upper case
base_url = 'https://api-sandbox.tazapay.com'
path = '/v1/user' # This can hold any value of the supported API.
​
# Current Unix time.
d = datetime.utcnow()
timestamp = calendar.timegm(d.utctimetuple())
​
# generate signature
to_sign = http_method + path + salt + str(timestamp) + access_key + secret_key
h = hmac.new(bytes(secret_key, 'utf-8'), bytes(to_sign, 'utf-8'), hashlib.sha256)
signature = base64.urlsafe_b64encode(str.encode(h.hexdigest()))
​
headers = {
    'accesskey': access_key,
    'signature': signature,
    'salt': salt,
    'timestamp': str(timestamp)
}
​
payload = {
    "email": "support@tazapay.com",
    "country": "Singapore",
    "ind_bus_type": "Business",
    "business_name":"business name"
}
​
response = requests.post(url=base_url+path, json=payload, headers=headers)
print(json.loads(response.content.decode('utf-8')))