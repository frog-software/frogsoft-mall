#!/bin/bash

# cd into where this script lives
SCRIPT_DIR="$(dirname "${BASH_SOURCE}")"
cd "${SCRIPT_DIR}" || exit 1

bash ./stop.sh
docker compose --file ../docker-compose.yml rm -sf
