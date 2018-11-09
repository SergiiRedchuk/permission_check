def BRANCH = 'master'

def CRED_ID = '1db97a1a-6604-4d90-9790-a0fd931af6f4'

@Library('jenk_lib@master')
import gov.ca.cwds.jenkins.SshAgent

node('master') {
  stage ('Preparation') {
    git branch: BRANCH, credentialsId: CRED_ID, url: 'git@github.com:SergiiRedchuk/permission_check.git'
  }
  stage ('Generate License Report') {
    if ('master' == BRANCH) {
      sh './gradlew deleteLicenses downloadLicenses copyLicenses'
    }
  }
  stage ('Push License Report') {
    if ('master' == BRANCH) {
      pushLicenseReport(new SshAgent(this, CRED_ID))
    }
  }
}

def pushLicenseReport(sshAgent) {
  sshAgent.exec('git config --global user.email cwdsdoeteam@osi.ca.gov')
  sshAgent.exec('git config --global user.name Jenkins')
  sshAgent.exec('git add license')
  sshAgent.exec('git commit -m "updated license info"')
  sshAgent.exec('git push --set-upstream origin master')
}
