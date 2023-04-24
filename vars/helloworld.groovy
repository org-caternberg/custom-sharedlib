def call(String world) {
    environment {
        WORLD = "${world}"
    }
    sh "helloworld.sh"
}

