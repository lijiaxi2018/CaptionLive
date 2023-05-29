# Frontend
Frontend UI for CaptionLive! based on React and Redux.

## Run the frontend locally
Inside the frontend directory, install the dependency by
```
npm install
```

Or install the dependency without changing the lockfile by
```
npm install --read-only-lockfile
```

Then initialize the frontend service by
```
npm start
```

## Run the frontend via Docker
```
docker build -t cl_frontend -f DockerFile .
docker run -p 3000:3000 -td cl_frontend
```