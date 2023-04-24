def call(String newVersion){
  try{
    	sh "mvn -q versions:set -DnewVersion=${newVersion} -DgenerateBackupPoms=false -DprocessAllModules=true"
  }catch (Exception e) {
      throw e;
  }
}
