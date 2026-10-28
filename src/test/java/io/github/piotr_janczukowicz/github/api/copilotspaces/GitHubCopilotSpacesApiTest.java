package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotSpacesApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotSpacesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void collaborators_shouldReturnApi_whenConstructed() {
        final GitHubCopilotSpacesApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubCopilotSpacesApi(TestClients.caller(client));
        }

        final GitHubCopilotSpacesCollaboratorsApi collaborators = api.collaborators();

        assertThat(collaborators).isNotNull();
    }

    @Test
    void copilotSpace_shouldReturnApi_whenConstructed() {
        final GitHubCopilotSpacesApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubCopilotSpacesApi(TestClients.caller(client));
        }

        final GitHubCopilotSpaceApi copilotSpace = api.copilotSpace();

        assertThat(copilotSpace).isNotNull();
    }

    @Test
    void resources_shouldReturnApi_whenConstructed() {
        final GitHubCopilotSpacesApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubCopilotSpacesApi(TestClients.caller(client));
        }

        final GitHubCopilotSpacesResourcesApi resources = api.resources();

        assertThat(resources).isNotNull();
    }
}
