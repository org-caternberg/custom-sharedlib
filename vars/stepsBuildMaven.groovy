def call() {
    pom = readMavenPom(file: 'pom.xml')
    def pom_version = pom.version
    def new_version=newSemVersion pom_version
    echo "1.0.1 -> $NEW_VERSION = ${new_version}"
    mvnVersionsSet "${new_version}"
    mvn "clean install"
}