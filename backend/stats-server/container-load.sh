#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail

if [ "$( docker container inspect -f '{{.State.Running}}' namenode )" != "true" ]; then
    echo "You should run your namenode by executing 'docker compose up -d' and wait a few minutes"
    exit 1
fi

if [ ! -d dist ]; then
    echo "You should have a dist directory."
    echo "You can either run 'make' or download compiled binaries from GitHub and extract them to dist"
    exit 1
fi

cat <<EOF | tee dist/run.sh >/dev/null
#!/bin/bash
START_COMMAND_PID="./apiserver & echo \$! > ./pid &"
START_COMMAND_NOHUP="nohup bash -c '\${START_COMMAND_PID}' > start.out 2> start.err < /dev/null &"
eval "\${START_COMMAND_NOHUP}"
EOF
chmod +x dist/run.sh

docker cp dist namenode:/tmp
docker exec -it namenode /bin/bash -c 'cd /tmp/dist && ./apiserver'