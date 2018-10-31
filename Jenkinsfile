// Used to avoid known_hosts addition, which would require each machine to have GitHub added in advance (maybe should do?)
GIT_SSH_COMMAND = 'GIT_SSH_COMMAND="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no"'

node('master') {
  stage ('Generate License Report') {
    buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'downloadLicenses'
    // todo test repeated
    org.apache.commons.io.FileUtils.copyDirectoryToDirectory(new File('./build/reports/license'), new File('.'))
  }
  stage ('Push License Report') {
    pushLicenseReport()
  }
}

def pushLicenseReport() {
  // todo if from master then push licenses

  debug("pushLicenseReport")
  sshagent (credentials: ['433ac100-b3c2-4519-b4d6-207c029a103b']) {
    def configStatus = sh(script: "${GIT_SSH_COMMAND} git config --global user.email cwdsdoeteam@osi.ca.gov; git config --global user.name Jenkins",
            returnStatus: true)
    if (configStatus != 0) {
        throw new Exception("git config failed")
    }
    sh(script: "${GIT_SSH_COMMAND} git add license", returnStatus: true)
    sh(script: "${GIT_SSH_COMMAND} git commit -m 'updated license info'", returnStatus: true)
    def pushStatus = sh(script: "${GIT_SSH_COMMAND} git push", returnStatus: true)
    if (pushStatus != 0) {
        throw new Exception("Unable to push licenses")
    }
  }
}
