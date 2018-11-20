@Library('jenkins-pipeline-utils') _

def SSH_CRED_ID = '1db97a1a-6604-4d90-9790-a0fd931af6f4'

node('tpt2-slave') {
    newTag = '';
    try {
        stage('Preparation') {
            try {
                git branch: 'master', credentialsId: SSH_CRED_ID, url: 'git@github.com:SergiiRedchuk/permission_check.git'

                if (pullRequestHasVersioningLabels()) {
                    newTag = newSemVer()
                    debug("Increment Tag: newTag: ${newTag}")
                } else {
                    newTag = newSemVer('patch')
                    debug("Increment Tag: No label going with old version: ${newTag}")
                }
            }
            catch(Exception ex) {
                error "[ERROR] ${ex}"
            }
        }
    } catch (Exception e) {
        errorcode = e
        currentBuild.result = "FAIL"
        throw e;
    } finally {
        cleanWs()
    }
}

def debug(String str) {
    echo "[DEBUG] ${str}"
}

def pullRequestHasVersioningLabels() {
    def prEvent = readJSON text: env.pull_request_event
    def labels = []
    prEvent.labels.each{ labels << it.name.toUpperCase() }
    return labels.contains('MAJOR') || labels.contains('MINOR') || labels.contains('PATCH')
}
