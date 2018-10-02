package backup.services;

import backup.models.BackupJob;
import backup.persistence.BackupRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class DatabaseBackupTest {

    @Autowired
    private BackupRepository backupRepository;

    @Before
    public void setup() {
        backupRepository.save(new BackupJob(null, "job-1", "http://localhost:3030/job-1", "this".getBytes()));
        backupRepository.save(new BackupJob(null, "job-2", "http://localhost:3030/job-2", "that".getBytes()));
    }

    @Test
    public void testLoad() {
        DatabaseBackup backup = new DatabaseBackup(backupRepository);
        List<BackupJob> jobs = backup.load();
        assertThat(jobs.size(), is(2));
    }
}
