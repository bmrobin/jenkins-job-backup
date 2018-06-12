package backup

import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import java.io.File
import java.net.URI

fun main(args: Array<String>) {

    checkProgramArguments(args)

    var it = BackupConfiguration(args[0], args[1], args[2])
    if (args.size == 4) {
        it = BackupConfiguration(args[0], args[1], args[2], args[3])
    }
    val configuration = it

    val client = HttpClients.createDefault()

    try {
        JobProcessor(configuration.jenkinsServerAddress)
                .getJenkinsJobs()
                .forEach { job ->
                    println("Backing up job: ${job.key}")
                    val request = HttpGet(URI(job.value))
                    request.addHeader("Authorization", "Basic " + getCredentialString(configuration))
                    val response = client.execute(request)
                    if (response.statusLine.statusCode == HttpStatus.SC_OK) {
                        val file = File("${configuration.backupFileLocation}/${job.key}.xml")
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
