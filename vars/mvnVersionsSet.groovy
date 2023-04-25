def call(String version){
  	sh "mvn -q versions:set -DprocessAllModules=true -DnewVersion=${version}"
}
