package backup.utils;

import backup.config.BackupConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ArgumentValidatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testCredentialString() {
        BackupConfiguration config = new BackupConfiguration();
        config.setJenkinsUsername("username");
        config.setJenkinsPassword("password");
        config.setJenkinsServerAddress("");
        assertThat(ArgumentValidatorKt.getCredentialString(config), is("dXNlcm5hbWU6cGFzc3dvcmQ="));
    }
}
