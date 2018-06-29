package backup

import backup.models.BackupConfiguration
import backup.services.Backup
import backup.utils.JobProcessor
import backup.utils.getCredentialString
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.springframework.stereotype.Service
import java.net.URI

@Service
class JenkinsBackup(var backup: Backup, var jobProcessor: JobProcessor) {

    fun backup(configuration: BackupConfiguration) {

        val client = HttpClients.createDefault()

        try {
            jobProcessor.getJenkinsJobs().forEach { job ->
                println("Backing up job: ${job.name}")
                job.url = job.url + "config.xml"
                val request = HttpGet(URI(job.url))
                request.addHeader("Authorization", "Basic " + getCredentialString(configuration))
                val response = client.execute(request)
                if (response.statusLine.statusCode == HttpStatus.SC_OK) {
                    backup.save(response.entity, job.name, job.url)
                } else {
                    println("Error backing up job: ${job.name} - ${response.statusLine}")
                }
            }
        } finally {
            println("Backup job finished")
            client.close()
        }

    }
}
