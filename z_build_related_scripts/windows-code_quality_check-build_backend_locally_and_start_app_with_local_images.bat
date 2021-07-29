@echo off
cls
title Parent script.
::--------------------------------------------------------------------------------------------------------
:: Clean install to compensate for a potential mvn clean in end of script.
::--------------------------------------------------------------------------------------------------------
echo ** mvn clean install. **
cd ..
start "mvn clean install" mvn clean install
timeout /T 90

::-------------------------------------------------------------------------------------------------------
:: Checking the code quality
::-------------------------------------------------------------------------------------------------------
echo ** Starting the SonarQube analysis process. **
echo **** Starting the SonarQube server.
start "SonarQube" StartSonar.bat &
echo **** Waiting for the SonarQube server to start
timeout /T 90

echo **** Starting the code quality analysis.
start "SonarQube analysis" mvn sonar:sonar -Dsonar.projectKey=Todo-List-Backend -Dsonar.host.url=http://127.0.0.1:9000 -Dsonar.login=7996651e4d30e4839ede619aaf76a994d11380ef

echo **** Waiting for the analysis to be done.
timeout /T 100 

echo **** Starting a browser to check the result of the analysis.
start msedge http://localhost:9000
timeout /T 100

::-------------------------------------------------------------------------------------------------------
:: Docker image built and application start-up with local images
::-------------------------------------------------------------------------------------------------------
cd z_build_script
./windows-no_code_quality_test_version-build_backend_locally_and_start_app_with_local_images.bat
cd ..

::-------------------------------------------------------------------------------------------------------
:: Cleaning the code 
::-------------------------------------------------------------------------------------------------------
echo ** Clearing the code from the surefire-reports folder. **
echo **** mvn clean
echo **** The value of an apiKey stored in an environment variable has been dumped and later pushed on GitHub.
echo **** The dump file was in the surefire-reports folder. 
echo **** mvn clean suppresses the surefire reports. 
 mvn clean