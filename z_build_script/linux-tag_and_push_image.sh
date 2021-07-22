echo "* docker login."
sudo docker login
echo "* docker tag."
sudo docker tag atl-back-end:v0.9  jlmacle/atl-back-end:v0.9
echo "* docker push."
sudo docker push jlmacle/atl-back-end:v0.9
