CMD /C gradlew.bat versionFile
SET /P VERSION=<build/VERSION
docker build . -t mitre-siphon:%VERSION%