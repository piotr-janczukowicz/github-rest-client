package io.github.piotr_janczukowicz.github.api.secretscanning;

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
class GitHubSecretScanningApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecretScanningApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void alerts_shouldReturnApi_whenConstructed() {
        final GitHubSecretScanningApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubSecretScanningApi(TestClients.caller(client));
        }

        final GitHubSecretScanningAlertsApi alerts = api.alerts();

        assertThat(alerts).isNotNull();
    }

    @Test
    void customPatterns_shouldReturnApi_whenConstructed() {
        final GitHubSecretScanningApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubSecretScanningApi(TestClients.caller(client));
        }

        final GitHubSecretScanningCustomPatternsApi customPatterns = api.customPatterns();

        assertThat(customPatterns).isNotNull();
    }

    @Test
    void pushProtection_shouldReturnApi_whenConstructed() {
        final GitHubSecretScanningApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubSecretScanningApi(TestClients.caller(client));
        }

        final GitHubSecretScanningPushProtectionApi pushProtection = api.pushProtection();

        assertThat(pushProtection).isNotNull();
    }

    @Test
    void delegatedBypass_shouldReturnApi_whenConstructed() {
        final GitHubSecretScanningApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubSecretScanningApi(TestClients.caller(client));
        }

        final GitHubSecretScanningDelegatedBypassApi delegatedBypass = api.delegatedBypass();

        assertThat(delegatedBypass).isNotNull();
    }

    @Test
    void alertDismissalRequests_shouldReturnApi_whenConstructed() {
        final GitHubSecretScanningApi api;
        try (final GitHubClient client = TestClients.client(transport)) {
            api = new GitHubSecretScanningApi(TestClients.caller(client));
        }

        final GitHubSecretScanningAlertDismissalRequestsApi alertDismissalRequests = api.alertDismissalRequests();

        assertThat(alertDismissalRequests).isNotNull();
    }
}
