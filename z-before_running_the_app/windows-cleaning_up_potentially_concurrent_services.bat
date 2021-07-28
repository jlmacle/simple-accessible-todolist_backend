cls
@echo off
echo **** Running the back-end as a Spring Boot app assumes for a regular, non-Docker PostgreSQL service to be running.
(to use localhost, instead of postgresql, as hostname)  ****
echo ** Starting Docker Desktop. **
cmd /c "C:\Program Files\Docker\Docker\Docker Desktop.exe"
echo **** Waiting for Docker Desktop to start
timeout /T 60

** Removing potential stack_backend service using port 8080 and potential stack_postgresql service using port 5432.**

echo ** Removing potential stack_backend service using port 8080 and potential stack_postgresql service using port 5432.**
docker stack rm stack 2> NUL
echo ** Removing potential atl-postgres and atl-backend services. **
docker service rm atl-postgres 2> NUL
docker service rm atl-back-end 2> NUL