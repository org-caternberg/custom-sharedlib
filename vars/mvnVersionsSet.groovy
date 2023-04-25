def call(String version){
  	sh "mvn  versions:set -DnewVersion=${version} -DprocessAllModules=true"
}
