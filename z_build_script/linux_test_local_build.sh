echo "Starting the test with a local back-end image built."
sudo docker stack rm stack &>/dev/null
echo "Waiting for a potential stack to be removed."
sleep 20
sudo service postgresql start
sleep 60

echo "Remaining Docker services:"
sudo docker service ls.

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