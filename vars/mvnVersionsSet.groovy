def call(String version){
	//Not everything must go to shellscript, we can also use this way for simple things
  	sh "mvn -q versions:set -DprocessAllModules=true -DgenerateBackupPoms=false  -DnewVersion=${version}"
}
