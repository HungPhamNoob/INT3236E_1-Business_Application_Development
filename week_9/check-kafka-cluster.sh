#!/bin/bash

echo "🔍 Checking Kafka Cluster Status..."

echo "📊 Listing Kafka Brokers:"
docker exec kafka1 kafka-broker-api-versions.sh --bootstrap-server kafka1:29092

echo ""
echo "🔧 Creating test topic if not exists:"
docker exec kafka1 kafka-topics.sh --bootstrap-server kafka1:29092 --create --topic user-topic --partitions 3 --replication-factor 3 --if-not-exists

echo ""
echo "📋 Detailed topic description:"
docker exec kafka1 kafka-topics.sh --bootstrap-server kafka1:29092 --describe --topic user-topic

echo ""
echo "🏆 Checking which broker is leader for each partition:"
docker exec kafka1 kafka-topics.sh --bootstrap-server kafka1:29092 --describe | grep -E "Topic: user-topic|Leader:"