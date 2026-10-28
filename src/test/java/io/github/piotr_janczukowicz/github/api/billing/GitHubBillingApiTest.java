package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBillingApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubBillingApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void budgets_shouldReturnBudgetsApi_whenCalled() {
        final GitHubBillingApi api = new GitHubBillingApi(TestClients.caller(transport));

        assertThat(api.budgets()).isInstanceOf(GitHubBillingBudgetsApi.class);
    }

    @Test
    void usage_shouldReturnUsageApi_whenCalled() {
        final GitHubBillingApi api = new GitHubBillingApi(TestClients.caller(transport));

        assertThat(api.usage()).isInstanceOf(GitHubBillingUsageApi.class);
    }

    @Test
    void costCenters_shouldReturnCostCentersApi_whenCalled() {
        final GitHubBillingApi api = new GitHubBillingApi(TestClients.caller(transport));
        assertThat(api.costCenters()).isInstanceOf(GitHubBillingCostCentersApi.class);
    }

    @Test
    void usageReports_shouldReturnUsageReportsApi_whenCalled() {
        final GitHubBillingApi api = new GitHubBillingApi(TestClients.caller(transport));
        assertThat(api.usageReports()).isInstanceOf(GitHubBillingUsageReportsApi.class);
    }

    @Test
    void billing_shouldReturnBillingApi_whenCalled() {
        final GitHubBillingApi api = new GitHubBillingApi(TestClients.caller(transport));
        assertThat(api.billing()).isInstanceOf(GitHubBillingBillingApi.class);
    }
}
