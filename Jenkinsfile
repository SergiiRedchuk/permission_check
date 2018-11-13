def BRANCH = 'master'
def CRED_ID = '1db97a1a-6604-4d90-9790-a0fd931af6f4'

@Library('jenk_lib@master')
import gov.ca.cwds.jenkins.* // todo .LicensingSupport

def licensingSupport = new LicensingSupport(this, BRANCH, CRED_ID)

node('master') {
  stage ('Preparation') {
    git branch: BRANCH, credentialsId: CRED_ID, url: 'git@github.com:SergiiRedchuk/permission_check.git'
  }
  stage ('Generate License Report') {
    licensingSupport.generateLicenseInfo()
  }
  stage ('Push License Report') {
    if ('master' == BRANCH) {
      pushLicenseReport(new SshAgent(this, CRED_ID))
    }
  }
}

def pushLicenseReport(sshAgent) {
  sshAgent.exec('git config --global user.email cwdsdoeteam@osi.ca.gov')
  /* todo
  def configStatus = sh(script: "${GIT_SSH_COMMAND} git config ...
  if (configStatus != 0) {
          throw new Exception("git config failed")
      }
  */
  sshAgent.exec('git config --global user.name Jenkins')
  sshAgent.exec('git add license')
  sshAgent.exec('git commit -m "updated license info"')
  sshAgent.exec('git push --set-upstream origin master')
  /* todo
  def pushStatus = sh(script: "${GIT_SSH_COMMAND} git push --set-upstream origin master", returnStatus: true)
      if (pushStatus != 0) {
          throw new Exception("Unable to push licenses")
      }
      */
}
