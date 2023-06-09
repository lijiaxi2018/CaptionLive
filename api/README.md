# Backend
Web server for CaptionLive!.

## Swagger
Graphical UI to test APIs provided by the server.

### To test the APIs with Swagger UI
Enter the following URL into the browser
```
http://localhost:8080/swagger-ui/index.html#/
```

## PgAdmin
Graphical UI to investigate the database.

### To manipulate the DB with PgAdmin
Enter the following URL into the browser
```
http://localhost:80/
```

## Spring Backend

### Running the Backend in the command line (Mac)
Go to the backend directory
```
cd CaptionLive/api
```

Install the Maven
```
brew install maven
```

Install dependencies
```
mvn install 
```

Compile
```
mvn package spring-boot:repackage
```

Start the application
```
java -jar target/captionlive-0.0.1-SNAPSHOT.jar
```

### Running the Backend via Docker
```
docker build -t cl_api -f DockerFile .
docker run -p 8080:8080 -td cl_api
```

### Running the Backend via Docker (No Cache)
```
docker build --no-cache -t cl_api -f DockerFile .
docker run -p 8080:8080 -td cl_api
```