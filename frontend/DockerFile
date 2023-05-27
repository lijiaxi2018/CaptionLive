FROM node:14 AS frontend

WORKDIR /frontend

COPY package.json /frontend/

RUN npm install

COPY . .

CMD ["npm", "start"]

EXPOSE 3000