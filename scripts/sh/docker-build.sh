#!/bin/sh
./gradlew versionFile
export VERSION=$(cat build/VERSION)
docker build . -t mitre-siphon:$VERSION