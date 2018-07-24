package backup.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "backupConfiguration")
class BackupConfiguration {
    lateinit var jenkinsUsername: String
    lateinit var jenkinsPassword: String
    lateinit var jenkinsServerAddress: String
}
