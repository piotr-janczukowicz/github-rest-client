package io.github.piotr_janczukowicz.github.api.agents;

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
class GitHubAgentsApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubAgentsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void secrets_shouldReturnApi_whenConstructed() {
        final GitHubAgentsApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubAgentsApi(TestClients.caller(client));
        }

        final GitHubAgentsSecretsApi secrets = api.secrets();

        assertThat(secrets).isNotNull();
    }

    @Test
    void variables_shouldReturnApi_whenConstructed() {
        final GitHubAgentsApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubAgentsApi(TestClients.caller(client));
        }

        final GitHubAgentsVariablesApi variables = api.variables();

        assertThat(variables).isNotNull();
    }
}
