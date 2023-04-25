def call() {
    //readMavenPom requires the pipeline-utility plugin
    def pom = readMavenPom(file: 'pom.xml')
    def pom_version = pom.version
    def new_version=newSemVersion pom_version+"-SNAPSHOT"
    echo "OLD_VERSION: ${pom_version} -> NEW_VERSION  = ${new_version}"
    mvnVersionsSet "${new_version}"
    mvn "clean install"
}