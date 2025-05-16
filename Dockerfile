# ───────────────────────────────────────────────
# 1) Stage de compilación con Maven + JDK 17
# ───────────────────────────────────────────────
FROM maven:3.8.4-openjdk-17 AS build

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos solo el pom para aprovechar el cache de dependencias
COPY pom.xml .

# Descarga e instala dependencias
RUN mvn dependency:go-offline -B

# Copiamos el resto del código fuente y compilamos
COPY src ./src
RUN mvn clean package -DskipTests -B

# ───────────────────────────────────────────────
# 2) Stage de runtime con JRE 17 (imagen más ligera)
# ───────────────────────────────────────────────
FROM openjdk:17-jdk-slim

# Directorio de trabajo
WORKDIR /app

# Copiamos el jar compilado desde el stage "build"
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto que usa Spring Boot
EXPOSE 8080

# Punto de entrada que arranca la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
