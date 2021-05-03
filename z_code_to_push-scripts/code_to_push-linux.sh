# ** Running the code quality **
echo ""
echo "** Starting SonarQube and running the code quality analysis **"

# Starting the SonarQube server
gnome-terminal -- sh -c 'sonar.sh start; sleep 90'

# Running the analysis
# Waiting for the SonarQube server to start
echo "Waiting for the SonarQube server to start"
sleep 90

gnome-terminal -- sh -c 'cd .. ; mvn sonar:sonar -Dsonar.projectKey=accessible-todo-list_back-end:jl.forthem \
-Dsonar.host.url=http://localhost:9000 -Dsonar.login=6fb38c8274cb8efccba8778aef56d226e7550659; sleep 100'

# Starting a browser to check the result of the analysis
echo "Waiting for the analysis to be done."
sleep 40 
echo ""
echo "Starting a browser to check the result of the analysis."
chromium-browser http:// http://localhost:9000

echo ""
echo "** Running mvn clean ** "
echo "The value of an apiKey stored in an environment variable has been dumped and later pushed on GitHub."
echo "The dump file was in the surefire-reports folder. "
echo "mvn clean suppresses some data, including the surefire-reports. "

gnome-terminal -- sh -c 'cd .. ; mvn clean ; sleep 90'


echo "Waiting for the mvn clean to be done."
sleep 30
echo ""
echo "git add ."
git add .
echo "git commit: enter a commit message"
read commit
git commit -m "$commit"
echo "You entered $commit"
echo "Waiting before pushing the code."
sleep 40 
echo "git push"
git push



