package backup

import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import java.io.File
import java.net.URI
import java.util.Base64

fun main(args: Array<String>) {

    val client = HttpClients.createDefault()

    try {
        JobProcessor().getJenkinsJobs().forEach { job ->
            println("Backing up job: ${job.key}")
            val request = HttpGet(URI(job.value))
            request.addHeader("Authorization", "Basic " + getCredentialString(args))
            val response = client.execute(request)
            if (response.statusLine.statusCode == HttpStatus.SC_OK) {
                val file = File("jobs/${job.key}.xml")
                file.createNewFile()
                file.writeText(EntityUtils.toString(response.entity), Charsets.UTF_8)
            } else {
                println("Error backing up job: ${job.key} - ${response.statusLine}")
            }
        }
    } finally {
        println("Backup job finished")
        client.close()
    }

}

fun getCredentialString(args: Array<String>): String {
    if (args.size != 2) {
        throw IllegalArgumentException("Username and password required in command line argument positions 1 and 2 respectively")
    }
    val username = args[0]
    val password = args[1]
    return Base64.getEncoder().encodeToString("$username:$password".toByteArray())
}
