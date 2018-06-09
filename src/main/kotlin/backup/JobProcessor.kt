package backup

import java.util.stream.Collectors

class JobProcessor constructor(jenkinsAddress: String) {

    private val address = jenkinsAddress

    /**
     * We need this in a dedicated class so that we can use the class loader to get the resource file.
     */
    fun loadJobs(): List<String> {
        return this.javaClass.getResource("/jobs.txt").readText().split("\n")
    }

    fun getJenkinsJobs(): Map<String, String> {

        return this.loadJobs()
                .stream()
                .filter { it -> it.isNotBlank() }
                .collect(Collectors.toMap({ key -> key}, { value ->
                    val normalized = value.replace(" ", "%20")
                    "$address/job/$normalized/config.xml"
                }))
    }

}
