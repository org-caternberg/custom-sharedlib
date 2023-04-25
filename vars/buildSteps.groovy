def call() {
    def new_version=newSemVersion "1.0.1"
    echo "1.0.1 -> $NEW_VERSION = ${new_version}"
    mvnVersionsSet "${new_version}"
    mvn "clean install"
}