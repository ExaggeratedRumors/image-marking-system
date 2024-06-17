# Use the official Node.js image.
FROM node:20-alpine as build

# Create and change to the app directory.
WORKDIR /app

# Copy application dependency manifests to the container image.
COPY package*.json ./

# Install dependencies.
RUN npm install

# Copy local code to the container image.
COPY . .

# Build the application.
RUN npm run build

# Use the official NGINX image for the production container.
FROM nginx:alpine

# Copy built assets from the build stage.
COPY --from=build /app/build /usr/share/nginx/html

# Expose the port that the app runs on.
EXPOSE 80

# Start NGINX server.
CMD ["nginx", "-g", "daemon off;"]
# Use the official Node.js image.
FROM node:20-alpine as build

# Create and change to the app directory.
WORKDIR /app

# Copy application dependency manifests to the container image.
COPY package*.json ./

# Install dependencies.
RUN npm install

# Copy local code to the container image.
COPY . .

# Build the application.
RUN npm run build

# Use the official NGINX image for the production container.
FROM nginx:alpine

# Copy built assets from the build stage.
COPY --from=build /app/build /usr/share/nginx/html

# Expose the port that the app runs on.
EXPOSE 8084

# Start NGINX server.
CMD ["nginx", "-g", "daemon off;"]
