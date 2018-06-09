package backup;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JobProcessorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testJobsProcessor() {
        JobProcessor processor = new JobProcessor("http://no-op.org");
        Map<String, String> jobs = processor.getJenkinsJobs();
        assertThat(jobs.size(), is(3));
        assertThat(jobs.get("an-example-job-1"), is("http://no-op.org/job/an-example-job-1/config.xml"));
        assertThat(jobs.get("an-example-job-2"), is("http://no-op.org/job/an-example-job-2/config.xml"));
        assertThat(jobs.get("an-example-job-3"), is("http://no-op.org/job/an-example-job-3/config.xml"));
    }

    @Test
    public void testJobsProcessorFails() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter specified as non-null is null");
        expectedException.expectMessage("parameter jenkinsAddress");
        JobProcessor processor = new JobProcessor(null);
        processor.getJenkinsJobs();
    }
}
