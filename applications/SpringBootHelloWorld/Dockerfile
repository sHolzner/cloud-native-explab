# base image
FROM openjdk:17

# port
EXPOSE 8080

# add jar file (left: position of your jar, right: name of the image, which is called in the entry)
ADD target/SpringBootHelloWorld-0.0.1-SNAPSHOT.jar helloWorld.jar

# entry, which will be worked out later
ENTRYPOINT ["java", "-jar", "helloWorld.jar"]