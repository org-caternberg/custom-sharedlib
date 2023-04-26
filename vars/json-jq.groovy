def call(String jqargs,String jsonData) {
    echo "param jqargs: ${jqargs}"
    echo "param jsonData:"
    sh "echo ${jsonData} | jq"
    def result = sh "echo ${jsonData} | jq ${jqargs}"
    //Or: for more complex lines in shell, externalize to shell script f.e.
    // def result = sh(returnStdout: true, script: "jq-sample-step.sh ${jsonData) }${jqargs}")
    echo  result
    return result
}