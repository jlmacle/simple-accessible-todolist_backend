cls
@echo off

echo ** Starting Docker Desktop. **
cmd /c "C:\Program Files\Docker\Docker\Docker Desktop.exe"
echo **** Waiting for Docker Desktop to start
timeout /T 60

echo ** Removing potential stack_backend service using port 8080. **
docker service rm stack_backend 2> NUL
echo ** Removing potential stack_postgresql service using port 5432.**
docker service rm stack_postgresql 2> NUL

echo ** Testing if a Docker atl-postgres service is already running. **
(docker service ls | findstr atl-postgres) > tmp_info.txt
:: issue without the following line.
set isAtlPostgresServiceFound=
set /p isAtlPostgresServiceFound=< tmp_info.txt
set isAtlPostgresServiceFound="%isAtlPostgresServiceFound%"
del tmp_info.txt

IF %isAtlPostgresServiceFound% == "" (echo ***** No Docker atl-postgres service is running. Starting a Docker PostgreSQL service.
		docker service create --publish published=5432,target=5432 --secret POSTGRES_PASSWORD -e POSTGRES_PASSWORD_FILE=/run/secrets/POSTGRES_PASSWORD --secret POSTGRES_USER -e POSTGRES_USER_FILE=/run/secrets/POSTGRES_USER --secret POSTGRES_DB -e POSTGRES_DB_FILE=/run/secrets/POSTGRES_DB --name atl-postgres postgres:alpine ) ELSE (echo ***** A Docker atl-postgres service is already running.
		echo The back-end should be able to be run successfully.)








