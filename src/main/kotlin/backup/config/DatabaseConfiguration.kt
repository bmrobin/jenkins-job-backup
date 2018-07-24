package backup.config

import backup.JenkinsBackup
import backup.persistence.BackupRepository
import backup.services.DatabaseBackup
import backup.utils.JobProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.sql.DataSource


@Configuration
@Profile("database")
@PropertySource("classpath:application.properties")
class DatabaseConfiguration {

    @Autowired
    lateinit var configuration: BackupConfiguration

    @Autowired
    lateinit var backupRepository: BackupRepository

//    @Autowired
//    lateinit var env: Environment
//
//    @Bean
//    fun dataSource(): DataSource {
//        return DataSourceBuilder
//                .create()
//                .driverClassName(env.getProperty("spring.datasource.driver-class-name"))
//                .url(env.getProperty("spring.datasource.url"))
//                .username(env.getProperty("spring.datasource.username"))
//                .password(env.getProperty("spring.datasource.password"))
//                .build()
//    }

    @Bean
    fun dbBackup(): DatabaseBackup {
        return DatabaseBackup(backupRepository)
    }

    @Bean
    fun databaseBackup() = CommandLineRunner {
        val jobProcessor = JobProcessor(configuration)
        val jenkinsBackup = JenkinsBackup(dbBackup(), jobProcessor)
        jenkinsBackup.backup(configuration)
    }

}
