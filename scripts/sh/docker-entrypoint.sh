#!/usr/bin/env sh
exec java -Duser.timezone=GMT -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar /app/mitre-siphon-$(cat /app/VERSION).jar
