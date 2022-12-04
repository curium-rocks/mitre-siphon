FROM ghcr.io/curium-rocks/alpine-zulu-jre:main AS RUNTIME
USER docker
COPY ./build/libs/ /app/
COPY ./build/VERSION /app/VERSION
COPY ./scripts/sh/docker-entrypoint.sh /app/docker-entrypoint.sh
ENTRYPOINT [ "/app/docker-entrypoint.sh" ]
