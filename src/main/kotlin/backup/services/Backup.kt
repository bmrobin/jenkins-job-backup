package backup.services

import org.apache.http.HttpEntity

abstract class Backup {

    abstract fun save(entity: HttpEntity, jobName: String, url: String)

}
