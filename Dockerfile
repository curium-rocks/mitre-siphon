FROM alpine:3.17 AS LOCAL_FILES
WORKDIR /app_files/
ADD ./ ./

FROM openjdk:17-jdk-alpine AS JAVA_BUILD
WORKDIR /app_build/
COPY --from=LOCAL_FILES /app_files/ /app_build/
RUN chmod +x /app_build/scripts/sh/gradle-build.sh
RUN chmod +x /app_build/gradlew
RUN /app_build/scripts/sh/gradle-build.sh

FROM openjdk:17-jdk-alpine AS RUNTIME
WORKDIR /app
COPY --from=JAVA_BUILD /app_build/build/libs/ /app/
COPY --from=JAVA_BUILD /app_build/build/VERSION /app/
CMD /opt/openjdk-17/bin/java -jar -Dspring.profiles.active=prod mitre-siphon-$(cat /app/VERSION).jar
