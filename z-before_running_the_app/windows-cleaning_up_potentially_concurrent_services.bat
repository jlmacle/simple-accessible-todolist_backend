echo "**** ISSUE: the script needs to be finished. ****"
echo "** Removing potential stack_backend service using port 8080. **"
sudo docker service rm stack_backend 2> /dev/null
echo "** Removing potential stack_postgresql service using port 5432.**"
sudo docker service rm stack_postgresql 2> /dev/null