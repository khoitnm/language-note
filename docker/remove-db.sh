#!/bin/bash
export HOST_IP=$(ip a | sed -En 's/127.0.0.1//;s/172.*.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p')
export MONGO_HOST=${HOST_IP}
export NEO4J_HOST=${HOST_IP}
export LANGUAGE_NOTE_SERVER_ENDPOINT=http://${HOST_IP}:8080/language-note-server
echo "Host IP ${HOST_IP}"
echo "MONGO_HOST ${MONGO_HOST}"
echo "NEO4J_HOST ${NEO4J_HOST}"
echo "LANGUAGE_NOTE_SERVER_ENDPOINT ${LANGUAGE_NOTE_SERVER_ENDPOINT}"

docker-compose -f docker-compose-mongodb.yml -f docker-compose-neo4j.yml down
