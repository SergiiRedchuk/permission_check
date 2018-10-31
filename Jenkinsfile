node('fit_test') {
  stage('Licenses') {
    updateLicenses()
  }
}

def updateLicenses() {
    debug("updateLicenses")

    // todo
    
    sshagent (credentials: ['433ac100-b3c2-4519-b4d6-207c029a103b']) {
        def tagStatus = sh(script: "git tag ${newTag}", returnStatus: true)
        if( tagStatus != 0) {
            throw new Exception("Unable to tag the repository with tag '${newTag}'")
        }

        def configStatus = sh(script: "${GIT_SSH_COMMAND} git config --global user.email cwdsdoeteam@osi.ca.gov; git config --global user.name Jenkins",
                returnStatus: true)
        if( configStatus != 0) {
            throw new Exception("Unable to push the tag '${newTag}'")
        }
        def pushStatus = sh(script: "${GIT_SSH_COMMAND} git push origin ${newTag}",
                returnStatus: true)
        if( pushStatus != 0) {
            throw new Exception("Unable to push the tag '${newTag}'")
        }
    }
}
