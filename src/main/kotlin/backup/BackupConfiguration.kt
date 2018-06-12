package backup

data class BackupConfiguration(val jenkinsUsername: String,
                               val jenkinsPassword: String,
                               val jenkinsServerAddress: String,
                               val backupFileLocation: String = "jobs")
