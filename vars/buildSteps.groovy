def call() {
    def new_version=newSemVersion 1.0.1
    echo "1.0.1 -> $NEW_VERSION"
    mvnVersionsSet new_version
    //OR use env variable: mvnVersionsSet $NEW_VERSION
    mvn "clean install"
}