FROM alpine:3.12 AS LOCAL_FILES
WORKDIR /app_files/
ADD ./ ./

FROM alpine:3.12 AS REACT_BUILD
WORKDIR /app_build
RUN apk add --no-cache nodejs npm
COPY --from=LOCAL_FILES /app_files/src/main/frontend/ /app_build/
WORKDIR /app_build/mitre-siphon
RUN npm install && npm run-script build

FROM alpine:3.12 AS JAVA_BUILD
RUN apk add --no-cache openjdk11
WORKDIR /app_build/
RUN pwd
RUN ls -la
COPY --from=LOCAL_FILES /app_files/ /app_build/
COPY --from=REACT_BUILD /app_build/mitre-siphon/build/ /app_build/src/main/resources/static/
RUN chmod +x /app_build/scripts/sh/gradle-build.sh
RUN chmod +x /app_build/scripts/sh/run-qa.sh
RUN chmod +x /app_build/gradlew
RUN /app_build/scripts/sh/run-qa.sh
RUN /app_build/scripts/sh/gradle-build.sh

FROM alpine:3.12 AS RUNTIME
RUN apk add --no-cache openjdk11-jre
WORKDIR /app
COPY --from=JAVA_BUILD /app_build/build/libs/ /app/
COPY --from=JAVA_BUILD /app_build/build/VERSION /app/
CMD /usr/bin/java -jar -Dspring.profiles.active=prod mitre-siphon-$(cat /app/VERSION).jar
