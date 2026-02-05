# Paso 1: Construir el proyecto usando Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Usar la imagen oficial de WildFly 27
FROM quay.io/wildfly/wildfly:27.0.1.Final-jdk17

# Copiar el archivo .war generado al directorio de despliegue de WildFly
# Ajusta 'target/*.war' si tu archivo tiene un nombre específico
COPY --from=build /app/target/*.war /opt/jboss/wildfly/standalone/deployments/ROOT.war

# Configuración de la base de datos Postgres mediante variables de entorno
# WildFly permite configurar el Datasource automáticamente con estas variables
ENV WILDFLY_DATASOURCE_NAME=ejPostgresDS
ENV WILDFLY_DATASOURCE_JNDI=java:jboss/datasources/ejPostgresDS
ENV WILDFLY_POSTGRESQL_SERVICE_HOST=postgres
ENV WILDFLY_POSTGRESQL_SERVICE_PORT=5432

# Exponer el puerto que usará Railway
EXPOSE 8080

# Comando para arrancar WildFly y escuchar en todas las interfaces
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]