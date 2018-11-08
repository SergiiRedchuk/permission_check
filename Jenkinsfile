def BRANCH = 'master'

//def CRED_ID = '433ac100-b3c2-4519-b4d6-207c029a103b'
def CRED_ID = 'c2120f6c-4df6-4842-a0d4-d08cac68a6b2'

/*
library identifier: 'jenk_lib@master', retriever: modernSCM(
  [$class: 'GitSCMSource',
   remote: 'git@github.com:SergiiRedchuk/jenk_lib.git',
   credentialsId: CRED_ID])
   */

@Library('jenk_lib@master')
import gov.ca.cwds.jenkins.SshGit
//def lib = library('my-shared-library').com.mycorp.pipeline // preselect the package
//echo useSomeLib(lib.Helper.new(lib.Constants.SOME_TEXT))

def sshGit = new SshGit(CRED_ID)

node('master') {
  //def serverArti = Artifactory.server 'CWDS_DEV'
  //def rtGradle = Artifactory.newGradleBuild()
  stage ('Preparation') {
    git branch: BRANCH, credentialsId: CRED_ID, url: 'git@github.com:SergiiRedchuk/permission_check.git'
    //rtGradle.tool = "Gradle_35"
    //rtGradle.resolver repo: 'repo', server: serverArti
    //rtGradle.useWrapper = false
  }
  stage ('Generate License Report') {
    if ('master' == BRANCH) {
      //buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'deleteLicenses'
      //buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'downloadLicenses'
      //buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'copyLicenses'
      // WARNING: Windows
      //bat 'gradle deleteLicenses downloadLicenses copyLicenses'
      bat './gradlew deleteLicenses downloadLicenses copyLicenses'
    }
  }
  stage ('Push License Report') {
    if ('master' == BRANCH) {
      pushLicenseReport()
    }
  }
}

def pushLicenseReport() {
/*
  sshagent (credentials: ['433ac100-b3c2-4519-b4d6-207c029a103b']) {
    def configStatus = sh(script: "${GIT_SSH_COMMAND} git config --global user.email cwdsdoeteam@osi.ca.gov; git config --global user.name Jenkins",
            returnStatus: true)
    if (configStatus != 0) {
        throw new Exception("git config failed")
    }
    sh(script: "${GIT_SSH_COMMAND} git add license", returnStatus: true)
    sh(script: "${GIT_SSH_COMMAND} git commit -m 'updated license info'", returnStatus: true)
    def pushStatus = sh(script: "${GIT_SSH_COMMAND} git push --set-upstream origin master", returnStatus: true)
    if (pushStatus != 0) {
        throw new Exception("Unable to push licenses")
    }
  }
  */
  sshGit.exec('config --global user.email cwdsdoeteam@osi.ca.gov')
  sshGit.exec('config --global user.name Jenkins')
  sshGit.exec('add license')
  sshGit.exec('git commit -m "updated license info"')
  sshGit.exec('push --set-upstream origin master')
}
