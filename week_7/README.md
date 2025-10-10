# CI/CD 

## Workflows
```
Repo (GitHub)
   ↓
GitHub Actions (Build & Push Docker image)
   ↓
Docker Hub (Store image)
   ↓
ArgoCD (Monitor repo & auto deploy)
   ↓
Kubernetes (Run Pod + Service)
   ↓
Browser (Access app)
```
## Run
Open the **Terminal** and run:
```bash
kubectl create namespace argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
```
<br>

Argo Login
<br>
Username: `admin`
<br>
Get password:
```
kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | ForEach-Object { [System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String($_)) }
```

Open ArgoCD
```
http://localhost:8080
```
<br>

Deploy
```
kubectl get svc
kubectl get pods
kubectl port-forward svc/k8s-hihi 8081:80
http://localhost:8081/actuator/health
```
## Preview
<p align="left">
  <b>CI - Github Action</b><br>
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_7/k8s-hihi/result/ci-github-action.png" alt="Login"/>
</p>
<br>
<p align="left">
  <b>Docker Hub</b><br>
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_7/k8s-hihi/result/docker-hub.png" alt="Admin"/>
</p>
<br>
<p align="left">
  <b>Docker Container</b><br>
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_7/k8s-hihi/result/docker-pod.png" alt="Hello"/>
</p>
<br>
<p align="left">
  <b>CD - ArgoCD</b><br>
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_7/k8s-hihi/result/cd-argocd.png" alt="Home"/>
</p>
<p align="left">
  <b>Web (localhost:8081)</b><br>
  <img src="https://github.com/HungPhamNoob/INT3236E_1-Enterprise_Application_Development/blob/main/week_7/k8s-hihi/result/localhost.png" alt="Home"/>
</p>
