# Step 1: Build the application
FROM node:18 AS builder
WORKDIR /app
COPY . .
RUN yarn install
RUN yarn build

# Step 2: Set up the production environment
FROM nginx:stable-alpine
COPY --from=builder /app/build /usr/share/nginx/html
COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf.template

CMD /bin/sh -c "envsubst '\$PORT \$BACKEND_URL' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf" && nginx -g 'daemon off;'
