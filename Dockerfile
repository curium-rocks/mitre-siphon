FROM ghcr.io/curium-rocks/alpine-zulu-jdk:main AS JAVA_BUILD
WORKDIR /app_build/
COPY ./ ./
RUN /app_build/scripts/sh/gradle-build.sh

FROM ghcr.io/curium-rocks/alpine-zulu-jre:main AS RUNTIME
USER docker
COPY --from=JAVA_BUILD /app_build/build/libs/ /app/
COPY --from=JAVA_BUILD /app_build/build/VERSION /app/
COPY ./scripts/sh/docker-entrypoint.sh /app/docker-entrypoint.sh
ENTRYPOINT [ "/app/docker-entrypoint.sh" ]
