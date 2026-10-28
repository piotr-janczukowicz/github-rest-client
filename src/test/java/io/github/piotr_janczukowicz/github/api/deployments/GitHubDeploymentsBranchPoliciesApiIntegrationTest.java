package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.deploymentsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubDeploymentsBranchPoliciesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ENVIRONMENT = "staging";
    private static final long BRANCH_POLICY_ID = 361_471L;

    private static final String BRANCH_POLICIES_PATH =
            "/repos/octocat/Hello-World/environments/staging/deployment-branch-policies";
    private static final String BRANCH_POLICY_PATH =
            "/repos/octocat/Hello-World/environments/staging/deployment-branch-policies/361471";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listDeploymentBranchPolicies_shouldReturnPolicies_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, BRANCH_POLICIES_PATH, okJson(wrappedPage("branch_policies", "deployment-branch-policy.json")));

        final GitHubPage<GitHubDeploymentBranchPolicy> page;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            page = api.api().branchPolicies().listDeploymentBranchPolicies(OWNER, REPO, ENVIRONMENT);
        }

        assertThat(page.items()).isEqualTo(expectedBranchPoliciesPage().branchPolicies());
        assertThat(page.totalCount()).isEqualTo(1);
        HTTP.verifyRequested(GET, BRANCH_POLICIES_PATH);
        HTTP.verifyQueryParam(GET, BRANCH_POLICIES_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, BRANCH_POLICIES_PATH, "per_page", "100");
    }

    @Test
    void listDeploymentBranchPolicies_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(GET, BRANCH_POLICIES_PATH, okJson(wrappedPage("branch_policies", "deployment-branch-policy.json")));

        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            api.api()
                    .branchPolicies()
                    .listDeploymentBranchPolicies(OWNER, REPO, ENVIRONMENT, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, BRANCH_POLICIES_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, BRANCH_POLICIES_PATH, "per_page", "10");
    }

    @Test
    void getDeploymentBranchPolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                BRANCH_POLICY_PATH,
                okJson(jsonBytes(
                        GitHubDeploymentsBranchPoliciesApiIntegrationTest.class, "deployment-branch-policy.json")));

        final Optional<GitHubDeploymentBranchPolicy> policy;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            policy = api.api().branchPolicies().getDeploymentBranchPolicy(OWNER, REPO, ENVIRONMENT, BRANCH_POLICY_ID);
        }

        assertThat(policy).contains(expectedBranchPolicy());
        HTTP.verifyRequested(GET, BRANCH_POLICY_PATH);
    }

    @Test
    void getDeploymentBranchPolicy_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, BRANCH_POLICY_PATH, notFound());

        final Optional<GitHubDeploymentBranchPolicy> policy;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            policy = api.api().branchPolicies().getDeploymentBranchPolicy(OWNER, REPO, ENVIRONMENT, BRANCH_POLICY_ID);
        }

        assertThat(policy).isEmpty();
        HTTP.verifyRequested(GET, BRANCH_POLICY_PATH);
    }

    @Test
    void createDeploymentBranchPolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                BRANCH_POLICIES_PATH,
                okJson(jsonBytes(
                        GitHubDeploymentsBranchPoliciesApiIntegrationTest.class, "deployment-branch-policy.json")));
        final GitHubCreateDeploymentBranchPolicyPayload payload =
                DeploymentsCatalogFixtures.createDeploymentBranchPolicyPayload();

        final GitHubDeploymentBranchPolicy policy;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            policy = api.api().branchPolicies().createDeploymentBranchPolicy(OWNER, REPO, ENVIRONMENT, payload);
        }

        assertThat(policy).isEqualTo(expectedBranchPolicy());
        HTTP.verifyRequested(POST, BRANCH_POLICIES_PATH);
        HTTP.verifyHeader(POST, BRANCH_POLICIES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, BRANCH_POLICIES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updateDeploymentBranchPolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                BRANCH_POLICY_PATH,
                okJson(jsonBytes(
                        GitHubDeploymentsBranchPoliciesApiIntegrationTest.class, "deployment-branch-policy.json")));
        final GitHubUpdateDeploymentBranchPolicyPayload payload =
                DeploymentsCatalogFixtures.updateDeploymentBranchPolicyPayload();

        final GitHubDeploymentBranchPolicy policy;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            policy = api.api()
                    .branchPolicies()
                    .updateDeploymentBranchPolicy(OWNER, REPO, ENVIRONMENT, BRANCH_POLICY_ID, payload);
        }

        assertThat(policy).isEqualTo(expectedBranchPolicy());
        HTTP.verifyRequested(PUT, BRANCH_POLICY_PATH);
        HTTP.verifyHeader(PUT, BRANCH_POLICY_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, BRANCH_POLICY_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteDeploymentBranchPolicy_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, BRANCH_POLICY_PATH, noContent());

        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            api.api().branchPolicies().deleteDeploymentBranchPolicy(OWNER, REPO, ENVIRONMENT, BRANCH_POLICY_ID);
        }

        HTTP.verifyRequested(DELETE, BRANCH_POLICY_PATH);
    }

    private static byte[] wrappedPage(final String arrayField, final String fixture) {
        final String item = new String(
                jsonBytes(GitHubDeploymentsBranchPoliciesApiIntegrationTest.class, fixture), StandardCharsets.UTF_8);
        return ("{\"total_count\":1,\"" + arrayField + "\":[" + item + "]}").getBytes(StandardCharsets.UTF_8);
    }

    private static GitHubDeploymentBranchPoliciesPage expectedBranchPoliciesPage() {
        return CODEC.decode(
                wrappedPage("branch_policies", "deployment-branch-policy.json"),
                GitHubDeploymentBranchPoliciesPage.class);
    }

    private static GitHubDeploymentBranchPolicy expectedBranchPolicy() {
        return CODEC.decode(
                jsonBytes(GitHubDeploymentsBranchPoliciesApiIntegrationTest.class, "deployment-branch-policy.json"),
                GitHubDeploymentBranchPolicy.class);
    }
}
