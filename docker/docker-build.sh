docker build -t tnmk/language-note-server:latest -f ./language-note-server/Dockerfile ../language-note-server/target
docker build -t tnmk/language-note-client:latest -f ./language-note-client/Dockerfile ../language-note-client/target
