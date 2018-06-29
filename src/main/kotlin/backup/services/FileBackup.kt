package backup.services

import org.apache.http.HttpEntity
import org.apache.http.util.EntityUtils
import org.springframework.stereotype.Service
import java.io.File

@Service
class FileBackup(private val backupFileLocation: String = "jobs") : Backup() {

    override fun save(entity: HttpEntity, jobName: String, url: String) {
        val file = File("$backupFileLocation/$jobName.xml")
        file.createNewFile()
        file.writeText(EntityUtils.toString(entity), Charsets.UTF_8)
        return
    }

}
