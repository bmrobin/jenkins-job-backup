package backup.services

import backup.models.BackupJob
import backup.persistence.BackupRepository
import org.apache.http.HttpEntity
import org.apache.http.util.EntityUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DatabaseBackup(val backupRepository: BackupRepository) : Backup() {

    override fun save(entity: HttpEntity, jobName: String, url: String) {
        val job = BackupJob(null, jobName, url, EntityUtils.toByteArray(entity))
        backupRepository.save(job)
        return
    }
}
