package io.github.piotr_janczukowicz.github.api.copilot;

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
class GitHubCopilotApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void copilotCloudAgentManagement_shouldReturnApi_whenConstructed() {
        final GitHubCopilotApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubCopilotApi(TestClients.caller(client));
        }

        assertThat(api.copilotCloudAgentManagement()).isNotNull();
    }

    @Test
    void copilotCodingAgentManagement_shouldReturnApi_whenConstructed() {
        final GitHubCopilotApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubCopilotApi(TestClients.caller(client));
        }

        assertThat(api.copilotCodingAgentManagement()).isNotNull();
    }

    @Test
    void copilotContentExclusionManagement_shouldReturnApi_whenConstructed() {
        final GitHubCopilotApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubCopilotApi(TestClients.caller(client));
        }

        assertThat(api.copilotContentExclusionManagement()).isNotNull();
    }

    @Test
    void copilotUsageMetrics_shouldReturnApi_whenConstructed() {
        final GitHubCopilotApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubCopilotApi(TestClients.caller(client));
        }

        assertThat(api.copilotUsageMetrics()).isNotNull();
    }

    @Test
    void copilotUserManagement_shouldReturnApi_whenConstructed() {
        final GitHubCopilotApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubCopilotApi(TestClients.caller(client));
        }

        assertThat(api.copilotUserManagement()).isNotNull();
    }

    @Test
    void copilotCustomAgents_shouldReturnApi_whenConstructed() {
        final GitHubCopilotApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubCopilotApi(TestClients.caller(client));
        }

        assertThat(api.copilotCustomAgents()).isNotNull();
    }

    @Test
    void copilotModelPolicyManagement_shouldReturnApi_whenConstructed() {
        final GitHubCopilotApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubCopilotApi(TestClients.caller(client));
        }

        assertThat(api.copilotModelPolicyManagement()).isNotNull();
    }
}
