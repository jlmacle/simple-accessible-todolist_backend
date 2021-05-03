cd ..
echo "Jar build"
mvn package
mv -f target/*.jar z_build_script/context/
echo "Jar file moved in context folder"
cd z_build_script/context/
echo "Image build"
sudo docker build -t back-end:v0.9 .
echo "Removing potential back-end service"
sudo docker service rm atl-back-end &> /dev/null
echo "Starting the back-end service"
sudo docker service create --network atl-network --hostname backend --publish 8080:8080 --secret POSTGRES_PASSWORD -e POSTGRES_PASSWORD_FILE=/run/secrets/POSTGRES_PASSWORD --secret POSTGRES_USER -e POSTGRES_USER_FILE=/run/secrets/POSTGRES_USER --secret POSTGRES_DB -e POSTGRES_DB_FILE=/run/secrets/POSTGRES_DB --secret DB_JDBC_ROOT -e DB_JDBC_ROOT_FILE=/run/secrets/DB_JDBC_ROOT --name atl-back-end back-end:v0.9
