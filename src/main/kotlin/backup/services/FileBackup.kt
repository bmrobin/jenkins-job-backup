package backup.services

import backup.models.BackupJob
import org.apache.http.HttpEntity
import org.apache.http.util.EntityUtils
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.io.File

@Service
@Profile("file")
class FileBackup(val backupFileLocation: String = "jobs") : Backup() {

    override fun save(entity: HttpEntity, jobName: String, url: String) {
        val file = File("$backupFileLocation/$jobName.xml")
        file.createNewFile()
        file.writeText(EntityUtils.toString(entity), Charsets.UTF_8)
        return
    }

    override fun load(): List<BackupJob> {
        val jobList: MutableList<BackupJob> = mutableListOf()

        File(backupFileLocation)
                .walk(FileWalkDirection.TOP_DOWN)
                .onEnter { file ->
                    file.exists()
                }
                .iterator()
                .forEach { file ->
                    if (!file.isHidden && !file.isDirectory) {
                        println(file.name)
                        val job = BackupJob(null, file.name, "", File(file.toURI()).readBytes())
                        jobList.add(job)
                    }
                }

        return jobList
    }
}
