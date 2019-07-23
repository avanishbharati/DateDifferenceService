#Integration Using Spring Boot, Apache Camel and RestTemplate

This project demonstrates how to use Spring Boot, Apache Camel, RestTemplate and date manipulation methods to process data.
Data is processable using folder location and a RESTful end point.
The emphasis is put on using Non-SDK techniques to get the number of days between the two given date values.
For future development, data could be retrieved/transported async using ActiveMq.

### Data format
This project reads data in a file of the format:

```DD MM YYYY, DD MM YYYY```

and process the date out in the following format difference:

``DD MM YYYY, DD MM YYYY, difference``

See data/in/sample.txt and data/out/sample.txt

# build package

```mvn clean package```

# Run locally

Run command ```spring-boot:run```

1. place valid file in data/in
2. see result data/out

# Swagger
Run locally and go to http://localhost:8080/datevalidationservice/swagger-ui


### Reference Documentation
* [List of Leap Years](https://kalender-365.de/leap-years.php)
* [Compare Results](https://www.timeanddate.com/date/durationresult.html?d1=31&m1=12&y1=2007&d2=31&m2=12&y2=2010)

### Built With

* [Maven](https://maven.apache.org)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Using Apache Camel with Spring Boot](https://camel.apache.org/spring-boot)
* [Swagger](https://swagger.io/)

## Authors
* **Avanish Bharati**

