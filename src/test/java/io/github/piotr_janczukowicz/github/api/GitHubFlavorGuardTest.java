package io.github.piotr_janczukowicz.github.api;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.actions.GitHubActionsCacheApi;
import io.github.piotr_janczukowicz.github.api.actions.GitHubActionsHostedRunnersApi;
import io.github.piotr_janczukowicz.github.api.billing.GitHubBillingBudgetsApi;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubEnterpriseAdminManageGhesApi;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullsStacksApi;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryApi;
import io.github.piotr_janczukowicz.github.api.scim.GitHubScimApi;
import io.github.piotr_janczukowicz.github.client.GhesVersion;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubManagementCredentials;
import io.github.piotr_janczukowicz.github.client.TestGitHubClients;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubFlavorGuardTest {

    private static final GitHubManagementCredentials CREDENTIALS = new GitHubManagementCredentials("secret");

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    private GitHub github(final GitHubFlavor flavor) {
        return new GitHub(GitHubCaller.of(TestGitHubClients.flavored(transport, flavor)));
    }

    private GitHub ghes(final GhesVersion version) {
        return new GitHub(GitHubCaller.of(TestGitHubClients.ghes(transport, version)));
    }

    @Test
    void deleteUserFromOrg_shouldThrowBeforeAnyRequest_whenClientIsCloud() throws Exception {
        final GitHubScimApi scim = github(GitHubFlavor.CLOUD).scim();

        final Throwable thrown = catchThrowable(() -> scim.deleteUserFromOrg("octo-org", "1"));

        assertThat(thrown)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("CLOUD")
                .hasMessageContaining("exists in: [GHEC]");
        verify(transport, never()).execute(any());
    }

    @Test
    void deleteUserFromOrg_shouldSendRequest_whenClientIsGhec() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubScimApi scim = github(GitHubFlavor.GHEC).scim();

        scim.deleteUserFromOrg("octo-org", "1");

        verify(transport).execute(any());
    }

    @Test
    void deleteUserFromOrg_shouldSendRequest_whenClientHasNoFlavor() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubScimApi scim = new GitHub(GitHubCaller.of(TestGitHubClients.raw(transport))).scim();

        scim.deleteUserFromOrg("octo-org", "1");

        verify(transport).execute(any());
    }

    @Test
    void deleteUserFromOrg_shouldThrowBeforeAnyRequest_whenClientIsGhes() throws Exception {
        final GitHubScimApi scim = ghes(new GhesVersion(3, 22)).scim();

        final Throwable thrown = catchThrowable(() -> scim.deleteUserFromOrg("octo-org", "1"));

        assertThat(thrown)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("GHES")
                .hasMessageContaining("exists in: [GHEC]");
        verify(transport, never()).execute(any());
    }

    @Test
    void getBudget_shouldThrowBeforeAnyRequest_whenClientIsGhec() throws Exception {
        final GitHubBillingBudgetsApi budgets =
                github(GitHubFlavor.GHEC).billing().budgets();

        final Throwable thrown = catchThrowable(() -> budgets.getBudget("octo-org", "b1"));

        assertThat(thrown).isInstanceOf(UnsupportedOperationException.class).hasMessageContaining("GHEC");
        verify(transport, never()).execute(any());
    }

    @Test
    void listBudgets_shouldThrowBeforeAnyRequest_whenClientIsGhec() throws Exception {
        final GitHubBillingBudgetsApi budgets =
                github(GitHubFlavor.GHEC).billing().budgets();

        final Throwable thrown = catchThrowable(() -> budgets.listBudgets("octo-org"));

        assertThat(thrown)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("GHEC")
                .hasMessageContaining("exists in: [CLOUD]");
        verify(transport, never()).execute(any());
    }

    @Test
    void listHostedRunnersForEnterprise_shouldThrowBeforeAnyRequest_whenClientIsGhes() throws Exception {
        final GitHubActionsHostedRunnersApi runners =
                ghes(new GhesVersion(3, 22)).actions().hostedRunners();

        final Throwable thrown = catchThrowable(() -> runners.listHostedRunnersForEnterprise("octo-ent"));

        assertThat(thrown)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("GHES")
                .hasMessageContaining("exists in: [GHEC]");
        verify(transport, never()).execute(any());
    }

    @Test
    void listCacheInfo_shouldThrowBeforeAnyRequest_whenClientIsCloud() throws Exception {
        final GitHubRepositoryApi repository =
                github(GitHubFlavor.CLOUD).repositories().repository();

        final Throwable thrown = catchThrowable(() -> repository.listCacheInfo("octo-org", "hello"));

        assertThat(thrown)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("CLOUD")
                .hasMessageContaining("exists in: [GHES]");
        verify(transport, never()).execute(any());
    }

    @Test
    void getEnterpriseCacheUsage_shouldThrowBeforeAnyRequest_whenClientIsCloud() throws Exception {
        final GitHubActionsCacheApi cache = github(GitHubFlavor.CLOUD).actions().cache();

        final Throwable thrown = catchThrowable(() -> cache.getEnterpriseCacheUsage("octo-ent"));

        assertThat(thrown)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("CLOUD")
                .hasMessageContaining("exists in: [GHEC, GHES]");
        verify(transport, never()).execute(any());
    }

    @Test
    void getEnterpriseCacheRetentionLimit_shouldThrowBeforeAnyRequest_whenClientIsGhes() throws Exception {
        final GitHubActionsCacheApi cache =
                ghes(new GhesVersion(3, 22)).actions().cache();

        final Throwable thrown = catchThrowable(() -> cache.getEnterpriseCacheRetentionLimit("octo-ent"));

        assertThat(thrown)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("GHES")
                .hasMessageContaining("exists in: [CLOUD, GHEC]");
        verify(transport, never()).execute(any());
    }

    @Test
    void list_shouldThrowBeforeAnyRequest_whenClientIsGhes() throws Exception {
        final GitHubPullsStacksApi stacks =
                ghes(new GhesVersion(3, 22)).pullRequests().stacks();

        final Throwable thrown = catchThrowable(() -> stacks.list("octo-org", "hello"));

        assertThat(thrown)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("GHES")
                .hasMessageContaining("exists in: [CLOUD, GHEC]");
        verify(transport, never()).execute(any());
    }

    @Test
    void getManageUpgradeStatus_shouldThrowBeforeAnyRequest_whenGhesIsOlderThanSince() throws Exception {
        final GitHubEnterpriseAdminManageGhesApi manage =
                ghes(new GhesVersion(3, 21)).enterpriseAdmin().manageGhes();

        final Throwable thrown = catchThrowable(() -> manage.getManageUpgradeStatus(CREDENTIALS));

        assertThat(thrown)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("not available in GitHub Enterprise Server 3.21")
                .hasMessageContaining("3.22");
        verify(transport, never()).execute(any());
    }

    @Test
    void licenseUpload_shouldThrowBeforeAnyRequest_whenClientIsCloud() throws Exception {
        final GitHubEnterpriseAdminManageGhesApi manage =
                github(GitHubFlavor.CLOUD).enterpriseAdmin().manageGhes();

        final Throwable thrown = catchThrowable(() -> manage.licenseUpload(CREDENTIALS, null));

        assertThat(thrown)
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("CLOUD")
                .hasMessageContaining("exists in: [GHES]");
        verify(transport, never()).execute(any());
    }
}
