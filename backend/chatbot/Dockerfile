FROM python:3.10

WORKDIR /usr/src

COPY requirements.txt .
RUN pip install -r requirements.txt

COPY . .

EXPOSE 8000

CMD [ "uvicorn", "search:app" ]