# 1. ETAPA DE CONSTRUCCIÓN
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. ETAPA DE EJECUCIÓN (WildFly 27)
FROM quay.io/wildfly/wildfly:27.0.1.Final-jdk17

USER root

# Descargamos el driver
ADD https://jdbc.postgresql.org/download/postgresql-42.6.0.jar /opt/jboss/wildfly/standalone/deployments/postgresql-jdbc.jar

# --- CONFIGURACIÓN DEL DATASOURCE ---
RUN echo "embed-server --server-config=standalone.xml" > /tmp/config.cli
RUN echo "data-source add --name=PostgresDS --jndi-name=java:jboss/datasources/ejPostgresDS --driver-name=postgresql-jdbc.jar --connection-url=jdbc:postgresql://\${env.DB_HOST}:\${env.DB_PORT}/\${env.DB_NAME} --user-name=\${env.DB_USER} --password=\${env.DB_PASSWORD} --check-valid-connection-sql=\"SELECT 1\" --background-validation=true" >> /tmp/config.cli
RUN echo "stop-embedded-server" >> /tmp/config.cli

# Ejecutamos el script
RUN /opt/jboss/wildfly/bin/jboss-cli.sh --file=/tmp/config.cli && rm /tmp/config.cli

# --- DESPLIEGUE ---
COPY --from=build /app/target/*.war /opt/jboss/wildfly/standalone/deployments/ROOT.war

# --- CORRECCIÓN DE PERMISOS (EL CAMBIO IMPORTANTE) ---
# Cambiamos el dueño de TODA la carpeta de WildFly al usuario jboss
# Esto soluciona el error WFLYDR0006 y el error de logs
RUN chown -R jboss:jboss /opt/jboss/wildfly/

EXPOSE 8080

# Volvemos al usuario jboss para arrancar de forma segura
USER jboss

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-Djboss.http.port=8080"]
