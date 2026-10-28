package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubActionsCacheApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ENTERPRISE = "my-enterprise";
    private static final String ORG = "github";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsCacheApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getEnterpriseCacheRetentionLimit_shouldReturnLimit_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-cache-retention-limit-for-enterprise.json")));

        final GitHubActionsCacheRetentionLimitForEnterprise limit = api().getEnterpriseCacheRetentionLimit(ENTERPRISE);

        assertThat(limit).isEqualTo(ActionsCatalogFixtures.actionsCacheRetentionLimitForEnterprise());
    }

    @Test
    void getEnterpriseCacheRetentionLimit_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getEnterpriseCacheRetentionLimit(ENTERPRISE));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setEnterpriseCacheRetentionLimit_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setEnterpriseCacheRetentionLimit(
                        ENTERPRISE, ActionsCatalogFixtures.actionsCacheRetentionLimitForEnterprise());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT https://api.github.com/enterprises/my-enterprise/actions/cache/retention-limit");
    }

    @Test
    void getEnterpriseCacheStorageLimit_shouldReturnLimit_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-cache-storage-limit-for-enterprise.json")));

        final GitHubActionsCacheStorageLimitForEnterprise limit = api().getEnterpriseCacheStorageLimit(ENTERPRISE);

        assertThat(limit).isEqualTo(ActionsCatalogFixtures.actionsCacheStorageLimitForEnterprise());
    }

    @Test
    void getEnterpriseCacheStorageLimit_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getEnterpriseCacheStorageLimit(ENTERPRISE));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setEnterpriseCacheStorageLimit_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setEnterpriseCacheStorageLimit(
                        ENTERPRISE, ActionsCatalogFixtures.actionsCacheStorageLimitForEnterprise());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT https://api.github.com/enterprises/my-enterprise/actions/cache/storage-limit");
    }

    @Test
    void getOrgCacheRetentionLimit_shouldReturnLimit_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), json("actions-cache-retention-limit-for-organization.json")));

        final GitHubActionsCacheRetentionLimitForOrganization limit = api().getOrgCacheRetentionLimit(ORG);

        assertThat(limit).isEqualTo(ActionsCatalogFixtures.actionsCacheRetentionLimitForOrganization());
    }

    @Test
    void getOrgCacheRetentionLimit_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getOrgCacheRetentionLimit(ORG));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setOrgCacheRetentionLimit_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setOrgCacheRetentionLimit(ORG, ActionsCatalogFixtures.actionsCacheRetentionLimitForOrganization());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT https://api.github.com/organizations/github/actions/cache/retention-limit");
    }

    @Test
    void getOrgCacheStorageLimit_shouldReturnLimit_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-cache-storage-limit-for-organization.json")));

        final GitHubActionsCacheStorageLimitForOrganization limit = api().getOrgCacheStorageLimit(ORG);

        assertThat(limit).isEqualTo(ActionsCatalogFixtures.actionsCacheStorageLimitForOrganization());
    }

    @Test
    void getOrgCacheStorageLimit_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getOrgCacheStorageLimit(ORG));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setOrgCacheStorageLimit_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setOrgCacheStorageLimit(ORG, ActionsCatalogFixtures.actionsCacheStorageLimitForOrganization());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT https://api.github.com/organizations/github/actions/cache/storage-limit");
    }

    @Test
    void getOrgCacheUsage_shouldReturnUsage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-cache-usage-org-enterprise.json")));

        final GitHubActionsCacheUsageOrgEnterprise usage = api().getOrgCacheUsage(ORG);

        assertThat(usage).isEqualTo(ActionsCatalogFixtures.actionsCacheUsageOrgEnterprise());
    }

    @Test
    void listOrgCacheUsageByRepository_shouldReturnUsages_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-cache-usages.json")));

        final GitHubPage<GitHubActionsCacheUsageByRepository> page = api().listOrgCacheUsageByRepository(ORG);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsCacheUsages().repositoryCacheUsages());
    }

    @Test
    void listOrgCacheUsageByRepository_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-cache-usages.json")));

        api().listOrgCacheUsageByRepository(ORG, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void getRepoCacheRetentionLimit_shouldReturnLimit_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-cache-retention-limit-for-repository.json")));

        final GitHubActionsCacheRetentionLimitForRepository limit = api().getRepoCacheRetentionLimit(OWNER, REPO);

        assertThat(limit).isEqualTo(ActionsCatalogFixtures.actionsCacheRetentionLimitForRepository());
    }

    @Test
    void getRepoCacheRetentionLimit_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getRepoCacheRetentionLimit(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setRepoCacheRetentionLimit_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setRepoCacheRetentionLimit(OWNER, REPO, ActionsCatalogFixtures.actionsCacheRetentionLimitForRepository());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT https://api.github.com/repos/octocat/Hello-World/actions/cache/retention-limit");
    }

    @Test
    void getRepoCacheStorageLimit_shouldReturnLimit_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-cache-storage-limit-for-repository.json")));

        final GitHubActionsCacheStorageLimitForRepository limit = api().getRepoCacheStorageLimit(OWNER, REPO);

        assertThat(limit).isEqualTo(ActionsCatalogFixtures.actionsCacheStorageLimitForRepository());
    }

    @Test
    void getRepoCacheStorageLimit_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getRepoCacheStorageLimit(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setRepoCacheStorageLimit_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setRepoCacheStorageLimit(OWNER, REPO, ActionsCatalogFixtures.actionsCacheStorageLimitForRepository());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT https://api.github.com/repos/octocat/Hello-World/actions/cache/storage-limit");
    }

    @Test
    void getRepoCacheUsage_shouldReturnUsage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-cache-usage-by-repository.json")));

        final GitHubActionsCacheUsageByRepository usage = api().getRepoCacheUsage(OWNER, REPO);

        assertThat(usage).isEqualTo(ActionsCatalogFixtures.actionsCacheUsageByRepository());
    }

    @Test
    void listActionsCaches_shouldReturnCaches_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-cache-list.json")));

        final GitHubPage<GitHubActionsCache> page = api().listActionsCaches(OWNER, REPO);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsCacheList().actionsCaches());
    }

    @Test
    void listActionsCaches_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-cache-list.json")));

        api().listActionsCaches(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void listActionsCaches_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-cache-list.json")));

        api().listActionsCaches(
                        OWNER,
                        REPO,
                        GitHubGetActionsCacheListFilterBuilder.builder()
                                .key("linux")
                                .build(),
                        GitHubPageNumberRequest.of(1, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("key", "linux");
    }

    @Test
    void deleteActionsCacheByKey_shouldReturnDeletedCaches_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-cache-list.json")));

        final GitHubActionsCacheList deleted = api().deleteActionsCacheByKey(
                        OWNER,
                        REPO,
                        GitHubDeleteActionsCacheByKeyFilterBuilder.builder()
                                .key("linux")
                                .build());

        assertThat(deleted).isEqualTo(ActionsCatalogFixtures.actionsCacheList());
    }

    @Test
    void deleteActionsCacheById_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteActionsCacheById(OWNER, REPO, 7L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE https://api.github.com/repos/octocat/Hello-World/actions/caches/7");
    }

    @Test
    void getEnterpriseCacheUsage_shouldReturnUsage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-cache-usage-org-enterprise.json")));

        final GitHubActionsCacheUsageOrgEnterprise usage = api().getEnterpriseCacheUsage(ENTERPRISE);

        assertThat(usage).isEqualTo(ActionsCatalogFixtures.actionsCacheUsageOrgEnterprise());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("GET https://api.github.com/enterprises/my-enterprise/actions/cache/usage");
    }

    private GitHubActionsCacheApi api() {
        return new GitHubActionsCacheApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActionsCacheApiTest.class, fileName);
    }

    private static String line(final HttpRequest request) {
        return request.method() + " " + request.uri();
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    @Test
    void getRepoCacheUsagePolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-cache-usage-policy-for-repository.json")));

        final GitHubActionsCacheUsagePolicyForRepository policy = api().getRepoCacheUsagePolicy(OWNER, REPO);

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsCacheUsagePolicyForRepository());
    }

    @Test
    void getRepoCacheUsagePolicy_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getRepoCacheUsagePolicy(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setRepoCacheUsagePolicy_shouldSendPatch_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setRepoCacheUsagePolicy(OWNER, REPO, ActionsCatalogFixtures.actionsCacheUsagePolicyForRepository());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PATCH https://api.github.com/repos/octocat/Hello-World/actions/cache/usage-policy");
    }

    @Test
    void setRepoCacheUsagePolicy_shouldThrow_whenPayloadNull() {
        final GitHubActionsCacheApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setRepoCacheUsagePolicy(OWNER, REPO, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getEnterpriseCacheUsagePolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-cache-usage-policy-enterprise.json")));

        final GitHubActionsCacheUsagePolicyEnterprise policy = api().getEnterpriseCacheUsagePolicy(ENTERPRISE);

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsCacheUsagePolicyEnterprise());
    }

    @Test
    void setEnterpriseCacheUsagePolicy_shouldSendPatch_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setEnterpriseCacheUsagePolicy(ENTERPRISE, ActionsCatalogFixtures.actionsCacheUsagePolicyEnterprise());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PATCH https://api.github.com/enterprises/my-enterprise/actions/cache/usage-policy");
    }

    @Test
    void setEnterpriseCacheUsagePolicy_shouldThrow_whenPayloadNull() {
        final GitHubActionsCacheApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setEnterpriseCacheUsagePolicy(ENTERPRISE, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }
}
