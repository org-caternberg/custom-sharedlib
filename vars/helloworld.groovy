def call(String world) {
    echo "param world: ${world}"
   // sh "helloworld.sh ${world}"
    def result = sh(returnStdout: true, script: "helloworld.sh ${world}")
    echo "RESULT" + result
    return result
}

