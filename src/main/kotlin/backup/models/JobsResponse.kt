package backup.models

import backup.models.BackupJob
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class JobsResponse(val jobs: List<BackupJob>)
