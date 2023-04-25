# Usa l'immagine di base di Java 17
FROM openjdk:17-alpine

# Copia il file jar dell'applicazione nella cartella /app
COPY target/Account-0.0.1-SNAPSHOT.jar /app/Account.jar

# Esponi la porta 8080
EXPOSE 8080

# Avvia l'applicazione usando il comando java
CMD ["java", "-jar", "/app/Account.jar"]