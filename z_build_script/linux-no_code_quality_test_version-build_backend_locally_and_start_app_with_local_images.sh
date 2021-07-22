#--------------------------------------------------------------------------------------------------------
# Preparing for the new image build
#--------------------------------------------------------------------------------------------------------

echo "* Making sure that no PostgreSQL Ubuntu service is running."
sudo service postgresql stop
echo "**** Waiting for a potential PostgreSQL Ubuntu service to stop."
sleep 60

echo "* Making sure that no Docker stack is running (Solved a Docker back-end image update issue)."
sudo docker stack rm stack 2> /dev/null
echo "**** Waiting for a potential Docker stack to be removed."
sleep 60

cd ..

#--------------------------------------------------------------------------------------------------------
# Building the new image 
#--------------------------------------------------------------------------------------------------------

echo "* Docker build process."
echo "**** Login to Docker if necessary."
sudo docker login

echo "**** Building the Docker image with spring-boot:build-image."
# sudo added to fix the following exception.
# I/O exception (java.io.IOException) caught when processing request to {}->docker://localhost:2376
# : com.sun.jna.LastErrorException: [13] Permission denied
sudo mvn spring-boot:build-image

echo "**** Re-taging the Docker image"
sudo docker tag accessible-todo-list_back-end:0.0.1-SNAPSHOT atl-back-end:v0.9

#--------------------------------------------------------------------------------------------------------
# Testing the new image built
#--------------------------------------------------------------------------------------------------------

echo "* Deploying with Docker stack."
echo "**** Using docker stack deploy."
sudo docker stack deploy -c z_docker_compose_files/docker-compose-stack-localImages.yml stack
sleep 60
echo "**** Waiting for the stack to be built."

echo "* Starting Chromium to check on the result."
chromium-browser http://127.0.0.1:8080 &


