link to dockerhub

## https://hub.docker.com/repository/docker/0n3p1a3r/ktorapi

create container with followed command

## docker run -d -p 8080:8080 --name your-container-name 0n3p1a3r/ktorapi

run in docker following command to start ngrok

## ngrok http 8080

then change baseUrl variable in src\main\java\com\example\a7zad\RetrofitHelper.kt to generated link by ngrok
