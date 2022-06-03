# Improve your containerized App

If you read this, you already containerized a simple spring web application, so congratulation.

In this chapter now an additional database should be added, structure will be given to the code,
and more steps towards a 12 factor app are done.

### Structure of the code

To achieve good readability as well as a good overview over your code, you should cluster it into different
classes. A possible structure should be given here:

* Application: Here only the start of the application happens, marked by the @SpringBootApplication Annotation

* Controller: As a @RestController they are able to obtain and define http requests from frontend components

* Service: Inside the service all data is requested from the storage, analysed and modified to pass it back to the controller.

* Model: Models sums up all classes, which are important and defined for our code.

* Repository: Repositories are used for communication with the used databases.

Of course all of these are just modeling recommendations and can differ from case to case.

### Include a database 

To include a database into your spring boot application, first of all add following dependencies to your pom.xml:

```
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
     <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
</dependency>
```

Hereby, jpa is used as communication between your code and the database. An example of this communication can be seen inside 
the Weather model of this application:

```
@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String city;
    private String weather;
    private Date date;

    public Integer getId() {
        return id;
    }

    ...
}
```

Annotated with the @Entity tag jpa recognizes this class as table inside a chosen database. Values markes with id
in this case the id of our weather data, are keys of this data table and therefore ideal for relational data base models.
As database in this application postgreSQL is used, but of course other databases like mysql are implementable in the same way.

Adding a Repository based on the CrudRepository:

```
public interface WeatherRepository extends CrudRepository<Weather, Integer> {

    Weather findWeatherById(Integer Id);

}
```

, which implements the fundamental requests: create, read, update, delete. And using
this data in service calls in the backend the database is usable already. Still, a problem occurs!
We have not defined in any way, where our database is located.

For this reason we need to create an application.properties file inside our src/main/resources folder.
In this we define location as well as passwords and type of our database.

```
spring.datasource.url=jdbc:postgresql://localhost:5432/weather_db
spring.datasource.username=springuser
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect

spring.datasource.driver-class-name=org.postgresql.Driver
```

* spring.datasource.url: describes the url of our database in this case localhost:5432 (default for postgres)
* spring.datasource.username: username of postgres database
* spring.datasource.password: password of user database
* spring.jpa.hibernate.ddl-auto: jpa updates the database, when started and written (alternatively: create, none, ...)
* spring.jpa.properties.hibernate.dialect: defines in this case postgres as database language
* spring.datasource.driverclassname: tells jpa to interpret the url as url pointing at a postgres database

### Creating a postgres database

If you don't know how to create a postgres database don't worry! I would personally recommend using docker, cause you
have this already installed in this step. Create a docker-compose.yml file, containing the following:

```
version: '3.5'

services:
  db:
    image: postgres:12
    container_name: db
    restart: always
    environment:
      POSTGRES_USER: springuser
      POSTGRES_PASSWORD: password
      POSTGRES_DB: weather_db
    ports:
      - "5432:5432"
```

Open a command line and type ``` docker-compose up ``` and you already created
a postgres database fitting our application properties. With this now your application should run
and your database as long itself exists contains the data, even when u restart the application itself.

#### Containerize the database combined with your application

But now there is a problem! If you define localhost as your database, obviously this will work at your pc
but not on another one or a kubernetes cluster. Therefore, we are not done now. To achieve a docker image of your new application
to work, we include it in the given docker-compose.yml. With this we create a multi-container docker image working on an
arbitrary machine.

For this the dev prod parity concept should be kept. This means we only change in the docker image,
what really cannot stay the same as on our local devices. Therefore, we add to our existing docker-compose file:

```
app:
    depends_on:
     - db
    build: .
    ports:
     - "8081:8080"
    environment:
      SPRING_DATASOURCE_URL : jdbc:postgresql://db:5432/weather_db
volumes:
  postgre_db:
    driver: local
```

Here now as a second service the written application is mentioned. As dependency the database is given
leading to the app being started after the database is implemented. the build attribute refers to the Dockerfile given
in the same folder, building your application as before into a dockerimage. Ports can be again chosen in the schema:
``` Your Desired Port : Docker Port ```

The most important line is here ``` SPRING_DATASOURCE_URL : jdbc:postgresql://db:5432/weather_db ```. As an environment variable
this line changes the url of the database to a new one positioned not on your localhost, but on the database created inside this
docker container and therefore the database designed above.

With this and the ``` docker-compose up ``` command, now a containerized version of your application can be created.

Congrats!!!
 