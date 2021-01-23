FROM alpine:3.12 AS LOCAL_FILES
WORKDIR /app_files/
ADD ./ ./

FROM alpine:3.12 AS REACT_BUILD
WORKDIR /app_build
RUN apk add --no-cache nodejs npm
COPY --from=LOCAL_FILES /app_files/src/main/frontend/ /app_build/
WORKDIR /app_build/mitre-siphon-ui
RUN npm install && npm build

FROM alpine:3.12 AS JAVA_BUILD
RUN apk add --no-cache openjdk11
WORKDIR /app_build/
RUN pwd
RUN ls -la
COPY --from=LOCAL_FILES /app_files/ /app_build/
COPY --from=REACT_BUILD /app_build/dist/ /app_build/src/main/resources/static/
RUN sh scripts/sh/gradle-build.sh

FROM alpine:3.12 AS RUNTIME
RUN apk add --no-cache openjdk11-jre
WORKDIR /app
COPY --from=JAVA_BUILD /app_build/build/libs/ /app/
COPY --from=AVA_BUILD /app_build/build/VERSION /app/
RUN export VERSION=$(echo "$(cat /app/VERSION)")
CMD ["/bin/java", "-jar "]
