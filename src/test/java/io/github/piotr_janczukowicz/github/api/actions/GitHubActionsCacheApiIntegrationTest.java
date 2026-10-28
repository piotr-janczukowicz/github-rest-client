package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.actionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActionsCacheApiIntegrationTest {

    private static final String REPO_USAGE_POLICY_PATH = "/repos/octocat/Hello-World/actions/cache/usage-policy";
    private static final String ENTERPRISE_USAGE_POLICY_PATH = "/enterprises/my-enterprise/actions/cache/usage-policy";
    private static final String ENTERPRISE_LIMIT_PATH = "/enterprises/my-enterprise/actions/cache/retention-limit";
    private static final String REPO_CACHES_PATH = "/repos/octocat/Hello-World/actions/caches";
    private static final String REPO_CACHE_PATH = "/repos/octocat/Hello-World/actions/caches/7";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getEnterpriseCacheRetentionLimit_shouldReturnLimit_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ENTERPRISE_LIMIT_PATH,
                okJson(jsonBytes(
                        GitHubActionsCacheApiIntegrationTest.class,
                        "actions-cache-retention-limit-for-enterprise.json")));

        final GitHubActionsCacheRetentionLimitForEnterprise limit;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            limit = actions.api().cache().getEnterpriseCacheRetentionLimit("my-enterprise");
        }

        assertThat(limit).isEqualTo(ActionsCatalogFixtures.actionsCacheRetentionLimitForEnterprise());
        HTTP.verifyRequested(GET, ENTERPRISE_LIMIT_PATH);
    }

    @Test
    void getEnterpriseCacheRetentionLimit_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ENTERPRISE_LIMIT_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            final GitHubActionsCacheApi cache = actions.api().cache();

            thrown = catchThrowable(() -> cache.getEnterpriseCacheRetentionLimit("my-enterprise"));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, ENTERPRISE_LIMIT_PATH);
    }

    @Test
    void setEnterpriseCacheRetentionLimit_shouldSendPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ENTERPRISE_LIMIT_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api()
                    .cache()
                    .setEnterpriseCacheRetentionLimit(
                            "my-enterprise", ActionsCatalogFixtures.actionsCacheRetentionLimitForEnterprise());
        }

        HTTP.verifyRequested(PUT, ENTERPRISE_LIMIT_PATH);
    }

    @Test
    void listActionsCaches_shouldReturnCaches_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_CACHES_PATH,
                okJson(jsonBytes(GitHubActionsCacheApiIntegrationTest.class, "actions-cache-list.json")));

        final GitHubPage<GitHubActionsCache> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().cache().listActionsCaches("octocat", "Hello-World");
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsCacheList().actionsCaches());
        HTTP.verifyQueryParam(GET, REPO_CACHES_PATH, "page", "1");
    }

    @Test
    void getRepoCacheUsage_shouldReturnUsage_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/repos/octocat/Hello-World/actions/cache/usage",
                okJson(jsonBytes(
                        GitHubActionsCacheApiIntegrationTest.class, "actions-cache-usage-by-repository.json")));

        final GitHubActionsCacheUsageByRepository usage;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            usage = actions.api().cache().getRepoCacheUsage("octocat", "Hello-World");
        }

        assertThat(usage).isEqualTo(ActionsCatalogFixtures.actionsCacheUsageByRepository());
    }

    @Test
    void deleteActionsCacheById_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, REPO_CACHE_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().cache().deleteActionsCacheById("octocat", "Hello-World", 7L);
        }

        HTTP.verifyRequested(DELETE, REPO_CACHE_PATH);
    }

    @Test
    void getRepoCacheUsagePolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_USAGE_POLICY_PATH,
                okJson(jsonBytes(
                        GitHubActionsCacheApiIntegrationTest.class, "actions-cache-usage-policy-for-repository.json")));

        final GitHubActionsCacheUsagePolicyForRepository policy;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            policy = actions.api().cache().getRepoCacheUsagePolicy("octocat", "Hello-World");
        }

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsCacheUsagePolicyForRepository());
        HTTP.verifyRequested(GET, REPO_USAGE_POLICY_PATH);
    }

    @Test
    void setRepoCacheUsagePolicy_shouldSendPatch_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PATCH, REPO_USAGE_POLICY_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api()
                    .cache()
                    .setRepoCacheUsagePolicy(
                            "octocat", "Hello-World", ActionsCatalogFixtures.actionsCacheUsagePolicyForRepository());
        }

        HTTP.verifyRequested(PATCH, REPO_USAGE_POLICY_PATH);
    }

    @Test
    void getEnterpriseCacheUsagePolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ENTERPRISE_USAGE_POLICY_PATH,
                okJson(jsonBytes(
                        GitHubActionsCacheApiIntegrationTest.class, "actions-cache-usage-policy-enterprise.json")));

        final GitHubActionsCacheUsagePolicyEnterprise policy;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            policy = actions.api().cache().getEnterpriseCacheUsagePolicy("my-enterprise");
        }

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsCacheUsagePolicyEnterprise());
        HTTP.verifyRequested(GET, ENTERPRISE_USAGE_POLICY_PATH);
    }

    @Test
    void setEnterpriseCacheUsagePolicy_shouldSendPatch_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PATCH, ENTERPRISE_USAGE_POLICY_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api()
                    .cache()
                    .setEnterpriseCacheUsagePolicy(
                            "my-enterprise", ActionsCatalogFixtures.actionsCacheUsagePolicyEnterprise());
        }

        HTTP.verifyRequested(PATCH, ENTERPRISE_USAGE_POLICY_PATH);
    }
}
