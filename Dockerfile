FROM openjdk:21-jdk-slim AS base
VOLUME /tmp
WORKDIR /chatapp
COPY target/*.jar chatapp-springboot-0.0.1-SNAPSHOT.jar
FROM base AS mysql
ENV MYSQL_ROOT_PASSWORD=root@123
ENV MYSQL_DATABASE=chatapp
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root@123
EXPOSE 3306
VOLUME /var/lib/mysql
CMD ["mysqld"]
FROM base AS app
COPY --from=mysql /var/lib/mysql /var/lib/mysql
ENTRYPOINT ["java","-jar","chatapp-springboot-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080