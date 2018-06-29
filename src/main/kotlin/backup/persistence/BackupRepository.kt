package backup.persistence

import backup.models.BackupJob
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BackupRepository : CrudRepository<BackupJob, Long>
