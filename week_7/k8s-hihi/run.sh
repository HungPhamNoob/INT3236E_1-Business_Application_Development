#!/bin/bash

# Đảm bảo chạy trong đúng thư mục chứa script
cd "$(dirname "$0")"

echo "[1/5] 🚧 Building Spring Boot application..."
./mvnw clean package -DskipTests
if [ $? -ne 0 ]; then
    echo "❌ Maven build failed!"
    exit 1
fi

echo "[2/5] 🐳 Building Docker image..."
docker build -t my-spring-app .
if [ $? -ne 0 ]; then
    echo "❌ Docker build failed!"
    exit 1
fi

echo "[3/5] 🚀 Deploying to Kubernetes..."
kubectl apply -f k8s/
if [ $? -ne 0 ]; then
    echo "❌ Kubernetes deployment failed!"
    exit 1
fi

echo "[4/5] 🔍 Checking pod status..."
kubectl get pods -o wide

echo "[5/5] 🌐 Starting port-forwarding..."
echo ""
echo "🚀 APPLICATION IS READY!"
echo "📡 Access your application at: http://localhost:8080"
echo "💡 Press Ctrl+C to stop port-forward"
echo ""

# kubectl port-forward svc/gs-spring-boot-k8s 8080:80
