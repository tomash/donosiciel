// This script installs everything except the default DAO authentication scheme.

grailsHome = Ant.project.properties.'environment.GRAILS_HOME'
includeTargets << new File("${grailsHome}/scripts/Init.groovy")

def defaultsDir = "${starkSecurityPluginDir}/src/defaults"
def domainDir = "${basedir}/grails-app/domain"

//	 Install the domain class Role
Ant.copy(file: "${defaultsDir}/_Role.groovy", tofile: "${domainDir}/Role.groovy")

//	 Install the access controller
Ant.copy(file: "${defaultsDir}/_AccessController.groovy", tofile: "${basedir}/grails-app/controllers/AccessController.groovy")

//	 Install the login view
Ant.mkdir(dir: "${basedir}/grails-app/views/access")
Ant.copy(file: "${defaultsDir}/login.gsp", tofile: "${basedir}/grails-app/views/access/login.gsp")

//	 Install the config properties file
Ant.copy(file: "${defaultsDir}/_StarkSecurityConfigWithoutDao.groovy", tofile: "${basedir}/grails-app/conf/StarkSecurityConfig.groovy")
