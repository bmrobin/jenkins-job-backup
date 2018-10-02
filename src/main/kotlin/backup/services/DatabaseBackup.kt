package backup.services

import backup.models.BackupJob
import backup.persistence.BackupRepository
import org.apache.http.HttpEntity
import org.apache.http.util.EntityUtils
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
@Profile("database")
class DatabaseBackup(val backupRepository: BackupRepository) : Backup() {

    override fun save(entity: HttpEntity, jobName: String, url: String) {
        val job = BackupJob(null, jobName, url, EntityUtils.toByteArray(entity))
        backupRepository.save(job)
        return
    }

    override fun load(): List<BackupJob> {
        var jobList: List<BackupJob> = emptyList()

        try {
            jobList = backupRepository.findAll().toList()
        } catch (e: Exception) {
            println("woops")
        }

        return jobList
    }
}
