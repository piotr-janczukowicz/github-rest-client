package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.acceptedJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.deploymentsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubDeploymentApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long DEPLOYMENT_ID = 42L;

    private static final String DEPLOYMENTS_PATH = "/repos/octocat/Hello-World/deployments";
    private static final String DEPLOYMENT_PATH = "/repos/octocat/Hello-World/deployments/42";

    private static final JsonType<List<GitHubDeployment>> DEPLOYMENTS_TYPE = JsonTypes.listOf(GitHubDeployment.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listDeployments_shouldReturnDeployments_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                DEPLOYMENTS_PATH,
                okJson(listJsonBytes(GitHubDeploymentApiIntegrationTest.class, "deployment.json")));

        final GitHubPage<GitHubDeployment> page;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            page = api.api().deployment().listDeployments(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedDeployments());
        HTTP.verifyRequested(GET, DEPLOYMENTS_PATH);
        HTTP.verifyQueryParam(GET, DEPLOYMENTS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, DEPLOYMENTS_PATH, "per_page", "100");
    }

    @Test
    void listDeployments_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                DEPLOYMENTS_PATH,
                okJson(listJsonBytes(GitHubDeploymentApiIntegrationTest.class, "deployment.json")));

        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            api.api()
                    .deployment()
                    .listDeployments(
                            OWNER,
                            REPO,
                            GitHubListDeploymentsFilterBuilder.builder()
                                    .environment("production")
                                    .ref("main")
                                    .build(),
                            GitHubPageRequest.byPage());
        }

        HTTP.verifyQueryParam(GET, DEPLOYMENTS_PATH, "environment", "production");
        HTTP.verifyQueryParam(GET, DEPLOYMENTS_PATH, "ref", "main");
        HTTP.verifyQueryParam(GET, DEPLOYMENTS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, DEPLOYMENTS_PATH, "per_page", "100");
    }

    @Test
    void listDeployments_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        stubPagedList(DEPLOYMENTS_PATH, "deployment.json");

        final GitHubPage<GitHubDeployment> first;
        final Optional<GitHubPage<GitHubDeployment>> second;
        final Optional<GitHubPage<GitHubDeployment>> third;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            first = api.api().deployment().listDeployments(OWNER, REPO);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedDeployments());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedDeployments());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, DEPLOYMENTS_PATH, 2);
    }

    @Test
    void listDeployments_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                DEPLOYMENTS_PATH,
                okJson(listJsonBytes(GitHubDeploymentApiIntegrationTest.class, "deployment.json")));

        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            api.api()
                    .deployment()
                    .listDeployments(
                            OWNER, REPO, GitHubListDeploymentsFilter.empty(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, DEPLOYMENTS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, DEPLOYMENTS_PATH, "per_page", "10");
    }

    @Test
    void getDeployment_shouldReturnDeployment_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, DEPLOYMENT_PATH, okJson(jsonBytes(GitHubDeploymentApiIntegrationTest.class, "deployment.json")));

        final Optional<GitHubDeployment> deployment;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            deployment = api.api().deployment().getDeployment(OWNER, REPO, DEPLOYMENT_ID);
        }

        assertThat(deployment).contains(expectedDeployment());
        HTTP.verifyRequested(GET, DEPLOYMENT_PATH);
    }

    @Test
    void getDeployment_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, DEPLOYMENT_PATH, notFound());

        final Optional<GitHubDeployment> deployment;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            deployment = api.api().deployment().getDeployment(OWNER, REPO, DEPLOYMENT_ID);
        }

        assertThat(deployment).isEmpty();
        HTTP.verifyRequested(GET, DEPLOYMENT_PATH);
    }

    @Test
    void createDeployment_shouldReturnDeployment_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                DEPLOYMENTS_PATH,
                createdJson(jsonBytes(GitHubDeploymentApiIntegrationTest.class, "deployment.json")));
        final GitHubCreateDeploymentPayload payload = DeploymentsCatalogFixtures.createDeploymentPayload();

        final GitHubCreateDeploymentResult result;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            result = api.api().deployment().createDeployment(OWNER, REPO, payload);
        }

        assertThat(result).isEqualTo(GitHubCreateDeploymentResult.ofCreated(expectedDeployment()));
        HTTP.verifyRequested(POST, DEPLOYMENTS_PATH);
        HTTP.verifyHeader(POST, DEPLOYMENTS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, DEPLOYMENTS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void createDeployment_shouldReturnMergedBranch_whenResponseIsAccepted() throws Exception {
        HTTP.stub(
                POST,
                DEPLOYMENTS_PATH,
                acceptedJson(jsonBytes(GitHubDeploymentApiIntegrationTest.class, "deployment-merged-branch.json")));
        final GitHubCreateDeploymentPayload payload = DeploymentsCatalogFixtures.createDeploymentPayload();

        final GitHubCreateDeploymentResult result;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            result = api.api().deployment().createDeployment(OWNER, REPO, payload);
        }

        assertThat(result).isEqualTo(GitHubCreateDeploymentResult.ofMergedBranch(expectedMergedBranch()));
        HTTP.verifyRequested(POST, DEPLOYMENTS_PATH);
    }

    @Test
    void deleteDeployment_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, DEPLOYMENT_PATH, noContent());

        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            api.api().deployment().deleteDeployment(OWNER, REPO, DEPLOYMENT_ID);
        }

        HTTP.verifyRequested(DELETE, DEPLOYMENT_PATH);
    }

    private void stubPagedList(final String path, final String fixture) {
        HTTP.stub(
                GET,
                path,
                okJson(listJsonBytes(GitHubDeploymentApiIntegrationTest.class, fixture))
                        .header(LINK, linkNext(HTTP.uri(path + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubDeploymentApiIntegrationTest.class, fixture)));
    }

    private static List<GitHubDeployment> expectedDeployments() {
        return CODEC.decode(
                listJsonBytes(GitHubDeploymentApiIntegrationTest.class, "deployment.json"), DEPLOYMENTS_TYPE);
    }

    private static GitHubDeployment expectedDeployment() {
        return CODEC.decode(
                jsonBytes(GitHubDeploymentApiIntegrationTest.class, "deployment.json"), GitHubDeployment.class);
    }

    private static GitHubDeploymentMergedBranch expectedMergedBranch() {
        return CODEC.decode(
                jsonBytes(GitHubDeploymentApiIntegrationTest.class, "deployment-merged-branch.json"),
                GitHubDeploymentMergedBranch.class);
    }
}
