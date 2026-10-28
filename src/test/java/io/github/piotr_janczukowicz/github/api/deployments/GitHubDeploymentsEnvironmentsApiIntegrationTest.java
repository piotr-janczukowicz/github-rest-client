package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
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
class GitHubDeploymentsEnvironmentsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ENVIRONMENT = "staging";

    private static final String ENVIRONMENTS_PATH = "/repos/octocat/Hello-World/environments";
    private static final String ENVIRONMENT_PATH = "/repos/octocat/Hello-World/environments/staging";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getAllEnvironments_shouldReturnEnvironments_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ENVIRONMENTS_PATH, okJson(wrappedPage("environments", "environment.json")));

        final GitHubPage<GitHubEnvironment> page;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            page = api.api().environments().getAllEnvironments(OWNER, REPO);
        }

        assertThat(page.items()).isEqualTo(expectedEnvironmentsPage().environments());
        assertThat(page.totalCount()).isEqualTo(1);
        HTTP.verifyRequested(GET, ENVIRONMENTS_PATH);
        HTTP.verifyQueryParam(GET, ENVIRONMENTS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, ENVIRONMENTS_PATH, "per_page", "100");
    }

    @Test
    void getAllEnvironments_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(GET, ENVIRONMENTS_PATH, okJson(wrappedPage("environments", "environment.json")));

        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            api.api().environments().getAllEnvironments(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, ENVIRONMENTS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, ENVIRONMENTS_PATH, "per_page", "10");
    }

    @Test
    void getEnvironment_shouldReturnEnvironment_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ENVIRONMENT_PATH,
                okJson(jsonBytes(GitHubDeploymentsEnvironmentsApiIntegrationTest.class, "environment.json")));

        final Optional<GitHubEnvironment> environment;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            environment = api.api().environments().getEnvironment(OWNER, REPO, ENVIRONMENT);
        }

        assertThat(environment).contains(expectedEnvironment());
        HTTP.verifyRequested(GET, ENVIRONMENT_PATH);
    }

    @Test
    void getEnvironment_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ENVIRONMENT_PATH, notFound());

        final Optional<GitHubEnvironment> environment;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            environment = api.api().environments().getEnvironment(OWNER, REPO, ENVIRONMENT);
        }

        assertThat(environment).isEmpty();
        HTTP.verifyRequested(GET, ENVIRONMENT_PATH);
    }

    @Test
    void createOrUpdateEnvironment_shouldReturnEnvironment_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                ENVIRONMENT_PATH,
                okJson(jsonBytes(GitHubDeploymentsEnvironmentsApiIntegrationTest.class, "environment.json")));
        final GitHubCreateOrUpdateEnvironmentPayload payload =
                DeploymentsCatalogFixtures.createOrUpdateEnvironmentPayload();

        final GitHubEnvironment environment;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            environment = api.api().environments().createOrUpdateEnvironment(OWNER, REPO, ENVIRONMENT, payload);
        }

        assertThat(environment).isEqualTo(expectedEnvironment());
        HTTP.verifyRequested(PUT, ENVIRONMENT_PATH);
        HTTP.verifyHeader(PUT, ENVIRONMENT_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, ENVIRONMENT_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void createOrUpdateEnvironment_shouldPutWithoutBody_whenPayloadNull() throws Exception {
        HTTP.stub(
                PUT,
                ENVIRONMENT_PATH,
                okJson(jsonBytes(GitHubDeploymentsEnvironmentsApiIntegrationTest.class, "environment.json")));

        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            api.api().environments().createOrUpdateEnvironment(OWNER, REPO, ENVIRONMENT, null);
        }

        HTTP.verifyRequested(PUT, ENVIRONMENT_PATH);
    }

    @Test
    void deleteAnEnvironment_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ENVIRONMENT_PATH, noContent());

        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            api.api().environments().deleteAnEnvironment(OWNER, REPO, ENVIRONMENT);
        }

        HTTP.verifyRequested(DELETE, ENVIRONMENT_PATH);
    }

    private static byte[] wrappedPage(final String arrayField, final String fixture) {
        final String item = new String(
                jsonBytes(GitHubDeploymentsEnvironmentsApiIntegrationTest.class, fixture), StandardCharsets.UTF_8);
        return ("{\"total_count\":1,\"" + arrayField + "\":[" + item + "]}").getBytes(StandardCharsets.UTF_8);
    }

    private static GitHubEnvironmentsPage expectedEnvironmentsPage() {
        return CODEC.decode(wrappedPage("environments", "environment.json"), GitHubEnvironmentsPage.class);
    }

    private static GitHubEnvironment expectedEnvironment() {
        return CODEC.decode(
                jsonBytes(GitHubDeploymentsEnvironmentsApiIntegrationTest.class, "environment.json"),
                GitHubEnvironment.class);
    }
}
