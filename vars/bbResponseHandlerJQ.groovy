def call() {
    //Fake some Git JSON response data
    def response = sh(returnStdout: true, script: "curl -s https://www.githubstatus.com/api/v2/status.json")
    def result = sh(returnStdout: true, script: "jq-sample-step.sh ${response} '.page.name'")
    echo  result
    return result
}