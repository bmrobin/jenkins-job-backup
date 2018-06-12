package backup;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.junit.Assert.assertThat;

public class BackupIntegrationTest {

    private static final List<Path> paths = Arrays.asList(
            Paths.get("src/test/resources/an-example-job-1.xml"),
            Paths.get("src/test/resources/an-example-job-2.xml"),
            Paths.get("src/test/resources/an-example-job-3.xml"));

    private ClientAndServer mockServer;

    @Before
    public void setup() {
        cleanup();
        mockServer = startClientAndServer(8080);
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
    public void test() {
        String[] args = {"user", "password", "http://localhost:8080", "src/test/resources"};
        BackupKt.main(args);
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
