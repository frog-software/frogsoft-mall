#!/bin/bash

# cd into where this script lives
SCRIPT_DIR="$(dirname "${BASH_SOURCE}")"
cd "${SCRIPT_DIR}" || exit 1

docker compose --file ../docker-compose.yml up -d mysql || exit 1
docker compose --file ../docker-compose.yml up -d opengauss  || exit 1
docker compose --file ../docker-compose.yml up -d nacos  || exit 1

bash ./start.sh || exit 1