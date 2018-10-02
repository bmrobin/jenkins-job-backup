package backup.services

import backup.models.BackupJob
import org.apache.http.HttpEntity

abstract class Backup {

    abstract fun save(entity: HttpEntity, jobName: String, url: String)
    abstract fun load(): List<BackupJob>

}
