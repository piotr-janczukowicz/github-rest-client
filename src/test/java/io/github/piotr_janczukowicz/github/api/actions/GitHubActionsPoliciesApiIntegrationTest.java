package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.actionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActionsPoliciesApiIntegrationTest {

    private static final String ORG_POLICIES_PATH = "/orgs/github/actions/policies";
    private static final String ORG_POLICY_PATH = "/orgs/github/actions/policies/1";
    private static final String REPO_POLICIES_PATH = "/repos/octocat/Hello-World/actions/policies";
    private static final String REPO_POLICY_PATH = "/repos/octocat/Hello-World/actions/policies/1";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getOrgActionsPolicies_shouldReturnPolicies_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_POLICIES_PATH,
                okJson(jsonBytes(GitHubActionsPoliciesApiIntegrationTest.class, "actions-policies.json")));

        final GitHubPage<GitHubActionsPolicy> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().policies().getOrgActionsPolicies("github");
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsPolicies().policies());
        HTTP.verifyQueryParam(GET, ORG_POLICIES_PATH, "page", "1");
    }

    @Test
    void createOrgActionsPolicy_shouldReturnPolicy_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                ORG_POLICIES_PATH,
                createdJson(jsonBytes(GitHubActionsPoliciesApiIntegrationTest.class, "actions-policy.json")));

        final GitHubActionsPolicy policy;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            policy = actions.api()
                    .policies()
                    .createOrgActionsPolicy("github", ActionsCatalogFixtures.createOrgActionsPolicyPayload());
        }

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsPolicy());
        HTTP.verifyRequested(POST, ORG_POLICIES_PATH);
    }

    @Test
    void getOrgActionsPolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_POLICY_PATH,
                okJson(jsonBytes(GitHubActionsPoliciesApiIntegrationTest.class, "actions-policy.json")));

        final GitHubActionsPolicy policy;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            policy = actions.api().policies().getOrgActionsPolicy("github", 1L).orElseThrow();
        }

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsPolicy());
    }

    @Test
    void updateOrgActionsPolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                ORG_POLICY_PATH,
                okJson(jsonBytes(GitHubActionsPoliciesApiIntegrationTest.class, "actions-policy.json")));

        final GitHubActionsPolicy policy;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            policy = actions.api()
                    .policies()
                    .updateOrgActionsPolicy("github", 1L, ActionsCatalogFixtures.updateOrgActionsPolicyPayload());
        }

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsPolicy());
        HTTP.verifyRequested(PUT, ORG_POLICY_PATH);
    }

    @Test
    void deleteOrgActionsPolicy_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORG_POLICY_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().policies().deleteOrgActionsPolicy("github", 1L);
        }

        HTTP.verifyRequested(DELETE, ORG_POLICY_PATH);
    }

    @Test
    void getRepoActionsPolicies_shouldReturnPolicies_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_POLICIES_PATH,
                okJson(jsonBytes(GitHubActionsPoliciesApiIntegrationTest.class, "actions-policies.json")));

        final GitHubPage<GitHubActionsPolicy> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().policies().getRepoActionsPolicies("octocat", "Hello-World");
        }

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsPolicies().policies());
        HTTP.verifyQueryParam(GET, REPO_POLICIES_PATH, "page", "1");
    }

    @Test
    void createRepoActionsPolicy_shouldReturnPolicy_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                REPO_POLICIES_PATH,
                createdJson(jsonBytes(GitHubActionsPoliciesApiIntegrationTest.class, "actions-policy.json")));

        final GitHubActionsPolicy policy;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            policy = actions.api()
                    .policies()
                    .createRepoActionsPolicy(
                            "octocat", "Hello-World", ActionsCatalogFixtures.createRepoActionsPolicyPayload());
        }

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsPolicy());
        HTTP.verifyRequested(POST, REPO_POLICIES_PATH);
    }

    @Test
    void getRepoActionsPolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_POLICY_PATH,
                okJson(jsonBytes(GitHubActionsPoliciesApiIntegrationTest.class, "actions-policy.json")));

        final GitHubActionsPolicy policy;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            policy = actions.api()
                    .policies()
                    .getRepoActionsPolicy("octocat", "Hello-World", 1L)
                    .orElseThrow();
        }

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsPolicy());
    }

    @Test
    void updateRepoActionsPolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                REPO_POLICY_PATH,
                okJson(jsonBytes(GitHubActionsPoliciesApiIntegrationTest.class, "actions-policy.json")));

        final GitHubActionsPolicy policy;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            policy = actions.api()
                    .policies()
                    .updateRepoActionsPolicy(
                            "octocat", "Hello-World", 1L, ActionsCatalogFixtures.updateRepoActionsPolicyPayload());
        }

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsPolicy());
        HTTP.verifyRequested(PUT, REPO_POLICY_PATH);
    }

    @Test
    void deleteRepoActionsPolicy_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, REPO_POLICY_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().policies().deleteRepoActionsPolicy("octocat", "Hello-World", 1L);
        }

        HTTP.verifyRequested(DELETE, REPO_POLICY_PATH);
    }
}
