# base image
FROM openjdk:17

# port
EXPOSE 8080

# jat file
COPY target/SpringBootWeather-0.0.1-SNAPSHOT.jar springbootweather.jar

# entry
ENTRYPOINT ["java", "-jar", "springbootweather.jar"]