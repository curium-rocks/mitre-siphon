FROM alpine:3.13 AS LOCAL_FILES
WORKDIR /app_files/
ADD ./ ./

FROM openjdk:15-jdk-alpine AS TEST
WORKDIR /app_test/
COPY --from=LOCAL_FILES /app_files/ /app_test/
RUN chmod +x /app_test/scripts/sh/run-qa.sh
RUN chmod +x /app_test/gradlew

RUN apk add --no-cache git curl bash findutils

ENV spring_profiles_active=ci