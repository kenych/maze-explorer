node {
checkout scm
sh 'git checkout test'
def approvedHashes = []

org.jenkinsci.plugins.scriptsecurity.scripts.ScriptApproval.get().approvedScriptHashes.each {
  println "approved hash: ${it}"
  approvedHashes << it	 
}

def json = groovy.json.JsonOutput.toJson(approvedHashes)

//new File('cnf.json').write(json)
writeFile file: "cnf.json", text: json, encoding: "UTF-8"

sh '''
 git config --global user.email "you@example.com"
 git config --global user.name "Your Name"
git add . ; git commit -m 'updating hashes';     git push --set-upstream origin test

'''
}
