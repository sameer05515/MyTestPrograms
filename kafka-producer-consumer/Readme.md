# Pull Kafka image
docker pull confluentinc/cp-kafka

# Create Docker network
docker network create kafka-network

# Start Kafka container
docker run -d --network kafka-network --name kafka -p 9092:9092 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092 -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 confluentinc/cp-kafka

# Start ZooKeeper container
docker run -d --network kafka-network --name zookeeper -p 2181:2181 -e ZOOKEEPER_CLIENT_PORT=2181 confluentinc/cp-zookeeper

# Check Kafka container logs
docker logs kafka

-------------------------------------

# To Run docker-compose.yml

## Run Kafka and Zookeeper
docker compose up -d

## Create a topic
docker exec broker kafka-topics --bootstrap-server broker:9092 --create --topic quickstart

## Write messages to the topic
docker exec --interactive --tty broker kafka-console-producer --bootstrap-server broker:9092 --topic quickstart

## Read messages from the topic
docker exec --interactive --tty broker kafka-console-consumer --bootstrap-server broker:9092 --topic quickstart --from-beginning

## Stop the Kafka broker
docker compose down

Read more on https://developer.confluent.io/quickstart/kafka-docker/


-------------------------------