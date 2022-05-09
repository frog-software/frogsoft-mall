#!/bin/bash
bash ./stop.sh
docker-compose --file ../docker-compose.yml rm -sf 
