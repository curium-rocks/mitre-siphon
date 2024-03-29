FROM ghcr.io/curium-rocks/alpine-zulu-jdk:main AS TEST
WORKDIR /app_test/
COPY ./ /app_test/

RUN apk add --no-cache git curl bash findutils nodejs npm
ENV NODE_PATH=/usr/bin
ENV spring_profiles_active=ci