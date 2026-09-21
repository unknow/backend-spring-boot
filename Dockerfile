# ---- etapa 1: construir (precisa de Maven e JDK) ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# O pom vem antes do src de propósito: enquanto ele não mudar,
# o Docker reaproveita a camada com as dependências já baixadas.
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

# ---- etapa 2: executar (só precisa do JRE) ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Não rodar como root.
RUN addgroup -S app && adduser -S app -G app
USER app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
