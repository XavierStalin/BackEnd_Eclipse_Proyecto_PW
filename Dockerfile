# 1. ETAPA DE CONSTRUCCIÓN
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. ETAPA DE EJECUCIÓN (WildFly 27)
FROM quay.io/wildfly/wildfly:27.0.1.Final-jdk17

USER root

# Descargamos el driver de Postgres directamente en la carpeta de despliegues
# WildFly 27 lo reconocerá automáticamente como un driver llamado "postgresql-jdbc.jar"
ADD https://jdbc.postgresql.org/download/postgresql-42.6.0.jar /opt/jboss/wildfly/standalone/deployments/postgresql-jdbc.jar

# CONFIGURACIÓN DEL DATASOURCE (Unificada en un solo comando)
# Usamos el nombre del archivo .jar como driver-name
RUN /opt/jboss/wildfly/bin/jboss-cli.sh --command="embed-server, \
    data-source add --name=PostgresDS \
    --jndi-name=java:jboss/datasources/ejPostgresDS \
    --driver-name=postgresql-jdbc.jar \
    --connection-url=jdbc:postgresql://\${env.DB_HOST}:\${env.DB_PORT}/\${env.DB_NAME} \
    --user-name=\${env.DB_USER} \
    --password=\${env.DB_PASSWORD} \
    --check-valid-connection-sql=\"SELECT 1\" \
    --background-validation=true, \
    stop-embedded-server"

# Copiamos el WAR generado (se renombra a ROOT.war para que sea la app principal)
COPY --from=build /app/target/*.war /opt/jboss/wildfly/standalone/deployments/ROOT.war

# Permisos correctos para el usuario jboss
RUN chown -R jboss:jboss /opt/jboss/wildfly/standalone/configuration

EXPOSE 8080

USER jboss

# Comando de inicio con resolución de variables activada
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-Djboss.http.port=8080"]
