def call(String version){
  	sh "mvn  versions:set -DprocessAllModules=true -DnewVersion=${version}"
}
