IF "%RUN_GRADLE_TEST%"=="" (
    ECHO "Running gradle without test task"
    gradlew.bat build javadoc jar versionFile -x test
) else (
    ECHO "Running gradle with test task"
    gradlew.bat build javadoc jar versionFile
)


