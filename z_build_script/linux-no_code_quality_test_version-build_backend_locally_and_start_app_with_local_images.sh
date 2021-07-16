echo "* Making sure that no PostgreSQL Ubuntu service is running."
sudo service postgresql stop
echo " Waiting for a potential PostgreSQL Ubuntu service to stop."
sleep 60

echo "* Making sure that no atl-postgres Docker service is running."
sudo docker service rm atl-postgres 2> /dev/null
echo " Waiting for a potential atl-postgres Docker service to stop."
sleep 60

cd ..

echo "* Docker build process."
echo "**** Login to Docker if necessary."
sudo docker login

echo "**** Building the jar file with."
sudo mvn spring-boot:build-image

echo "**** Re-taging the Docker image"
sudo docker tag accessible-todo-list_back-end:0.0.1-SNAPSHOT atl-back-end:v0.9

#--------------------------------------------------------------------------------------------------------
# Testing the new image built
#--------------------------------------------------------------------------------------------------------

echo "* Deploying with Docker stack."
sudo docker stack deploy -c z_docker_compose/docker-compose-stack-localImages.yml stack
sleep 60
echo "Waiting for the stack to be built."

echo "* Starting Chromium to check on the result."
chromium-browser http://127.0.0.1:8080 &


