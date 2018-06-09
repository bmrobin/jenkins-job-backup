package backup

import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.FileEntity
import org.apache.http.impl.client.HttpClients
import java.io.File
import java.net.URI

fun main(args: Array<String>) {

    val client = HttpClients.createDefault()

    try {
        JobProcessor().getJenkinsJobs().forEach { job ->
            println("Restoring job: ${job.key}")

            val request = HttpPost(URI(job.value))
            request.addHeader("Authorization", "Basic " + getCredentialString(args))
            request.addHeader("Content-Type", ContentType.APPLICATION_XML.toString())
            request.entity = FileEntity(File("jobs/${job.key}.xml"), ContentType.APPLICATION_XML)

            val response = client.execute(request)
            if (response.statusLine.statusCode == 200) {
                println("Restored job: ${job.key}")
            } else {
                println("Error restoring job: ${job.key} - ${response.statusLine}")
            }
        }
    } finally {
        println("Restore job finished")
        client.close()
    }

}
