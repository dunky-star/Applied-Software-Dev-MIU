# Stage 1: Build React App
FROM node:20-alpine AS builder

WORKDIR /app

# Copy dependency files & install
COPY package*.json ./
RUN npm install

# Copy the rest of the app and build
COPY . .
RUN npm run build

# Stage 2: Serve with NGINX
FROM nginx:alpine

# Copy built React app from builder
COPY --from=builder /app/build /usr/share/nginx/html

# Replace default NGINX config with custom one
COPY nginx.conf /etc/nginx/conf.d/default.conf

# NGINX will listen on port 3000
EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]
