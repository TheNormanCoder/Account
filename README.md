# Account project

Eseguire i comandi mvn clean package per generare il jar descritto nel dockerfile

Docker

docker build -t account-image .

docker run -p 8080:8080 account-image

Swagger Doc disponibile all'indirizzo:
http://localhost:8080/account/swagger-ui/index.html
