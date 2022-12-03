FROM eclipse-temurin:17-jdk-alpine AS TEST
WORKDIR /app_test/
COPY ./ /app_test/

RUN apk add --no-cache git curl bash findutils

ENV spring_profiles_active=ci