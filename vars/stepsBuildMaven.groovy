def call() {
    //readMavenPom requires the pipeline-utility plugin and "Script Approvals" on FolderLevel Libs!
    /*def pom = readMavenPom(file: 'pom.xml')
    def pom_version = pom.version
    def new_version=newSemVersion pom_version
    */
    //One alternative would be to parse with yq (https://github.com/kislyuk/yq) like so:
    //For that , xq needs to be installed in the pod
    def pom_version = sh(returnStdout: true, script: "cat pom.xml | xq -r '.project.version'")
    def new_version=newSemVersion pom_version
   // def new_version="1.1.1-SNAPSHOT"
    echo "OLD_VERSION: ${pom_version} -> NEW_VERSION  = ${new_version}"
    mvnVersionsSet "${new_version}"
    mvn "clean install"
}