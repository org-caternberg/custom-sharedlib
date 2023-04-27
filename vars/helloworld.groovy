def call(String world) {
    echo "param world: ${world}"
    //see https://www.baeldung.com/ops/jenkins-pipeline-output-shell-command-variable
    def result = sh(returnStdout: true, script: "helloworld.sh ${world}")
    echo "RESULT" + result
    return result
}



