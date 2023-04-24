def call(String ver) {
    def newVersion = sh(returnStdout: true, script: "newSematicVersion.sh -m ${ver}")
    echo "Output: ${newVersion}"
    try{
        sh "mvn versions:set -DnewVersion=${newVersion} -DgenerateBackupPoms=false -DprocessAllModules=true"
    }catch (Exception e) {
        throw e;
    }
}