 # Weather API Caching Service


 # Information
 * This project is for beginners who wish to implement Restful Api service with Java Spring Boot, Redis cache service and 3rd party visual crossing api.The project provides fetching real-time Weather data based on request parameters. The API supports logging for request tracking and Redis cache integration.
* ## Resolving request
   * Retrieve real-time weather conditions data based on location, startDate, endDate, include , current parameters.
   * Include parameter defines what kind of data you need. There are two possible values : days and hours. If you choose days the data will include specified days's summary conditions like tempmin,tempmax, humidity etc. If you choose hours the data will have hourly weather conditions in specified days.If not provided system defaults to the days.
   * Location is mandatory.
   * The date values are optional. If you dont pass any date , the system will fetch weather data within 7-days beginning from today.If only startDate is provided then the data will include only single day. The endDate cannot be used without startDate.
   * Current parameter specialized the data with only todays's or current time's data. It is optional but the dates are forbidden when using current.

* ## Redis Integration
    * Each query first checks the cache storage. Only missing data will be obtained form external API, and then stored in the cache.
    * Every cache ad api operations are logged by logging aspect. It indicates where the data is returned.
    * **Example workflow:**
        * QUERY1 = /weather?location=Istanbul&startDate=2025-07-11 -> Data is not in the cache, fetched from API and stored.

* ## Custom Logging Aspect
  * Custom logging aspect is designed to log the method calls of services and exceptions they lead to    

### Explore Rest APIs

Endpoints Summary
<table style="width:100%;">
    <tr>
        <th>Method</th>
        <th>Url</th>
        <th>Description</th>
        <th>Request Body</th>
        <th>Path Variable</th>
        <th>Response</th>
    </tr>
    <tr>
        <td>GET</td>
        <td>/weather</td>
        <td>Retrieve weather data</td>
        <td>Location<br>startDate<br>endDate<br>Include<br>current</td>
        <td>None</td>
        <td>WeatherDto</td>
    </tr>
</table>  


 ## Technologies

 ### Dependencies
 - Java SE 24
 - Spring Boot 3.5.3
 - Spring Web
 - Spring Data Redis
 - Spring Cloud OpenFeign
 - Spring AOP
 - Lombok
 - MapStruct
 - Spring Open API

 ### Development tools
 - Intellij Idea Community edition
 - Postman
 - Docker
 - Maven build tool
 - Swagger UI


## Required configuration properties

 * The visual crossing weather api requires a key to be able to connect with it. You can obtain your key from here : [Visual Crossing](https://www.visualcrossing.com/). And you should set it in the applicaiton properties.

  * weatherapi.key = YOUR_WEATHER_API_KEY

## Setting up Redis

* The API utilizes Redis and it is built by using docker container. You can also set your redis standalone by downloading it but in this way you dont need it. Docker handles it for yourself.

* You should first dowland docker and run this command: 
* **docker run --name redis-weather -p 6379:6379 -d redis**
    * **--name redis-weather** determines name for container
    * **-p 6379:6379** sets ports for redis and the accessor to it. 
    * **-d redis** indicates that redis will be started in the container and can be run also in background.

* **6379** is default port of **Redis**. When it is dowloanded **6379** will be used implicitly. Redis also opens itself in this port when inside **Docker**.

* However the ports that exist in docker container are not reachable outside the container. So we need to set it reach.
    * **-p 6379:6379** handle this we start redis in 6379 and make it reachable from 6379.


## Setting Spring Data Redis

```properties
spring.data.redis.port=6379
spring.data.redis.host=localhost
spring.data.redis.client-type=JEDIS

cache.ttl.hourly = 1
cache.ttl.daily = 4
```

* To be able to connect the redis , these configurations are made. Jedis is one of the redis java clients. Oother one is lettuce and it is default one. You dont need to specify it explicitly.
* The cache enables us to limit the duration of objects within the storage. The duraiton values are defined inside app properties and they are retrieved from configuration class.

## Compiling And Running the API
* Maven commands can be used to initialize the app.
    * ``` mvn clean install ``` | This will compile the project also clean if there are older files dowloands the dependencies,run the tests and jar file is created.
    * ```java -jar target/basic-app-0.0.1-SNAPSHOT.jar ``` | This runs the built jar file.
    * Alternatively we can run the project faster due to usage Spring boot.
        * ```maven spring-boot run ``` |This command compiles the app and run it quickly without packageing the app, creating the jar file and running the tests.

* In Intellij Idea , building and running the applicaiton is much more straightforward.

## Swagger UI
* OpenApı definiton can be inspected with Swagger: 
    * http://localhost:8080/swagger-ui/index.html
