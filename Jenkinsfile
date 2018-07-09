node {
    
def approvedHashes = []

org.jenkinsci.plugins.scriptsecurity.scripts.ScriptApproval.get().approvedScriptHashes.each {
  println "approved hash: ${it}"
  approvedHashes << it	 
}

def json = groovy.json.JsonOutput.toJson(approvedHashes)

new File('cnf.json').write(json)
}
