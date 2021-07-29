@echo off
cls
::--------------------------------------------------------------------------------------------------------
:: Preparing for the new image build
::--------------------------------------------------------------------------------------------------------
echo ** Preparing for the new image build. **
echo **** Starting Docker Desktop.
cmd /c "C:\Program Files\Docker\Docker\Docker Desktop.exe"
echo Waiting for Docker Desktop to start
timeout /T 60

echo **** Making sure that no PostgreSQL Windows service is running. 
:: no PostgreSQL Windows service installed.
::echo Waiting for a potential PostgreSQL Windows service to stop.
::timeout /T 60

echo **** Making sure that no Docker stack is running (Solved a Docker back-end image update issue). 
docker stack rm stack 2> NUL
echo Waiting for a potential Docker stack to be removed.
timeout /T 60

cd ..

echo -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
::--------------------------------------------------------------------------------------------------------
:: Building the new image 
::--------------------------------------------------------------------------------------------------------
echo ** Docker build process. **
echo **** Login to Docker if necessary.
docker login

echo **** Building the Docker image with spring-boot:build-image.
cmd /c mvn spring-boot:build-image

echo **** Re-taging the Docker image
docker tag accessible-todo-list_back-end:0.0.1-SNAPSHOT atl-back-end:v0.9

echo -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
::--------------------------------------------------------------------------------------------------------
:: Testing the new image built
::--------------------------------------------------------------------------------------------------------

echo ** Deploying with Docker stack. **
echo **** Using docker stack deploy.
docker stack deploy -c z_docker_compose_files/docker-compose-stack-localImages.yml stack
timeout /T 60
echo **** Waiting for the stack to be built.

echo -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
echo ** Starting Edge to check on the result. **
start msedge http://127.0.0.1:8080 &

cd z_build_script 
