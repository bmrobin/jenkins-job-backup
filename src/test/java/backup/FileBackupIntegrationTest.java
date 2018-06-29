package backup;

import backup.models.BackupConfiguration;
import backup.models.BackupJob;
import backup.services.FileBackup;
import backup.utils.JobProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

@ActiveProfiles("file")
@RunWith(SpringRunner.class)
public class FileBackupIntegrationTest {

    private static final FileBackup fileBackup = new FileBackup("src/test/resources/testJobs");

    private static final List<Path> paths = Arrays.asList(
            Paths.get("src/test/resources/testJobs/backup-1.xml"),
            Paths.get("src/test/resources/testJobs/backup-2.xml"));

    private ClientAndServer mockServer;

    @Mock
    private JobProcessor mockJobProcessor;

    @Before
    public void setup() {
        cleanup();
        mockServer = startClientAndServer(3030);
        mockServer.when(HttpRequest
                .request()
                .withPath("/job/.*/config.xml")
                .withHeader("Authorization", "Basic .*"))
                .respond(HttpResponse.response("hello"));
    }

    @After
    public void teardown() {
        cleanup();
        mockServer.stop();
    }

    @Test
    public void testFileBackup() {
        BackupConfiguration configuration = new BackupConfiguration();
        configuration.setJenkinsUsername("user");
        configuration.setJenkinsPassword("password");
        configuration.setJenkinsServerAddress("http://localhost:3030");

        BackupJob one = new BackupJob(11L, "backup-1", "http://localhost:3030/job/example-1/", null);
        BackupJob two = new BackupJob(22L, "backup-2", "http://localhost:3030/job/example-2/", null);
        List<BackupJob> jobs = new ArrayList<>();
        jobs.add(one);
        jobs.add(two);
        when(mockJobProcessor.getJenkinsJobs()).thenReturn(jobs);

        JenkinsBackup jenkinsBackup = new JenkinsBackup(fileBackup, mockJobProcessor);
        jenkinsBackup.backup(configuration);

        paths.forEach(path -> assertThat(Files.exists(path), is(true)));
    }

    private void cleanup() {
        paths.forEach(path -> {
            try {
                if (Files.exists(path)) {
                    Files.delete(path);
                }
            } catch (Exception ex) {
                System.out.println("failed to delete test file in setup");
                ex.printStackTrace();
            }
        });
    }
}
