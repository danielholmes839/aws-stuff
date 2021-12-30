import os

from fastapi import FastAPI
from pydantic import BaseSettings
from mangum import Mangum


class Settings(BaseSettings):
    counter: int = 0


settings = Settings()
app = FastAPI()


@app.get("/")
def view():
    """View counter"""
    return {"message": "It's working!!!", "count": settings.counter}


@app.get("/increment")
@app.post("/increment")
def increment():
    """Increment counter"""
    settings.counter += 1
    return {"message": "It's working!!!", "count": settings.counter}


handler = Mangum(app)
