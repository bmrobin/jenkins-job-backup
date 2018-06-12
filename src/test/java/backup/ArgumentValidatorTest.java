package backup;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ArgumentValidatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testCredentialString() {
        BackupConfiguration config = new BackupConfiguration("username", "password",
                "", "");
        String credentialString = ArgumentValidatorKt.getCredentialString(config);
        assertThat(credentialString, not(containsString("username")));
        assertThat(credentialString, not(containsString("password")));
    }

    @Test
    public void testValidArguments() {
        String[] args1 = { "", "", "", "" };
        assertTrue(ArgumentValidatorKt.checkProgramArguments(args1));

        String[] args2 = { "", "", "" };
        assertTrue(ArgumentValidatorKt.checkProgramArguments(args2));
    }

    @Test
    public void testNullArguments() {
        expectedException.expect(IllegalArgumentException.class);
        String[] args = null;
        ArgumentValidatorKt.checkProgramArguments(args);
    }

    @Test
    public void testBadArguments() {
        expectedException.expect(IllegalArgumentException.class);
        String[] args = { "one" };
        ArgumentValidatorKt.checkProgramArguments(args);
    }
}
