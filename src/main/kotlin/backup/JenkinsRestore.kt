package backup

import backup.config.BackupConfiguration
import backup.services.Backup
import backup.utils.getCredentialString
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.FileEntity
import org.apache.http.impl.client.HttpClients
import org.springframework.stereotype.Service
import java.io.File
import java.net.URI

@Service
class JenkinsRestore(var backup: Backup) {

    fun restore(configuration: BackupConfiguration) {

        val client = HttpClients.createDefault()

        try {
            backup.load().forEach { job ->
                println("Restoring job: ${job.name}")

                val request = HttpPost(URI(job.url))
                request.addHeader("Authorization", "Basic " + getCredentialString(configuration))
                request.addHeader("Content-Type", ContentType.APPLICATION_XML.toString())
//                request.entity = FileEntity(File(job.file), ContentType.APPLICATION_XML)

                val response = client.execute(request)
                if (response.statusLine.statusCode == 200) {
                    println("Restored job: ${job.name}")
                } else {
                    println("Error restoring job: ${job.name} - ${response.statusLine}")
                }
            }
        } finally {
            println("Restore job finished")
            client.close()
        }
    }
}
