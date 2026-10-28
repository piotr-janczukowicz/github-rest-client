package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoriesApiTest {

    private GitHubClient client;

    @BeforeEach
    void setUpClient() {
        client = TestClients.client(HttpTransportMocks.mock());
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoriesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void repository_shouldReturnRepositoriesApi_whenCalled() {
        final GitHubRepositoriesApi reposApi = new GitHubRepositoriesApi(TestClients.caller(client));

        assertThat(reposApi.repository()).isInstanceOf(GitHubRepositoryApi.class);
    }

    @Test
    void autolinks_shouldReturnAutolinksApi_whenCalled() {
        final GitHubRepositoriesApi reposApi = new GitHubRepositoriesApi(TestClients.caller(client));

        assertThat(reposApi.autolinks()).isInstanceOf(GitHubRepositoriesAutolinksApi.class);
    }

    @Test
    void forks_shouldReturnForksApi_whenCalled() {
        final GitHubRepositoriesApi reposApi = new GitHubRepositoriesApi(TestClients.caller(client));

        assertThat(reposApi.forks()).isInstanceOf(GitHubRepositoriesForksApi.class);
    }

    @Test
    void issueTypes_shouldReturnIssueTypesApi_whenCalled() {
        final GitHubRepositoriesApi reposApi = new GitHubRepositoriesApi(TestClients.caller(client));

        assertThat(reposApi.issueTypes()).isInstanceOf(GitHubRepositoriesIssueTypesApi.class);
    }

    @Test
    void contents_shouldReturnContentsApi_whenCalled() {
        final GitHubRepositoriesApi reposApi = new GitHubRepositoriesApi(TestClients.caller(client));

        assertThat(reposApi.contents()).isInstanceOf(GitHubRepositoriesContentsApi.class);
    }

    @Test
    void webhooks_shouldReturnWebhooksApi_whenCalled() {
        final GitHubRepositoriesApi reposApi = new GitHubRepositoriesApi(TestClients.caller(client));

        assertThat(reposApi.webhooks()).isInstanceOf(GitHubRepositoriesWebhooksApi.class);
    }

    @Test
    void rules_shouldReturnRulesApi_whenCalled() {
        final GitHubRepositoriesApi reposApi = new GitHubRepositoriesApi(TestClients.caller(client));

        assertThat(reposApi.rules()).isInstanceOf(GitHubRepositoriesRulesApi.class);
    }

    @Test
    void ruleSuites_shouldReturnRuleSuitesApi_whenCalled() {
        final GitHubRepositoriesApi reposApi = new GitHubRepositoriesApi(TestClients.caller(client));

        assertThat(reposApi.ruleSuites()).isInstanceOf(GitHubRepositoriesRuleSuitesApi.class);
    }

    @Test
    void attestations_shouldReturnAttestationsApi_whenCalled() {
        final GitHubRepositoriesApi reposApi = new GitHubRepositoriesApi(TestClients.caller(client));

        assertThat(reposApi.attestations()).isInstanceOf(GitHubRepositoriesAttestationsApi.class);
    }

    @Test
    void customProperties_shouldReturnCustomPropertiesApi_whenCalled() {
        final GitHubRepositoriesApi reposApi = new GitHubRepositoriesApi(TestClients.caller(client));

        assertThat(reposApi.customProperties()).isInstanceOf(GitHubRepositoriesCustomPropertiesApi.class);
    }

    @Test
    void repository_shouldReturnSameInstance_whenCalledTwice() {
        final GitHubRepositoriesApi reposApi = new GitHubRepositoriesApi(TestClients.caller(client));

        assertThat(reposApi.repository()).isSameAs(reposApi.repository());
    }

    @Test
    void bypassRequests_shouldReturnBypassRequestsApi_whenCalled() {
        final GitHubRepositoriesApi reposApi = new GitHubRepositoriesApi(TestClients.caller(client));

        assertThat(reposApi.bypassRequests()).isInstanceOf(GitHubRepositoriesBypassRequestsApi.class);
    }

    @Test
    void lfs_shouldReturnLfsApi_whenCalled() {
        final GitHubRepositoriesApi reposApi = new GitHubRepositoriesApi(TestClients.caller(client));

        assertThat(reposApi.lfs()).isInstanceOf(GitHubRepositoriesLfsApi.class);
    }
}
