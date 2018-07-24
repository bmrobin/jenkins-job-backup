package backup.config

import backup.JenkinsBackup
import backup.services.FileBackup
import backup.utils.JobProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource

@Configuration
@Profile("file")
@PropertySource("classpath:application.properties")
class FileConfiguration {

    @Autowired
    lateinit var configuration: BackupConfiguration

    @Bean
    fun fileBackup(): FileBackup {
        return FileBackup()
    }

    @Bean
    fun backup() = CommandLineRunner {
        val jobProcessor = JobProcessor(configuration)
        val jenkinsBackup = JenkinsBackup(fileBackup(), jobProcessor)
        jenkinsBackup.backup(configuration)
    }

}
