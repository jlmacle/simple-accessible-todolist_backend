#-------------------------------------------------------------------------------------------------------
# Checking the code quality
#-------------------------------------------------------------------------------------------------------
echo "SonarQube"
gnome-terminal -- bash -c 'sonar.sh start; sleep 90'
echo "Waiting for the SonarQube server to start"
sleep 90

echo "Code quality analysis"
gnome-terminal -- bash -c 'cd .. ; mvn sonar:sonar -Dsonar.projectKey=accessible-todo-list_back-end:jl.forthem \
-Dsonar.host.url=http://localhost:9000 -Dsonar.login=6fb38c8274cb8efccba8778aef56d226e7550659; sleep 100'

echo "Waiting for the analysis to be done."
sleep 100 

echo "Starting a browser to check the result of the analysis."
chromium-browser http://localhost:9000 &
sleep 100

#--------------------------------------------------------------------------------------------------------
# Building the jar file
#--------------------------------------------------------------------------------------------------------
echo "Jar build. The PostgreSQL service must be running."
sudo docker stack rm stack &>/dev/null
echo "Waiting for a potential stack to be removed."
sleep 20
sudo service postgresql start
sleep 60

echo "Remaining Docker services:"
sudo docker service ls

cd ..
mvn package

mv -uf target/*.jar z_build_script/context/
echo "Jar file moved in context folder"

#-------------------------------------------------------------------------------------------------------
# Cleaning the code 
#-------------------------------------------------------------------------------------------------------
echo "mvn clean"
echo "The value of an apiKey stored in an environment variable has been dumped and later pushed on GitHub."
echo "The dump file was in the surefire-reports folder. "
echo "mvn clean suppresses the surefire-reports. "
mvn clean

#--------------------------------------------------------------------------------------------------------
# Pushing the code to GitHub
#--------------------------------------------------------------------------------------------------------
echo "git"
git add .
echo "git commit: enter a commit message"
read commit
git commit -m "$commit"
echo "You entered $commit"
echo "git push"
git push

#--------------------------------------------------------------------------------------------------------
# Testing the new image built
#--------------------------------------------------------------------------------------------------------
echo "Waiting for the image update."
sleep 300
sudo docker pull jlmacle/atl-back-end:v0.9

echo "Removing a potentially existing image."
sudo docker service rm atl-back-end &> /dev/null
sudo docker service rm atl-postgres &> /dev/null

echo "Removing potential Ubuntu postgreSQL service"
sudo service postgresql stop &> /dev/null
sleep 20

sudo docker stack deploy -c z_docker_compose/docker-compose-stack-dockerHub.yml stack

sleep 120
chromium-browser http://127.0.0.1:8080 &


