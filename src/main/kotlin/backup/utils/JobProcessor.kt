package backup.utils

import backup.config.BackupConfiguration
import backup.models.BackupJob
import backup.models.JobsResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.springframework.stereotype.Component
import java.net.URI

@Component
class JobProcessor(var backupConfiguration: BackupConfiguration) {

    fun getJenkinsJobs(): List<BackupJob> {

        val objectMapper = ObjectMapper().registerKotlinModule()
        val httpClient = HttpClients.createDefault()
        httpClient.use { client ->
            val request = HttpGet(URI(backupConfiguration.jenkinsServerAddress + "/api/json?tree=jobs[name,url]"))
            request.addHeader("Authorization", "Basic " + getCredentialString(backupConfiguration))
            val response = client.execute(request)

            if (response.statusLine.statusCode == HttpStatus.SC_OK) {
                val jobsResponseWrapper: JobsResponse = objectMapper.readValue(response.entity.content)
                return jobsResponseWrapper.jobs
            }
        }
        return emptyList()
    }

}
