FROM alpine:3.13 AS LOCAL_FILES
WORKDIR /app_files/
ADD ./ ./

FROM alpine:3.13 AS REACT_BUILD
WORKDIR /app_build
RUN apk add --no-cache nodejs npm
COPY --from=LOCAL_FILES /app_files/src/main/frontend/ /app_build/
WORKDIR /app_build/mitre-siphon
RUN npm install && npm run-script build

FROM openjdk:15-jdk-alpine AS JAVA_BUILD
WORKDIR /app_build/
COPY --from=LOCAL_FILES /app_files/ /app_build/
COPY --from=REACT_BUILD /app_build/mitre-siphon/build/ /app_build/src/main/resources/static/
RUN chmod +x /app_build/scripts/sh/gradle-build.sh
RUN chmod +x /app_build/gradlew
RUN /app_build/scripts/sh/gradle-build.sh

FROM openjdk:15-jdk-alpine AS RUNTIME
WORKDIR /app
COPY --from=JAVA_BUILD /app_build/build/libs/ /app/
COPY --from=JAVA_BUILD /app_build/build/VERSION /app/
CMD /opt/openjdk-15/bin/java -jar -Dspring.profiles.active=prod mitre-siphon-$(cat /app/VERSION).jar
