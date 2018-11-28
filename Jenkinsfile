def BRANCH = 'master'
def SSH_CRED_ID = '1db97a1a-6604-4d90-9790-a0fd931af6f4'

//@Library('jenk_lib@master') _
@Library('jenkins-pipeline-utils@FIT-229') _
//import gov.ca.cwds.jenkins.licensing.LicenseReportUpdater

class RuntimeGradle {
  def pipeline

  RuntimeGradle(pipeline) {
    this.pipeline = pipeline
  }

  def run(map) {
    pipeline.echo("*************************************")
    pipeline.echo(map)
  }
}

node('master') {
  stage ('Preparation') {
    git branch: BRANCH, credentialsId: SSH_CRED_ID, url: 'git@github.com:SergiiRedchuk/permission_check.git'
  }

  /*
  stage('Update License Report') {
    updateLicenseReport(BRANCH, SSH_CRED_ID)
  }
  */

  stage('Update License Report') {
    def rtGradle = new RuntimeGradle(this)
    updateLicenseReport(BRANCH, SSH_CRED_ID, [runtimeGradle: rtGradle])
  }

  cleanWs()
}
