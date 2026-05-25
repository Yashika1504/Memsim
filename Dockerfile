FROM eclipse-temurin:17

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/memory-backend-0.0.1-SNAPSHOT.jar"]