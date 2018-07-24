//package backup
//
//import backup.config.BackupConfiguration
//import backup.utils.JobProcessor
//import org.apache.http.client.methods.HttpPost
//import org.apache.http.entity.ContentType
//import org.apache.http.entity.FileEntity
//import org.apache.http.impl.client.HttpClients
//import org.springframework.stereotype.Service
//import java.io.File
//import java.net.URI
//
//@Service
//class JenkinsRestore(var backup: Backup, var jobProcessor: JobProcessor) {
//
//    fun restore(configuration: BackupConfiguration) {
//
//        val client = HttpClients.createDefault()
//
//        try {
//            JobProcessor(configuration.jenkinsServerAddress)
//                    .getJenkinsJobs()
//                    .forEach { job ->
//                        println("Restoring job: ${job.key}")
//
//                        val request = HttpPost(URI(job.value))
//                        request.addHeader("Authorization", "Basic " + getCredentialString(configuration))
//                        request.addHeader("Content-Type", ContentType.APPLICATION_XML.toString())
//                        request.entity = FileEntity(File("${configuration.backupFileLocation}/${job.key}.xml"), ContentType.APPLICATION_XML)
//
//                        val response = client.execute(request)
//                        if (response.statusLine.statusCode == 200) {
//                            println("Restored job: ${job.key}")
//                        } else {
//                            println("Error restoring job: ${job.key} - ${response.statusLine}")
//                        }
//                    }
//        } finally {
//            println("Restore job finished")
//            client.close()
//        }
//    }
//}
