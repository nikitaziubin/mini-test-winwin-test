# Mini Test: Two Spring Boot Apps with Postgres and Docker


To build auth-api: mvn -f auth-api/pom.xml clean package -DskipTests

To build data-api: mvn -f data-api/pom.xml clean package -DskipTests

To run the docker compose : docker compose up -d --build

To test the Register: curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d "{"email":"a@a.com","password":"pass"}"

To test the Login:  curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{"email":"a@a.com","password":"pass"}

To test process  curl -X POST http://localhost:8080/api/process -H "Authorization: Bearer <token>" -H "Content-Type: application/json" -d "{"text":"hello"}"

token should be taken from the result of the login call. 
