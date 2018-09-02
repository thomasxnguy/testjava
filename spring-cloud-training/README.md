Spring Cloud Course Summary
----
This project is a compilation of all the spring-cloud components used during certification training.


### Registration Server
Sample registration endpoint

Run on ``http://localhost:8083`` 
- healthcheck : ```/health```
- Actuator metric: ```/metrics``` (JVM..)
- Hystric stream: ``/hystrix.stream`` (Continuous ping)

### Timesheets Server
Run on ``http://localhost:8084 ``
- healthcheck : ```/health```
- Actuator metric: ```/metrics (JVM..)```
- Hystric stream: ```/hystrix.stream``` (Continuous ping)



````
curl -i -XPOST -H"Content-Type: application/json" localhost:8084/time-entries/ -d"{\"projectId\": 1, \"userId\": 1, \"date\": \"2015-05-17\", \"hours\": 6}"
````

### Eureka Server
For Service Registry and Discovery :

Run on 
```
http://localhost:8761
```
#Hystrix-Dashboard
For Fault tolerance, Isolation strategy (bulking,loadshedding) :

Stats dashboard run on 
````
http://localhost:8085
````
### Turbine-Server 
For Hystrix stats aggregation :

Use streaming protocole ans queue

Run on 
```
http://localhost:8086/turbine.stream
```
If use rabbit :

Run rabbitMQ using etc/docker/docker-compose.yml
Access rabbitMQ dashboard on : localhost:15672
Change annotation from @EnableTurbine to @EnableTurbineStream

### Config-server
For managing configuration file through git-repository

Run on 
````
http://localhost:8888

````


Need to modify app.properties:
````
server.port=8888
spring.cloud.config.server.git.uri={Path to config repo}
````

App config visible through :

``curl localhost:8888/timesheets-server.properties``

To refresh (If bean annotated @RefreshScope) :

```
curl -i -XPOST localhost:8084/refresh
```


Secret File can be stored using vault :

Vault project web site : https://www.vaultproject.io/


### Oauth2 Server

Run on 8080

````
curl -X POST localhost:8080/oauth/token -u timesheets-server:secret -d 'grant_type=client_credentials&response_type=token&scope=project.read'
````

Response 

````
{
  "access_token": "75ebe6f1-74e5-41d7-b9c1-5fb823eb7d6c",
  "token_type": "bearer",
  "expires_in": 43193,
  "scope": "project.read"
}

````

Each request needs to include bearer

````
curl -i localhost:8083/projects/1 -H "Authorization: Bearer {{token}}"
````
