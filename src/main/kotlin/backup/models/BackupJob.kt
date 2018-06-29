package backup.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*
import javax.persistence.Basic
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.Table

@Entity
@Table(name = "backups")
@JsonIgnoreProperties(ignoreUnknown = true)
data class BackupJob (
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @JsonIgnore
        val id: Long? = null,
        val name: String,
        var url: String,
        @JsonIgnore
        @Lob
        @Basic
        val file: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BackupJob

        if (id != other.id) return false
        if (name != other.name) return false
        if (url != other.url) return false
        if (!Arrays.equals(file, other.file)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id!!.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + Arrays.hashCode(file)
        return result
    }

}
