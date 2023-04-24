def call(String world) {
    echo "param world: ${world}"
    environment {
        WORLD = "${world}"
    }
    sh "helloworld.sh"
}

