FROM node:18 as base

RUN apt-get update
WORKDIR /app
COPY package.json ./
RUN npm install -f
COPY . .
RUN chown node:node /app

EXPOSE 3000

CMD [ "npm", "run", "start" ]