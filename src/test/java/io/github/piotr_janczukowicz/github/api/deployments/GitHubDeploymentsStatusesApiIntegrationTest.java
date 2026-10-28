package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.deploymentsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
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
class GitHubDeploymentsStatusesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long DEPLOYMENT_ID = 42L;
    private static final long STATUS_ID = 1L;

    private static final String STATUSES_PATH = "/repos/octocat/Hello-World/deployments/42/statuses";
    private static final String STATUS_PATH = "/repos/octocat/Hello-World/deployments/42/statuses/1";

    private static final JsonType<List<GitHubDeploymentStatus>> STATUSES_TYPE =
            JsonTypes.listOf(GitHubDeploymentStatus.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listDeploymentStatuses_shouldReturnStatuses_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                STATUSES_PATH,
                okJson(listJsonBytes(GitHubDeploymentsStatusesApiIntegrationTest.class, "deployment-status.json")));

        final GitHubPage<GitHubDeploymentStatus> page;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            page = api.api().statuses().listDeploymentStatuses(OWNER, REPO, DEPLOYMENT_ID);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedStatuses());
        HTTP.verifyRequested(GET, STATUSES_PATH);
        HTTP.verifyQueryParam(GET, STATUSES_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, STATUSES_PATH, "per_page", "100");
    }

    @Test
    void listDeploymentStatuses_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        stubPagedList(STATUSES_PATH, "deployment-status.json");

        final GitHubPage<GitHubDeploymentStatus> first;
        final Optional<GitHubPage<GitHubDeploymentStatus>> second;
        final Optional<GitHubPage<GitHubDeploymentStatus>> third;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            first = api.api().statuses().listDeploymentStatuses(OWNER, REPO, DEPLOYMENT_ID);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedStatuses());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedStatuses());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, STATUSES_PATH, 2);
    }

    @Test
    void listDeploymentStatuses_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                STATUSES_PATH,
                okJson(listJsonBytes(GitHubDeploymentsStatusesApiIntegrationTest.class, "deployment-status.json")));

        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            api.api().statuses().listDeploymentStatuses(OWNER, REPO, DEPLOYMENT_ID, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, STATUSES_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, STATUSES_PATH, "per_page", "10");
    }

    @Test
    void getDeploymentStatus_shouldReturnStatus_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                STATUS_PATH,
                okJson(jsonBytes(GitHubDeploymentsStatusesApiIntegrationTest.class, "deployment-status.json")));

        final Optional<GitHubDeploymentStatus> status;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            status = api.api().statuses().getDeploymentStatus(OWNER, REPO, DEPLOYMENT_ID, STATUS_ID);
        }

        assertThat(status).contains(expectedStatus());
        HTTP.verifyRequested(GET, STATUS_PATH);
    }

    @Test
    void getDeploymentStatus_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, STATUS_PATH, notFound());

        final Optional<GitHubDeploymentStatus> status;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            status = api.api().statuses().getDeploymentStatus(OWNER, REPO, DEPLOYMENT_ID, STATUS_ID);
        }

        assertThat(status).isEmpty();
        HTTP.verifyRequested(GET, STATUS_PATH);
    }

    @Test
    void createDeploymentStatus_shouldReturnStatus_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                STATUSES_PATH,
                createdJson(jsonBytes(GitHubDeploymentsStatusesApiIntegrationTest.class, "deployment-status.json")));
        final GitHubCreateDeploymentStatusPayload payload = DeploymentsCatalogFixtures.createDeploymentStatusPayload();

        final GitHubDeploymentStatus status;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            status = api.api().statuses().createDeploymentStatus(OWNER, REPO, DEPLOYMENT_ID, payload);
        }

        assertThat(status).isEqualTo(expectedStatus());
        HTTP.verifyRequested(POST, STATUSES_PATH);
        HTTP.verifyHeader(POST, STATUSES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, STATUSES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    private void stubPagedList(final String path, final String fixture) {
        HTTP.stub(
                GET,
                path,
                okJson(listJsonBytes(GitHubDeploymentsStatusesApiIntegrationTest.class, fixture))
                        .header(LINK, linkNext(HTTP.uri(path + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubDeploymentsStatusesApiIntegrationTest.class, fixture)));
    }

    private static List<GitHubDeploymentStatus> expectedStatuses() {
        return CODEC.decode(
                listJsonBytes(GitHubDeploymentsStatusesApiIntegrationTest.class, "deployment-status.json"),
                STATUSES_TYPE);
    }

    private static GitHubDeploymentStatus expectedStatus() {
        return CODEC.decode(
                jsonBytes(GitHubDeploymentsStatusesApiIntegrationTest.class, "deployment-status.json"),
                GitHubDeploymentStatus.class);
    }
}
