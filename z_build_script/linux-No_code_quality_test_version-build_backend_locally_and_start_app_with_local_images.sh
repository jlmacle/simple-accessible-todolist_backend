echo "* Making sure that no PostgreSQL Ubuntu service is running."
sudo service postgresql stop
echo " Waiting for a potential PostgreSQL Ubuntu service to stop."
sleep 60

cd ..

echo "* Preparing for the Docker build."
#--------------------------------------------------------------------------------------------------------
# Building the jar file
#--------------------------------------------------------------------------------------------------------
echo "****** Preparing for the jar file building."
echo "****** ****** Suppressing a potential stack_backend service to release the port 8080 before using mvn package."
sudo docker service rm stack_backend

echo "****** ****** Pruning the Docker system to avoid unexpected side-effects that could involve port 8080."
sudo docker system prune -f

echo "****** ****** Verifying the remaining Docker services for a potential use of port 8080."
sudo docker service ls
sleep 20

echo "****** Building the jar file with mvn package."
mvn package

echo "****** Moving the jar file in the context folder."
mv -uf target/*.jar z_build_script/context/
echo "Jar file moved in context folder."

echo "* Building the Docker image."
mv -uf target/*.jar z_build_script/context/
echo "Jar file moved in context folder"

#--------------------------------------------------------------------------------------------------------
# Testing the new image built
#--------------------------------------------------------------------------------------------------------

echo "* Updating the potential stack."
sudo docker stack deploy -c z_docker_compose/docker-compose-stack-localImages.yml stack
sleep 60
echo "Waiting for the stack to be built."

echo "* Starting Chromium to check on the result."
chromium-browser http://127.0.0.1:8080 & chromium-browser http://127.0.0.1/index.html &

echo "* Clearing the code from the surefire-reports folder."
#-------------------------------------------------------------------------------------------------------
# Cleaning the code 
#-------------------------------------------------------------------------------------------------------
echo "mvn clean"
echo "The value of an apiKey stored in an environment variable has been dumped and later pushed on GitHub."
echo "The dump file was in the surefire-reports folder. "
echo "mvn clean suppresses the surefire reports. "
mvn clean



