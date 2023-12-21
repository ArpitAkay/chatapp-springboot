# Stage 1: Build the Spring Boot application
FROM openjdk:17-jdk-slim AS build
WORKDIR /chatapp
COPY . /chatapp
RUN ./mvnw clean package

# Stage 2: Create MySQL image
FROM mysql:latest AS mysql
ENV MYSQL_ROOT_PASSWORD=root@123
ENV MYSQL_DATABASE=chatapp
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root@123
EXPOSE 3306
VOLUME /var/lib/mysql

CMD ["mysqld"]

# Stage 3: Create a minimal JRE image and copy the JAR file
FROM openjdk:17-jre-slim AS app
WORKDIR /chatapp
COPY --from=build /chatapp/target/chatapp-springboot-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080