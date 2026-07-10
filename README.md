<img width="1712" height="755" alt="Screenshot 2026-07-10 172801" src="https://github.com/user-attachments/assets/e9994679-59be-4563-abe8-548c66f85c9a" /># Déploiement Kubernetes - Projet Final

## Présentation du projet

Application web full-stack déployée sur Kubernetes avec Minikube, composée d'un backend Spring Boot et d'un frontend Angular. L'application communique via des services Kubernetes internes, sans aucune IP codée en dur.

## Architecture

```
Navigateur
      │
http://IP_MINIKUBE:30080
      │
      ▼
NodePort (30080)
      │
      ▼
Frontend Service (NodePort)
      │
      ▼
Frontend Pods (Angular)
      │
      ▼
backend-service (ClusterIP)
      │
      ▼
Backend Service (ClusterIP)
      │
      ▼
Backend Pods (Spring Boot)
```

## Technologies

- **Backend**: Spring Boot
- **Frontend**: Angular
- **Containerisation**: Docker
- **Orchestration**: Kubernetes
- **Environnement local**: Minikube

## Images Docker

- `mbayeuser/backend-k8s:1.0` - Backend Spring Boot
- `mbayeuser/frontend-k8s:1.0` - Frontend Angular

## Construction des images Docker

### Backend

```bash
cd backend_java
docker build -t mbayeuser/backend-k8s:1.0 .
docker push mbayeuser/backend-k8s:1.0
```

### Frontend

```bash
cd frontend-angular
docker build -t mbayeuser/frontend-k8s:1.0 .
docker push mbayeuser/frontend-k8s:1.0
```

**Note** : Les images sont déjà publiées sur Docker Hub, vous pouvez les utiliser directement sans rebuild.

## Déploiement Kubernetes

### Prérequis

- Minikube installé et démarré
- kubectl configuré
- Docker installé

### Commandes de déploiement

```bash
# Appliquer tous les manifests Kubernetes
kubectl apply -f k8s/

# Vérifier que tous les objets sont créés
kubectl get all -n projet-final
```

### Structure des manifests

```
k8s/
├── 00-namespace.yaml          # Namespace projet-final
├── configmap.yaml             # ConfigMap pour BACKEND_URL
├── 01-backend-deployment.yaml # Deployment backend (2 réplicas)
├── 02-backend-service.yaml    # Service ClusterIP backend
├── 03-frontend-deployment.yaml # Deployment frontend (2 réplicas)
└── 04-frontend-service.yaml    # Service NodePort frontend
```

## Vérifications

### Vérifier les pods

```bash
kubectl get pods -n projet-final
```

**Attendu**: 2 pods backend et 2 pods frontend avec status `Running`

```
NAME                                   READY   STATUS    RESTARTS   AGE
backend-deployment-xxxxx               1/1     Running   0          Xm
backend-deployment-yyyyy               1/1     Running   0          Xm
frontend-deployment-xxxxx              1/1     Running   0          Xm
frontend-deployment-yyyyy              1/1     Running   0          Xm
```

### Vérifier les deployments

```bash
kubectl get deployments -n projet-final
```

**Attendu**: 2 deployments avec 2 réplicas chacun

```
NAME                   READY   UP-TO-DATE   AVAILABLE   AGE
backend-deployment     2/2     2            2           Xm
frontend-deployment    2/2     2            2           Xm
```

### Vérifier les services

```bash
kubectl get svc -n projet-final
```

**Attendu**: 2 services (ClusterIP pour backend, NodePort pour frontend)

```
NAME               TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)        AGE
backend-service    ClusterIP   10.x.x.x         <none>        8080/TCP       Xm
frontend-service   NodePort    10.x.x.x         <none>        80:30080/TCP   Xm
```

### Vérifier les ConfigMaps

```bash
kubectl get configmap -n projet-final
```

**Attendu**: ConfigMap `frontend-config` avec `BACKEND_URL`

