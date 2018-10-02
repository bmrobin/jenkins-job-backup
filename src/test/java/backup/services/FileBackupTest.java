package backup.services;

import backup.models.BackupJob;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

public class FileBackupTest {

    @Test
    public void testLoad() {
        FileBackup backup = new FileBackup("src/test/resources/testJobs/");
        List<BackupJob> jobs = backup.load();
        assertThat(jobs.size(), is(2));
        assertThat(jobs.get(0).getName(), is("test-file-b.txt"));
        assertThat(jobs.get(1).getName(), is("test-file-a.txt"));
    }

}
