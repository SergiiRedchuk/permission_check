def BRANCH = 'master'
def CRED_ID = '1db97a1a-6604-4d90-9790-a0fd931af6f4'

@Library('jenk_lib@master')
import gov.ca.cwds.jenkins.LicensingSupport

def licensingSupport = new LicensingSupport(this, BRANCH, CRED_ID)

node('master') {
  stage ('Preparation') {
    git branch: BRANCH, credentialsId: CRED_ID, url: 'git@github.com:SergiiRedchuk/permission_check.git'
  }
  stage ('Generate License Report') {
    licensingSupport.generateLicenseInfo()
  }
  stage ('Push License Report') {
    licensingSupport.pushLicenseReport()
  }
}
