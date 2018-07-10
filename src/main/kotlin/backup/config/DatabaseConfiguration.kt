package backup.config

import backup.JenkinsBackup
import backup.models.BackupConfiguration
import backup.services.DatabaseBackup
import backup.utils.JobProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@Profile("database")
@EnableJpaRepositories("backup.persistence")
@PropertySource("classpath:application.properties")
class DatabaseConfiguration {

    @Autowired
    lateinit var configuration: BackupConfiguration

    @Bean
    fun databaseBackup(dbBackup: DatabaseBackup) = CommandLineRunner {
        val jobProcessor = JobProcessor(configuration)
        val jenkinsBackup = JenkinsBackup(dbBackup, jobProcessor)
        jenkinsBackup.backup(configuration)
    }

}
