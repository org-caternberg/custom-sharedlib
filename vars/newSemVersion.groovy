def call(String version) {
    echo "param world: ${version}"
    def result = sh(returnStdout: true, script: "newSematicVersion.sh -m ${version}")
    echo "RESULT: " + result.substring(result.lastIndexOf("##RESULT##:") + 1)
    env.NEW_VERSION=result.substring(result.lastIndexOf("##RESULT##:") + 1)
}