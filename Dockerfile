FROM eclipse-temurin:17-jdk-alpine AS JAVA_BUILD
WORKDIR /app_build/
COPY ./ ./
RUN /app_build/scripts/sh/gradle-build.sh

FROM openjdk:17-alpine AS RUNTIME
WORKDIR /app
COPY --from=JAVA_BUILD /app_build/build/libs/ /app/
COPY --from=JAVA_BUILD /app_build/build/VERSION /app/
CMD /opt/openjdk-17/bin/java -jar -Dspring.profiles.active=prod mitre-siphon-$(cat /app/VERSION).jar
