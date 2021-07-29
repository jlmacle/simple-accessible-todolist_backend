cls
@echo off 

echo Assuming that Docker Destop is started.
echo ** docker login. **
docker login
echo -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
echo ** docker tag. **
docker tag atl-back-end:v0.9  jlmacle/atl-back-end:v0.9
echo -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
echo ** docker push. **
docker push jlmacle/atl-back-end:v0.9
