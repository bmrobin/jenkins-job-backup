package backup

import java.util.Base64

fun getCredentialString(args: Array<String>): String {
    if (args.size != 2) {
        throw IllegalArgumentException("Username and password required in command line argument positions 1 and 2 respectively")
    }
    val username = args[0]
    val password = args[1]
    return Base64.getEncoder().encodeToString("$username:$password".toByteArray())
}
