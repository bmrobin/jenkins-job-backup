//package backup
//
//import org.apache.http.client.methods.HttpPost
//import org.apache.http.entity.ContentType
//import org.apache.http.entity.FileEntity
//import org.apache.http.impl.client.HttpClients
//import java.io.File
//import java.net.URI
//
//fun main(args: Array<String>) {
//
//    checkProgramArguments(args)
//
//    var it = BackupConfiguration(args[0], args[1], args[2])
//    if (args.size == 4) {
//        it = BackupConfiguration(args[0], args[1], args[2], args[3])
//    }
//    val configuration = it
//
//    val client = HttpClients.createDefault()
//
//    try {
//        JobProcessor(configuration.jenkinsServerAddress)
//                .getJenkinsJobs()
//                .forEach { job ->
//                    println("Restoring job: ${job.key}")
//
//                    val request = HttpPost(URI(job.value))
//                    request.addHeader("Authorization", "Basic " + getCredentialString(configuration))
//                    request.addHeader("Content-Type", ContentType.APPLICATION_XML.toString())
//                    request.entity = FileEntity(File("${configuration.backupFileLocation}/${job.key}.xml"), ContentType.APPLICATION_XML)
//
//                    val response = client.execute(request)
//                    if (response.statusLine.statusCode == 200) {
//                        println("Restored job: ${job.key}")
//                    } else {
//                        println("Error restoring job: ${job.key} - ${response.statusLine}")
//                    }
//                }
//    } finally {
//        println("Restore job finished")
//        client.close()
//    }
//
//}
