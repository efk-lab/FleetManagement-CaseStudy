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
Kubernetes

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
