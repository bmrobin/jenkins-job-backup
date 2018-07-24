package backup.utils;

import backup.config.BackupConfiguration;
import backup.models.BackupJob;
import kotlin.text.Charsets;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.util.List;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class JobProcessorTest {

    private ClientAndServer mockServer;

    @Before
    public void setup() {
        mockServer = startClientAndServer(3030);
        mockServer.when(HttpRequest
                .request()
                .withPath("/api/json")
                .withQueryStringParameter("tree", "jobs[name,url]")
                .withHeader("Authorization", "Basic .*"))
                .respond(HttpResponse
                        .response()
                        .withBody(jobsResponseMock()));
    }

    @After
    public void teardown() {
        mockServer.stop();
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testJobsProcessor() {
        BackupConfiguration configuration = new BackupConfiguration();
        configuration.setJenkinsServerAddress("http://localhost:3030");
        configuration.setJenkinsUsername("user");
        configuration.setJenkinsPassword("password");

        JobProcessor processor = new JobProcessor(configuration);
        List<BackupJob> jobs = processor.getJenkinsJobs();

        assertThat(jobs.size(), is(2));
        assertThat(jobs.get(0).getName(), is("example-1"));
        assertThat(jobs.get(0).getUrl(), is("https://jenkins.dummy/job/example-1/"));
        assertThat(jobs.get(1).getName(), is("example-2"));
        assertThat(jobs.get(1).getUrl(), is("https://jenkins.dummy/job/example-2/"));
    }

    @Test
    public void testJobsProcessorFails() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter specified as non-null is null");
        expectedException.expectMessage("parameter backupConfiguration");
        JobProcessor processor = new JobProcessor(null);
        processor.getJenkinsJobs();
    }

    private String jobsResponseMock() {
        try {
            return IOUtils.resourceToString("/jobsResponse.json", Charsets.UTF_8);
        } catch (Exception e) {
            fail();
            return null;
        }
    }
}
