echo "Starting the test with a local back-end image built."

echo "Suppressing a potentially existing stack."
sudo docker stack rm stack &>/dev/null
echo "Waiting for a potential stack to be removed."
sleep 20

echo "Suppressing a potentially existing Docker postgres service."
sudo docker service rm atl-postgres &>/dev/null
echo "Waiting for the potential Docker postgres service to be removed."
sleep 20

echo "Suppressing a potentially existing Docker front-end service."
sudo docker service rm atl-front-end &>/dev/null
echo "Waiting for the potential Docker atl-front-end service to be removed."
sleep 20

echo "Suppressing a potentially existing Docker atl-back-end service."
sudo docker service rm atl-back-end &>/dev/null
echo "Waiting for the potential Docker atl-back-end service to be removed."
sleep 20

echo "Demarrage du service postgresql" 
sudo service postgresql start
sleep 60

echo "Remaining Docker services:"
sudo docker service ls

cd ..
mvn package

sudo service postgresql stop

mv -uf target/*.jar z_build_script/context/
echo "Jar file moved in context folder"

cd z_build_script/context
sudo docker build -t atl-back-end:v0.9 .

cd ../..
sudo docker stack deploy -c z_docker_compose/docker-compose-stack-localImages.yml stack
sleep 30

chromium-browser http://127.0.0.1:8080