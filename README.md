# peopleflow
build project using maven to generate target folder:
mvn clean install

pull latest mysql docker image and run it as a container:
docker pull mysql:latest
docker run --name mysql-standalone -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=new_schema -e MYSQL_USER=root -e MYSQL_PASSWORD=root -d mysql:latest

show all containers:
docker ps -a

start mysql container:
docker container start mysql-standalone

go to root folder of project create docker image: 
docker build -t peopleflow .

docker run -p 8080:8080 --name peopleflow --link mysql-standalone:mysql peopleflow

Swagger UI documentation:
http://localhost:8080/peopleflow/api/swagger-ui.html

Open API documentation:
http://localhost:8080/peopleflow/api/api-docs