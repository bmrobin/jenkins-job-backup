package backup.utils

import backup.models.BackupConfiguration
import java.util.Base64

fun getCredentialString(configuration: BackupConfiguration): String {
    return Base64
            .getEncoder()
            .encodeToString("${configuration.jenkinsUsername}:${configuration.jenkinsPassword}".toByteArray())
}
