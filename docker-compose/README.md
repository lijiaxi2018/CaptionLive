# Run CaptionLive! locally via Docker
Pull down the latest Docker images
```
docker compose pull
```

Initialize the system
```
docker compose up
```

## Tips
### Push a docker image to Docker Hub
```
docker tag IMAGE_NAME DOCKERHUB_NAME/IMAGE_NAME
docker push DOCKERHUB_NAME/IMAGE_NAME
```