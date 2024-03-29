clear

echo "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------"
echo "Running the back-end as a Spring Boot app assumes for a regular, 
non-Docker PostgreSQL service to be running (to use localhost, instead of postgresql, as hostname)."
echo "The configuration information for the PostgreSQL service can be found in the post below."
echo "https://javafullstackcode.wpcomstaging.com/2021/02/24/linux-ubuntu-cloning-the-back-end-code-configuring-postgresql-the-environment-variables-and-spring-tool-suite-edited/"

#--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
# Script testing
# Creation of stack_backend and stack_postgresql services
 echo "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------"
 echo "** Creation of stack_backend and stack_postgresql services for script testing purposes." sudo docker stack deploy -c ../z_docker_compose_files/docker-compose-stack-localImages.yml stack
sudo docker stack deploy -c ../z_docker_compose_files/docker-compose-stack-localImages.yml stack

echo "** Creation of atl-back-end and atl-postgres services for script testing purposes."

#sudo docker network create --driver overlay atl-network &> /dev/null
#
#sudo docker service create --network atl-network --hostname postgresql \
#--publish  published=5432,target=5432 \
#--secret POSTGRES_PASSWORD -e POSTGRES_PASSWORD_FILE=/run/secrets/POSTGRES_PASSWORD \
#--secret POSTGRES_USER -e POSTGRES_USER_FILE=/run/secrets/POSTGRES_USER \
#--secret POSTGRES_DB -e POSTGRES_DB_FILE=/run/secrets/POSTGRES_DB \
#--name atl-postgres postgres:alpine
#
#sudo docker service create --network atl-network --hostname backend \
#--publish published=8080,target=8080 \
#--secret POSTGRES_PASSWORD -e POSTGRES_PASSWORD_FILE=/run/secrets/POSTGRES_PASSWORD \
#--secret POSTGRES_USER -e POSTGRES_USER_FILE=/run/secrets/POSTGRES_USER \
#--secret POSTGRES_DB -e POSTGRES_DB_FILE=/run/secrets/POSTGRES_DB \
#--secret DB_JDBC_ROOT -e DB_JDBC_ROOT_FILE=/run/secrets/DB_JDBC_ROOT \
#--name atl-back-end atl-back-end:v0.9

# End of script testing code
#--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

echo "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------"
echo "** Removing potential stack_backend service using port 8080 and potential stack_postgresql service using port 5432.**"
sudo docker stack rm stack &> /dev/null
echo "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------"
echo "** Removing potential atl-postgres and atl-backend services. **"
sudo docker service rm atl-postgres &> /dev/null
sudo docker service rm atl-back-end &> /dev/null

echo "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------"
echo "Waiting for the PostgreSQL Ubuntu service to be ready to accept connections."
sleep 120
