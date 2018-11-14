def BRANCH = 'master'
def SSH_CRED_ID = '1db97a1a-6604-4d90-9790-a0fd931af6f4'

@Library('jenk_lib@master') _

def sshAgent = new gov.ca.cwds.jenkins.SshAgent(this, SSH_CRED_ID)
def licensingSupport = new gov.ca.cwds.jenkins.licensing.LicensingSupport(this, BRANCH, sshAgent)

node('master') {
  stage ('Preparation') {
    git branch: BRANCH, credentialsId: SSH_CRED_ID, url: 'git@github.com:SergiiRedchuk/permission_check.git'
  }
  /*
  stage ('Generate License Report') {
    licensingSupport.generateLicenseReport()
  }
  stage ('Push License Report') {
    licensingSupport.pushLicenseReport()
  }
  */
  stage ('Generate and Push License Report') {
    licensingSupport.generateAndPushLicenseReport()
  }
}