```
NAME               DATA   AGE
frontend-config    1      Xm
```

### Décrire un pod pour plus de détails

```bash
kubectl describe pod <nom-du-pod> -n projet-final
```

### Vérifier les logs

**Frontend**:
```bash
kubectl logs -l app=frontend -n projet-final
```

**Backend**:
```bash
kubectl logs -l app=backend -n projet-final
```

## Points de vérification importants

- ✅ Les 2 pods backend sont `Running`
- ✅ Les 2 pods frontend sont `Running`
- ✅ Les Services sont créés (ClusterIP pour backend, NodePort pour frontend)
- ✅ Le NodePort fonctionne (port 30080)
- ✅ Le frontend appelle correctement le backend via le Service `backend-service`
- ✅ Aucune IP de pod n'est utilisée (communication via services uniquement)
- ✅ La ConfigMap injecte correctement `BACKEND_URL` dans les pods frontend

## Accès à l'application

### Méthode 1 - Via minikube service

```bash
minikube service frontend-service -n projet-final
```

Cette commande ouvre automatiquement le navigateur.

### Méthode 2 - Via IP Minikube

```bash
# Récupérer l'IP de Minikube
minikube ip
```

Puis ouvrir dans le navigateur :
```
http://<IP_MINIKUBE>:30080
```

Exemple :
```
http://192.168.49.2:30080
```

## Test de l'application

Une fois l'application accessible dans le navigateur, vous devriez voir :

- L'interface Angular
- Le message **"Bonjour Kubernetes"** affiché
- Ce message confirme que le frontend communique avec le backend via l'API `/api/hello`

## Captures d'écran

### Images Docker

<img width="1879" height="966" alt="Screenshot 2026-07-10 171215" src="https://github.com/user-attachments/assets/d1773875-674f-42d1-9d48-dde9386cb897" />

<img width="971" height="207" alt="Screenshot 2026-07-10 171724" src="https://github.com/user-attachments/assets/bb054af3-a526-4c22-92fb-91e595d0c8d9" />


### Conteneurs Docker
<img width="1007" height="299" alt="Screenshot 2026-07-10 172015" src="https://github.com/user-attachments/assets/98c5f904-53ef-4719-8785-8ae9c3d5531b" />


### Pods Kubernetes
<img width="874" height="216" alt="Screenshot 2026-07-10 172141" src="https://github.com/user-attachments/assets/73d83d73-4dda-4c44-89fb-340d4fc42406" />

### Services Kubernetes
<img width="895" height="126" alt="Screenshot 2026-07-10 172300" src="https://github.com/user-attachments/assets/c18e76d0-8852-434a-8162-d6184463cc3d" />

### Application dans le navigateur
<img width="1674" height="609" alt="Screenshot 2026-07-10 173128" src="https://github.com/user-attachments/assets/4d55faca-5768-43cb-8b9d-2abe61141461" />
<img width="1712" height="755" alt="Screenshot 2026-07-10 172801" src="https://github.com/user-attachments/assets/cd6563f8-6f80-42cc-ab7c-2818d33ae9b9" />



## Bonnes pratiques appliquées

- ✅ Fichiers YAML bien indentés
- ✅ Labels cohérents (`app` et `tier`)
- ✅ Selecteurs corrects entre Services et Deployments
- ✅ Utilisation de ConfigMap pour la configuration
- ✅ Aucune IP codée en dur
- ✅ Backend accessible uniquement via Service ClusterIP
- ✅ Frontend exposé uniquement via Service NodePort
- ✅ Health probes (readiness et liveness)
- ✅ Resource requests et limits définis
- ✅ 2 réplicas pour haute disponibilité

## Nettoyage

Pour supprimer tous les ressources :

```bash
kubectl delete -f k8s/
```

Ou supprimer le namespace entier :

```bash
kubectl delete namespace projet-final
```
