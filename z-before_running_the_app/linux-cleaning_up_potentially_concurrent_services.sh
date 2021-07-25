echo "** Removing potential stack_backend service using port 8080. **"
sudo docker service rm stack_backend 2> /dev/null
echo "** Removing potential stack_postgresql service using port 5432.**"
sudo docker service rm stack_postgresql 2> /dev/null

echo "** Testing if a Docker atl-postgres service is already running. **"
noAtlPostgresServiceFound=$(sudo docker service ls | grep atl-postgres)

# https://www.gnu.org/savannah-checkouts/gnu/bash/manual/bash.html#Conditional-Constructs
# https://www.gnu.org/savannah-checkouts/gnu/bash/manual/bash.html#Bash-Conditional-Expressions
# -z string: True if the length of string is zero.
if  [ -z "$noAtlPostgresServiceFound" ];
	then
		echo "***** No Docker atl-postgres service is running. Starting a Docker PostgreSQL service."
		sudo docker service create --publish published=5432,target=5432 \
		--secret POSTGRES_PASSWORD -e POSTGRES_PASSWORD_FILE=/run/secrets/POSTGRES_PASSWORD \
		--secret POSTGRES_USER -e POSTGRES_USER_FILE=/run/secrets/POSTGRES_USER \
		--secret POSTGRES_DB -e POSTGRES_DB_FILE=/run/secrets/POSTGRES_DB \
		--name atl-postgres postgres:alpine		
		
	else
		echo "***** A Docker atl-postgres service is already running."			
fi
