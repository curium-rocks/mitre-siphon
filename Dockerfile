FROM ghcr.io/curium-rocks/alpine-zulu-jdk:main AS BUILD
WORKDIR /app_build/
COPY ./ ./
RUN ./gradlew build jar versionFile -x test

FROM ghcr.io/curium-rocks/alpine-zulu-jre:main AS RUNTIME
USER docker
COPY --from=BUILD /app_build/build/libs/ /app/
COPY --from=BUILD /app_build/build/VERSION /app/VERSION
COPY ./scripts/sh/docker-entrypoint.sh /app/docker-entrypoint.sh
ENTRYPOINT [ "/app/docker-entrypoint.sh" ]
