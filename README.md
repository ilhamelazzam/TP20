# Microservices TP

Ce dépôt contient les quatre modules demandés dans la correction du TP :

1. **eureka-server** – serveur de découverte Spring Cloud Eureka (port `8761`).
2. **client-service** – API REST qui gère les clients (port `8081`).
3. **car-service** – API REST qui gère les voitures et compose les réponses avec les clients via la passerelle (port `8082`).
4. **gateway** – Spring Cloud Gateway qui route dynamiquement les appels vers les services enregistrés.

## Pré-requis

- Java 17
- Maven 3+
- MySQL (les bases `clientservicedb` et `carservicedb` sont créées automatiquement grâce à `createDatabaseIfNotExist=true` dans les URLs)
- Activer les services avec les ports décrits ci-dessus (assurez-vous qu'ils ne sont pas déjà occupés)

Les services utilisent l'utilisateur `root` sans mot de passe par défaut (modifiable dans les fichiers `application.yml` de chaque microservice).

## Lancer les services (ordre recommandé)

1. **Eureka Server**  
   ```bash
   cd eureka-server
   mvn spring-boot:run
   ```
   Vérifiez le dashboard : [http://localhost:8761](http://localhost:8761).

2. **Client Service**  
   ```bash
   cd ../
   mvn spring-boot:run
   ```
   L’application expose `/api/client`.

3. **Car Service**  
   ```bash
   cd car
   mvn spring-boot:run
   ```
   Elle s’enregistre auprès d’Eureka et utilise la passerelle pour enrichir les données clients.

4. **Gateway**  
   ```bash
   cd ../gateway
   mvn spring-boot:run
   ```
   Elle récupère automatiquement les routes des services enregistrés.

Chaque module peut être lancé indépendamment en faisant `mvn spring-boot:run` depuis son propre dossier (*eureka-server*, *car*, *gateway*). Le module `client-service` reste à la racine.

## Points de test manuels

### 1. Découverte
- Accéder à [http://localhost:8761](http://localhost:8761) pour vérifier que `SERVICE-CLIENT`, `SERVICE-CAR` et `Gateway` apparaissent (`UP`).

### 2. Endpoints REST via la Gateway
- Liste des clients :
  ```bash
  curl http://localhost:8888/SERVICE-CLIENT/api/client
  ```
- Liste des voitures enrichies :
  ```bash
  curl http://localhost:8888/SERVICE-CAR/api/car
  ```
- Détail d’une voiture (et donc du client qui la possède) :
  ```bash
  curl http://localhost:8888/SERVICE-CAR/api/car/1
  ```

### 3. Vérifier les services directement
- `http://localhost:8081/api/client/` pour le service client
- `http://localhost:8082/api/car/` pour le service voiture (avant la passerelle)

### 4. Ajouter un client
```bash
curl -X POST http://localhost:8081/api/client \
  -H "Content-Type: application/json" \
  -d '{"nom":"Nouvel Client","age":28}'
```

### 5. Pouvoir diagnostiquer
- La gateway expose les endpoints Actuator : [http://localhost:8888/actuator/health](http://localhost:8888/actuator/health) et `/actuator/gateway`.

## Résilience, monitoring & prochaines étapes

- Ajouter un circuit breaker (`Resilience4j`), le suivi (`Spring Cloud Sleuth`), la surveillance (`Prometheus/Grafana`) ou la documentation API (`OpenAPI/Swagger`).
- Pour centraliser la configuration, intégrer Spring Cloud Config et externaliser les `application.yml`.

Ce README résume la configuration livrée : chaque service possède sa propre base, utilise Eureka pour la découverte et la gateway centralise l’accès aux API métier.
