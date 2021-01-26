#!/bin/sh
./gradlew test jacocoReport
if [ -z ${CODECOV_TOKEN+x} ]; then
  echo "skipping reporting coverage to codecov"
else
  bash <(curl -s https://codecov.io/bash)
fi

