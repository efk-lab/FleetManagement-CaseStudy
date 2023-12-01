# FleetManagement-CaseStudy

FleetManagement is a system which consists of 3 microservices:
- FleetManager
- VehicleManager
- CustomerManager

# Tech Stack

Java11,
Spring Boot(web, test, data-mongodb, data-redis, security, log4j2, cache, validation, aop),
Spring Kafka,
Spring Security Oauth2,
Spring Cloud Circuitbreaker,
REST,
MongoDB,
Kafka,
Redis,
Resilience4j,
Maven,
Junit5, Mockito, Assertj,
Kubernetes,
Microservices

# Rest API

- FLEETMANAGER
  - UserRegistryController : Restfull Service for user registry.
    - signUp
 
  - DeliveryPointController : Restfull Service for delivery point operations.
    - saveDeliveryPoint 

  - VehicleController : Restfull Service for vehicle operations.
    - saveVehicle
    - getVehicle

  - ShipmentController : Restfull Service for shipment operations.
    - savePackage
    - getShipment
    - saveBag
    - assignPackageToBag
    
  - DistributionController : Restfull Service for distribution operations.
    - saveDistribution
    - getDistribution

  - DistributionLogController : Restfull Service for distribution log operations.
    - getDistributionLog
   
- VEHICLEMANAGER
  - UserRegistryController : Restfull Service for user registry
    - signUp
      
  - DistributionController : Restfull Service for distribution operations.
    - startDistribution

- CUSTOMERMANAGER
  - UserRegistryController : Restfull Service for user registry
    - signUp
   
  - ShipmentStateController : Restfull Service for shipment state operations.
    - getShipmentState

# Deployment

- Install minikube
  - https://minikube.sigs.k8s.io/docs/start/
    
- Install Kafka
   >helm install my-release oci://registry-1.docker.io/bitnamicharts/kafka
   
- Build Images
  > docker build -t fleetmanager:latest -f /eclipse-workspace/FleetManager/src/main/resources/Dockerfile .
  
  > docker build -t vehiclemanager:latest -f /eclipse-workspace/VehicleManager/src/main/resources/Dockerfile .
  
  > docker build -t customermanager:latest -f /eclipse-workspace/CustomerManager/src/main/resources/Dockerfile .
  
  > eval $(minikube docker-env)
  
- Deploy Applications
  > kubectl apply -f fleetmanagement.yaml
  
- For loadbanacer run command below
  > minikube tunnel
  
- Kubernetes Dashboard
  > minikube dasboard
  
- Prometheus&Grafana
  > kubectl create namespace monitoring
  
  > helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
  
  > helm install k8spromethuesstack --namespace monitoring prometheus-community/kube-prometheus-stack
