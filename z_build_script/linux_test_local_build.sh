sudo docker stack rm stack &>/dev/null
sleep 20
sudo service postgresql start
sleep 60

cd ..
mvn package

sudo service postgresql stop

mv -uf target/*.jar z_build_script/context/
echo "Jar file moved in context folder"

cd z_build_script/context
sudo docker build -t back-end:v0.9 .

cd ../..
sudo docker stack deploy -c z_docker_compose/docker-compose-stack-localImages.yml stack