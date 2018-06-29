package backup

import backup.models.BackupConfiguration
import backup.services.DatabaseBackup
import backup.services.FileBackup
import backup.utils.JobProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories("backup")
class Application {

    @Autowired
    lateinit var configuration: BackupConfiguration

    @Bean
    @Profile("database")
    fun databaseBackup(dbBackup: DatabaseBackup) = CommandLineRunner {
        val jobProcessor = JobProcessor(configuration)
        val jenkinsBackup = JenkinsBackup(dbBackup, jobProcessor)
        jenkinsBackup.backup(configuration)
    }

    @Bean
    @Profile("file")
    fun fileBackup() = CommandLineRunner {
        val jobProcessor = JobProcessor(configuration)
        val jenkinsBackup = JenkinsBackup(FileBackup(), jobProcessor)
        jenkinsBackup.backup(configuration)
    }
}

fun main(args: Array<String>) {
    SpringApplication(Application::class.java).run(*args)
}
