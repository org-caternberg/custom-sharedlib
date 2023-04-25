def call(String version){
  	sh "mvn -q versions:set -DnewVersion=${version} -DgenerateBackupPoms=false -DprocessAllModules=true"
}
