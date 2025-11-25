# --- ETAPA 1: Build ---
# Usamos una imagen de Maven con JDK 21 (igual al pom.xml) para compilar
FROM maven:3.9-eclipse-temurin-21 AS build

# Directorio de trabajo en el build
WORKDIR /app

# Copiamos solo el pom.xml primero para aprovechar la caché de capas de Docker
COPY pom.xml .

# Descargamos todas las dependencias (esta capa se cachea si el pom no cambia)
RUN mvn dependency:go-offline

# Ahora sí, copiamos el resto del código fuente
COPY src ./src

# Compilamos y empaquetamos el .jar. Saltamos los tests para el build
RUN mvn package -DskipTests

# --- ETAPA 2: Runtime ---
# Usamos una imagen JRE ligera (alpine es súper pequeña) para correr la app
FROM eclipse-temurin:21-jre-alpine

# Directorio de trabajo en la imagen final
WORKDIR /app

# Copiamos el .jar desde la etapa de 'build'
# OJO: El nombre 'demo-0.0.1-SNAPSHOT.jar' viene del <artifactId> en el pom.xml
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# El puerto 8080 es el que usa Spring por defecto
EXPOSE 8080

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]