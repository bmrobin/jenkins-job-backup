package backup

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories("backup.persistence")
class Application

fun main(args: Array<String>) {
    SpringApplication(Application::class.java).run(*args)
}
