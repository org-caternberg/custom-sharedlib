def call() {
    newSemVersion 1.0.1
    echo "1.0.1 -> $NEW_VERSION"
    mvnVersionsSet "$NEW_VERSION"
    mvn "clean install"
}