echo "* Removing potential stack_backend service."
sudo docker service rm stack_backend 2> /dev/null

echo "* Removing potential stack_postgresql service."
sudo docker service rm stack_postgresql 2> /dev/null
echo "Waiting for the PostgreSQL service to stop."
sleep 20

echo "* Starting the Ubuntu PostgreSQL service"
sudo service postgresql start