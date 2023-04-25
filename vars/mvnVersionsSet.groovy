def call(String version){
  	sh "mvn  versions:set -DnewVersion=${version} -DgenerateBackupPoms=false -DprocessAllModules=true"
}
