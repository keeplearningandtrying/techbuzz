# TechBuzz REST API using SpringBoot + Java 8

### Authenticate User

`curl -H "Content-Type: application/json" -X POST -d '{"username":"admin","password":"admin"}' http://localhost:9090/auth/login`


### API Endpoints

`curl -H "Authorization: Bearer TOKEN_HERE" -X GET http://localhost:9090/api/me`

`curl -H "Content-Type: application/json" -X GET http://localhost:9090/api/links`