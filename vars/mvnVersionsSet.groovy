def call(String newVersion){
  try{
    	sh "mvn versions:set -DnewVersion=${newVersion} -DgenerateBackupPoms=false -DprocessAllModules=true"
  }catch (Exception e) {
      throw e;
  }
}
