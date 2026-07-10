# Image Java légère
FROM eclipse-temurin:21-jdk-alpine
# Répertoire de travail
WORKDIR /app
# Copie du fichier JAR
COPY target/*.jar app.jar
# Port exposé
EXPOSE 8080
# Lancement de l'application
ENTRYPOINT ["java","-jar","app.jar"]
