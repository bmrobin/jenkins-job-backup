package backup.utils

import backup.models.BackupConfiguration
import backup.models.BackupJob
import backup.models.JobsResponse
import backup.utils.getCredentialString
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import java.net.URI

class JobProcessor constructor(backupConfiguration: BackupConfiguration) {

    private val configuration = backupConfiguration

    fun getJenkinsJobs(): List<BackupJob> {

        val objectMapper = ObjectMapper().registerKotlinModule()
        val client = HttpClients.createDefault()
        try {
            val request = HttpGet(URI(configuration.jenkinsServerAddress + "/api/json?tree=jobs[name,url]"))
            request.addHeader("Authorization", "Basic " + getCredentialString(configuration))
            val response = client.execute(request)

            if (response.statusLine.statusCode == HttpStatus.SC_OK) {
                val jobsResponseWrapper: JobsResponse = objectMapper.readValue(response.entity.content)
                return jobsResponseWrapper.jobs
            }
        } finally {
            client.close()
        }
        return emptyList()
    }

}
