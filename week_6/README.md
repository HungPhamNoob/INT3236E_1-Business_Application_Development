# Docker & K8s

## Run
Open the project in **IntelliJ IDEA**, then open the **Terminal** and run:
```bash
cd k8s-hihi
./run.sh
```
```
kubectl port-forward svc/gs-spring-boot-k8s 8080:80
```
```
http://localhost:8080/actuator/health
```
## Preview
<p align="left">
  <b>Build</b><br>
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_6/k8s-hihi/result/docker_build.png" alt="Login"/>
</p>
<br>
<p align="left">
  <b>Check image</b><br>
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_6/k8s-hihi/result/docker_image.png" alt="Admin"/>
</p>
<br>
<p align="left">
  <b>Container</b><br>
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_6/k8s-hihi/result/k8s_run.png" alt="Hello"/>
</p>
<br>
<p align="left">
  <b>Deploy</b><br>
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_6/k8s-hihi/result/k8s_deploy.png" alt="Home"/>
</p>
<p align="left">
  <b>Web</b><br>
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_6/k8s-hihi/result/localhost.png" alt="Home"/>
</p>
