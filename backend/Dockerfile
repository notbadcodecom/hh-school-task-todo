FROM maven:3.8.7-amazoncorretto-17 AS builder
WORKDIR /compile
COPY src ./src
COPY pom.xml .
RUN mvn package

FROM amazoncorretto:17.0.6-alpine3.17
WORKDIR /todo-app
COPY --from=builder /compile/target/todo-mvc-*.jar ./todo-mvc.jar
ENTRYPOINT ["java","-jar","/todo-app/todo-mvc.jar"]
