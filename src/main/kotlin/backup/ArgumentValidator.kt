package backup

import java.util.Base64

fun getCredentialString(configuration: BackupConfiguration): String {
    return Base64
            .getEncoder()
            .encodeToString("${configuration.jenkinsUsername}:${configuration.jenkinsPassword}".toByteArray())
}

fun checkProgramArguments(args: Array<String>): Boolean {
    if (args.size < 3 || args.size > 4 ) {
        val text = """
            |
            |Provide the following command line arguments into arg[]
            |- Username: username for Jenkins server
            |- Password: password for Jenkins user account
            |- Host: server address of the Jenkins server, including port if necessary
            |- Job storage directory (optional): a pre-existing directory to store Jenkins job XML files.
            |   "Default value is ./jobs if no value specified
                """
        throw IllegalArgumentException(text.trimMargin())
    }
    return true
}
