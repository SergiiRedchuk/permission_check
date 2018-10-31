// Used to avoid known_hosts addition, which would require each machine to have GitHub added in advance (maybe should do?)
GIT_SSH_COMMAND = 'GIT_SSH_COMMAND="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no"'
def BRANCH = 'master'

node('linux') {
  def serverArti = Artifactory.server 'CWDS_DEV'
  def rtGradle = Artifactory.newGradleBuild()
  stage ('Preparation') {
    git branch: BRANCH, credentialsId: '433ac100-b3c2-4519-b4d6-207c029a103b', url: 'git@github.com:kaver79/permission_check.git'
    //checkout([$class: 'GitSCM', branches: [[name: BRANCH], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '433ac100-b3c2-4519-b4d6-207c029a103b', url: 'git@github.com:kaver79/permission_check.git']]])
    rtGradle.tool = "Gradle_35"
    rtGradle.resolver repo: 'repo', server: serverArti
    rtGradle.useWrapper = false
  }
  stage ('Generate License Report') {
    if ('master' == BRANCH) {
      buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'downloadLicenses'
    }
  }
  stage ('Push License Report') {
    if ('master' == BRANCH) {
      // todo test repeated
      org.apache.commons.io.FileUtils.copyDirectoryToDirectory(
        new File("${WORKSPACE}/build/reports/license"), new File(${WORKSPACE}))
      pushLicenseReport()
    }
  }
}

def pushLicenseReport() {
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
