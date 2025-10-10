# Kafka


## 1. Run Kafka Cluster
```
docker-compose up -d
```
<p align="left">
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_9/result/1.png" alt="Login"/>
</p>
<br>

## 2. Container status
```
docker ps
```
<p align="left">
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_9/result/2.png" alt="Admin"/>
</p>
<br>

## 3. Run app
### Consumer
```
cd consumer-app
mvn clean spring-boot:run
```
## Producer
```
cd producer-app
mvn clean spring-boot:run
```

## 4. Test API
```
curl -X POST http://localhost:8081/api/users/random
```
<p align="left">
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_9/result/3.png" alt="Hello"/>
</p>
<br>

## 5. Run 3 clusters in Kafka
<p align="left">
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_9/result/4.png" alt="Home"/>
</p>
<br>
<p align="left">
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_9/result/5.png" alt="Home"/>
</p>

## 6. Stop 1 Kafka
<p align="left">
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_9/result/6.png" alt="Home"/>
</p>
<br>
<p align="left">
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_9/result/7.png" alt="Home"/>
</p>
