#!/bin/bash
export HOST_IP=$(ip a | sed -En 's/127.0.0.1//;s/172.*.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p')
echo "Host IP ${HOST_IP}"
docker-compose up -d
