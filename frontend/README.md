# Frontend
Frontend UI for CaptionLive! based on React and Redux.

## Run the frontend locally
Inside the frontend directory, run the following
```
npm install
npm start
```

## Run the frontend via Docker
```
docker build -t cl_frontend -f DockerFile .
docker run -p 3000:3000 -td cl_frontend
```