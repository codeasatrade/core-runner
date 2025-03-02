# refer docker-compose.yml for the image name
# docker rmi java-local-java-runner 

cd javaExecutionEngine

mvn clean package

cd ..

docker compose down

docker compose build 

docker compose up