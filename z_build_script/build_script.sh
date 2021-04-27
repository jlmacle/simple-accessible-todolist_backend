cd ..
mvn package
mv -f target/*.jar z_build_script/context/
echo "Jar file moved in context folder"
cd z_build_script/context/
sudo docker build -t back-end:docker-test .