# country-api

REST API using Java, Spring Boot, H2, Liquibase, Swagger, Docker

### How to run

To run Spring Boot application. In project root directory

```shell
mvn clean package

docker build -t country-api .  

docker run -it -p 8080:8080 country-api 
```

### Endpoints

```
http://localhost:8080/get-country/{ip} - returns details about country from supplied Ip address
http://localhost:8080/get-universities/{country} - returns list of universities from supplied country
```

### Swagger

```
Browser:  http://localhost:8080/swagger-ui/index.html#/
```

### H2

To access H2 database navigate to

```
Browser: http://localhost:8080/h2-console/

Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:mydb
User Name: sa
Password: 

```

# country-api
