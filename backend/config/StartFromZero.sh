#!/bin/bash

docker-compose --file ../docker-compose.yml up -d mysql 
docker-compose --file ../docker-compose.yml up -d opengauss 
docker-compose --file ../docker-compose.yml up -d nacos 

bash ./start.sh