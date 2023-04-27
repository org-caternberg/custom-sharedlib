def call(String repo,String branch) {
    echo "param world: ${repo}"
    /*The cloneAndCheckoutBranch.sh scripts expects the following environment variables
    However, it could also be shell parameters
    * */

    //TODO use the params, hardcoded here
    env.REPO_URL = "https://github.com/pipeline-demo-caternberg/maven-executable-jar-example.git"
    env.REPO_BRANCH = "master"
    env.GIT_CLONE_DIR = ".app"
    env.GIT_SHALLOW_DEPTH = "1"
    sh "cloneAndCheckoutBranch.sh "
}