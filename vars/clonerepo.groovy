def call(String repo,String branch) {
    echo "param world: ${repo}"
    //The cloneAndCheckoutBranch.sh scripts expects the following environment variables

    env.REPO_URL = "https://github.com/pipeline-demo-caternberg/maven-executable-jar-example.git"
    env.REPO_BRANCH = "master"
    env.GIT_CLONE_DIR = ".app"
    env.GIT_SHALLOW_DEPTH = "1"
  withCredeials
    sh "cloneAndCheckoutBranch.sh "
    //see https://www.baeldung.com/ops/jenkins-pipeline-output-shell-command-variable
    def result = sh(returnStdout: true, script: "helloworld.sh ${world}")
    echo "RESULT" + result
    return result
}