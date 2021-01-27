#!/bin/sh
./gradlew test codeCoverageReport
if [ -z ${CODECOV_TOKEN+x} ]; then
  echo "skipping reporting coverage to codecov"
else
  bash -c '/bin/bash <(curl -s https://codecov.io/bash)'
fi

