jar:
	./gradlew versionFile bootJar
image: jar
	docker build . -t ghcr.io/curium-rocks/mitre-siphon:local
clean:
	./gradlew clean
run:
	helm upgrade --install ms helm/mitre-siphon --set image.tag=local --set image.pullPolicy=IfNotPresent