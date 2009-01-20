// This script installs everything (see plugin doc for details)

grailsHome = Ant.project.properties.'environment.GRAILS_HOME'
includeTargets << new File("${grailsHome}/scripts/Init.groovy")

def defaultsDir = "${starkSecurityPluginDir}/src/defaults"
def domainDir = "${basedir}/grails-app/domain"

// Install the domain classes User and Role
Ant.copy(file: "${defaultsDir}/_User.groovy", tofile: "${domainDir}/User.groovy")
Ant.copy(file: "${defaultsDir}/_Role.groovy", tofile: "${domainDir}/Role.groovy")

// Install the DAO/UserDetails service
Ant.copy(file: "${defaultsDir}/_UserLookupService.groovy", tofile: "${basedir}/grails-app/services/UserLookupService.groovy")

// Install the access controller
Ant.copy(file: "${defaultsDir}/_AccessController.groovy", tofile: "${basedir}/grails-app/controllers/AccessController.groovy")

// Install the login view
Ant.mkdir(dir: "${basedir}/grails-app/views/access")
Ant.copy(file: "${defaultsDir}/login.gsp", tofile: "${basedir}/grails-app/views/access/login.gsp")

// Install the config properties file
Ant.copy(file: "${defaultsDir}/_StarkSecurityConfig.groovy", tofile: "${basedir}/grails-app/conf/StarkSecurityConfig.groovy")
