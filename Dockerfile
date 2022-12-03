FROM eclipse-temurin:17-jdk-alpine AS JAVA_BUILD
WORKDIR /app_build/
COPY ./ ./
RUN /app_build/scripts/sh/gradle-build.sh

FROM eclipse-temurin:17-alpine AS RUNTIME
ARG VERSION
WORKDIR /app
COPY --from=JAVA_BUILD /app_build/build/libs/ /app/
COPY --from=JAVA_BUILD /app_build/build/VERSION /app/
COPY ./scripts/sh/docker-entrypoint.sh /app/docker-entrypoint.sh
ENTRYPOINT [ "/app/docker-entrypoint.sh" ]
