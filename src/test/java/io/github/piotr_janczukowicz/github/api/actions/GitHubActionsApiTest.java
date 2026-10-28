package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void artifacts_shouldReturnApi_whenConstructed() {
        final GitHubActionsArtifactsApi api = new GitHubActionsApi(TestClients.caller(transport)).artifacts();

        assertThat(api).isNotNull();
    }

    @Test
    void cache_shouldReturnApi_whenConstructed() {
        final GitHubActionsCacheApi api = new GitHubActionsApi(TestClients.caller(transport)).cache();

        assertThat(api).isNotNull();
    }

    @Test
    void concurrencyGroups_shouldReturnApi_whenConstructed() {
        final GitHubActionsConcurrencyGroupsApi api =
                new GitHubActionsApi(TestClients.caller(transport)).concurrencyGroups();

        assertThat(api).isNotNull();
    }

    @Test
    void hostedRunners_shouldReturnApi_whenConstructed() {
        final GitHubActionsHostedRunnersApi api = new GitHubActionsApi(TestClients.caller(transport)).hostedRunners();

        assertThat(api).isNotNull();
    }

    @Test
    void oidc_shouldReturnApi_whenConstructed() {
        final GitHubActionsOidcApi api = new GitHubActionsApi(TestClients.caller(transport)).oidc();

        assertThat(api).isNotNull();
    }

    @Test
    void permissions_shouldReturnApi_whenConstructed() {
        final GitHubActionsPermissionsApi api = new GitHubActionsApi(TestClients.caller(transport)).permissions();

        assertThat(api).isNotNull();
    }

    @Test
    void policies_shouldReturnApi_whenConstructed() {
        final GitHubActionsPoliciesApi api = new GitHubActionsApi(TestClients.caller(transport)).policies();

        assertThat(api).isNotNull();
    }

    @Test
    void secrets_shouldReturnApi_whenConstructed() {
        final GitHubActionsSecretsApi api = new GitHubActionsApi(TestClients.caller(transport)).secrets();

        assertThat(api).isNotNull();
    }

    @Test
    void selfHostedRunnerGroups_shouldReturnApi_whenConstructed() {
        final GitHubActionsSelfHostedRunnerGroupsApi api =
                new GitHubActionsApi(TestClients.caller(transport)).selfHostedRunnerGroups();

        assertThat(api).isNotNull();
    }

    @Test
    void selfHostedRunners_shouldReturnApi_whenConstructed() {
        final GitHubActionsSelfHostedRunnersApi api =
                new GitHubActionsApi(TestClients.caller(transport)).selfHostedRunners();

        assertThat(api).isNotNull();
    }

    @Test
    void variables_shouldReturnApi_whenConstructed() {
        final GitHubActionsVariablesApi api = new GitHubActionsApi(TestClients.caller(transport)).variables();

        assertThat(api).isNotNull();
    }

    @Test
    void workflowJobs_shouldReturnApi_whenConstructed() {
        final GitHubActionsWorkflowJobsApi api = new GitHubActionsApi(TestClients.caller(transport)).workflowJobs();

        assertThat(api).isNotNull();
    }

    @Test
    void workflowRuns_shouldReturnApi_whenConstructed() {
        final GitHubActionsWorkflowRunsApi api = new GitHubActionsApi(TestClients.caller(transport)).workflowRuns();

        assertThat(api).isNotNull();
    }

    @Test
    void workflows_shouldReturnApi_whenConstructed() {
        final GitHubActionsWorkflowsApi api = new GitHubActionsApi(TestClients.caller(transport)).workflows();

        assertThat(api).isNotNull();
    }
}
