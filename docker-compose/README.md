# Run CaptionLive locally via Docker
Pull down the latest Docker images
```
docker compose pull
```

Initialize the system
```
docker compose up
```

## Tips
### For frontend development, initialize only the backend services
```
docker compose -f docker-compose-fe-dev.yml up
```

### Push a docker image to Docker Hub
```
docker tag IMAGE_NAME DOCKERHUB_NAME/IMAGE_NAME
docker push DOCKERHUB_NAME/IMAGE_NAME
```