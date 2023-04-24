def call(String world) {
    echo "param world: ${world}"
    sh "helloworld.sh $world"
}

