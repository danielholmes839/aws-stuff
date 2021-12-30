import requests


for i in range(10):
    requests.post("http://localhost:8000/increment")
