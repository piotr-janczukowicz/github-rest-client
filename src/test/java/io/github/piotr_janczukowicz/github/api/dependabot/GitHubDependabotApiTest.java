package io.github.piotr_janczukowicz.github.api.dependabot;

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
class GitHubDependabotApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDependabotApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void alerts_shouldReturnApi_whenConstructed() {
        final GitHubDependabotApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubDependabotApi(TestClients.caller(client));
        }

        final GitHubDependabotAlertsApi alerts = api.alerts();

        assertThat(alerts).isNotNull();
    }

    @Test
    void repositoryAccess_shouldReturnApi_whenConstructed() {
        final GitHubDependabotApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubDependabotApi(TestClients.caller(client));
        }

        final GitHubDependabotRepositoryAccessApi repositoryAccess = api.repositoryAccess();

        assertThat(repositoryAccess).isNotNull();
    }

    @Test
    void secrets_shouldReturnApi_whenConstructed() {
        final GitHubDependabotApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubDependabotApi(TestClients.caller(client));
        }

        final GitHubDependabotSecretsApi secrets = api.secrets();

        assertThat(secrets).isNotNull();
    }

    @Test
    void alertDismissalRequests_shouldReturnApi_whenConstructed() {
        final GitHubDependabotApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubDependabotApi(TestClients.caller(client));
        }

        final GitHubDependabotAlertDismissalRequestsApi alertDismissalRequests = api.alertDismissalRequests();

        assertThat(alertDismissalRequests).isNotNull();
    }
}
