#--------------------------------------------------------------------------------------------------------
# Clean install to compensate for a potential mvn clean in end of script.
#--------------------------------------------------------------------------------------------------------
echo "** mvn clean install. **"
cd ..
sudo mvn clean install

#-------------------------------------------------------------------------------------------------------
# Checking the code quality
#-------------------------------------------------------------------------------------------------------
echo "** Starting the SonarQube analysis process. **"
echo "**** Starting the SonarQube server."
gnome-terminal -- bash -c 'sonar.sh start; sleep 90'
echo "**** Waiting for the SonarQube server to start"
sleep 90

echo "**** Starting the code quality analysis."
# sudo to avoid a permission issue as sudo is used to generate the target folder.
gnome-terminal -- bash -c 'sudo mvn sonar:sonar -Dsonar.projectKey=accessible-todo-list_back-end:jl.forthem \
-Dsonar.host.url=http://localhost:9000 -Dsonar.login=6fb38c8274cb8efccba8778aef56d226e7550659; sleep 100'

echo "**** Waiting for the analysis to be done."
sleep 100 

echo "**** Starting a browser to check the result of the analysis."
chromium-browser http://localhost:9000 &
sleep 100

#-------------------------------------------------------------------------------------------------------
# Docker image built and application start-up with local images
#-------------------------------------------------------------------------------------------------------
cd z_build_script
./linux-no_code_quality_test_version-build_backend_locally_and_start_app_with_local_images.sh
cd ..

#-------------------------------------------------------------------------------------------------------
# Cleaning the code 
#-------------------------------------------------------------------------------------------------------
echo "** Clearing the code from the surefire-reports folder. **"
echo "**** mvn clean"
echo "**** The value of an apiKey stored in an environment variable has been dumped and later pushed on GitHub."
echo "**** The dump file was in the surefire-reports folder. "
echo "**** mvn clean suppresses the surefire reports. "
sudo mvn clean



