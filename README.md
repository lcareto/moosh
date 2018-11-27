Moosh
-----
A REST and distributed (database-centric) Service Registry API, designed to allow any client to register with a Gateway/Proxy server.


Let's imagine you have :
- A `gateway-service` a SpringBoot based application
```java
@EnableRegistryServer
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
```

- An `account-service`, a SpringBoot base application that has a Users API available at `/users`:
```java
@EnableClientRegistry
@SpringBootApplication
public class AccountServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccountServiceApplication.class, args);
  }
}
```

### Registration

Annotation `@EnableRegistryServer` will initialize your Gateway as a proxy server and fetch all of the routes-service mapping, available in a database.
On the other hand, `@EnableClientRegistry` will register your service by just sending a Http call to the Gateway.
If any Discovery Server (Spring Eureka, Spring Cloud Kubernetes) is present in the classpath of the client, the service will be located using his id.

As we are using Http calls to register, client itself does not need to be a Spring application but can be a Python/NodeJs/whatever application that sends a request to `/moosh/clients` register to the running Gateway.

Eg. Here the Gateway is running locally on port `8080` and we want to start the `python-service` on port `8081`
```bash
curl -X POST \
  http://localhost:8080/moosh/clients \
  -H 'Content-Type: application/json' \
  -d '  {
        "id": "python-service",
        "url": "http://localhost:8081",
        "requestMappings": [
            {
                "method": "GET",
                "patterns": [
                    "/mooshers"
                ]
            }
        ]
    }'
```
   
### Routing
When the `account-service` is registered and running, the `/users` API will be available from the `gateway-service`.
Eg. `GET -> http://localhost:8080/users` (Proxy) and `GET -> http://localhost:8081/users` (Client) will both return the same result.   
 
Getting started 
----------------


### Minimum requirements
<ul>
    <li>Java 8</li>
    <li>Maven 3.5</li>
</ul>

Example 
----------------
Using Moosh Registry you could achieve the following level of architecture; 

A small example has been provided in `/example` folder;

![Alt text](example/Moosh_with_MS_architecture_on_Kubernetes.png?raw=true "Moosh with MS architecture on Kubernetes")