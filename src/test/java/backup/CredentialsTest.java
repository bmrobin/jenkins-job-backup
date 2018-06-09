package backup;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class CredentialsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testCredentialString() {
        String[] args = { "username", "P@ssw0rd!" };
        String credentialString = CredentialsKt.getCredentialString(args);
        assertThat(credentialString, not(containsString("username")));
        assertThat(credentialString, not(containsString("P@ssw0rd!")));
    }

    @Test
    public void testNullArguments() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Parameter specified as non-null is null");
        expectedException.expectMessage("parameter args");
        String[] args = null;
        CredentialsKt.getCredentialString(args);
    }

    @Test
    public void testBadArguments() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Username and password required in command line argument positions 1 and 2 respectively");
        String[] args = { "one" };
        CredentialsKt.getCredentialString(args);
    }
}
