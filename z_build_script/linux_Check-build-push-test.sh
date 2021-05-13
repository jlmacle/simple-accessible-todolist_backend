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
chromium-browser http://localhost:9000
sleep 100

#--------------------------------------------------------------------------------------------------------
# Building the jar file
#--------------------------------------------------------------------------------------------------------
echo "Jar build. The PostgreSQL service must be running."
sudo service postgresql start
sleep 60

#removing potential stack services
sudo docker stack rm stack &> /dev/null
cd ..
mvn package

mv -f target/*.jar z_build_script/context/
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

echo "Removing a potentially existing image."
sudo docker service rm atl-back-end &> /dev/null
sudo docker service rm atl-postgres &> /dev/null

echo "Starting the postgreSQL service"
sudo docker service create --publish  published=5432,target=5432 --network atl-network --hostname postgresql \
--secret POSTGRES_PASSWORD -e POSTGRES_PASSWORD_FILE=/run/secrets/POSTGRES_PASSWORD \
--secret POSTGRES_USER -e POSTGRES_USER_FILE=/run/secrets/POSTGRES_USER \
--secret POSTGRES_DB -e POSTGRES_DB_FILE=/run/secrets/POSTGRES_DB --name atl-postgres postgres:alpine

echo "Waiting for the postgreSQL service to start"
sleep 60

echo "Starting the back-end service"
cd
sudo docker service create --publish published=8080,target=8080 \
--secret POSTGRES_PASSWORD -e POSTGRES_PASSWORD_FILE=/run/secrets/POSTGRES_PASSWORD \
--secret POSTGRES_USER -e POSTGRES_USER_FILE=/run/secrets/POSTGRES_USER \
--secret POSTGRES_DB -e POSTGRES_DB_FILE=/run/secrets/POSTGRES_DB \
--secret DB_JDBC_ROOT -e DB_JDBC_ROOT_FILE=/run/secrets/DB_JDBC_ROOT --name atl-back-end jlmacle/back-end:v0.9

echo "Waiting for the back-end to start"
chromium-browser http://localhost:8080


