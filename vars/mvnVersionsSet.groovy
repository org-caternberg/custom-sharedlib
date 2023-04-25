def call(String version){
  	sh "mvn -q versions:set -DprocessAllModules=true -DgenerateBackupPoms=false  -DnewVersion=${version}"
}
