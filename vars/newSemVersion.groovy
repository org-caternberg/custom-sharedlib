def call(String version) {
    echo "param world: ${version}"
    def result = sh(returnStdout: true, script: "newSematicVersion.sh -m ${version}")
    reesult=result.substring(result.lastIndexOf("##RESULT##:") + 1)
    //echo result
    env.NEW_VERSION=result
    return result
}