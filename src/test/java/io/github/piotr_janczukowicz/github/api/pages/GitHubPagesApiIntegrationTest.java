package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.accepted;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.forbidden;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.pagesApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.client.exception.GitHubForbiddenException;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
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
class GitHubPagesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long BUILD_ID = 1L;
    private static final String DEPLOYMENT_ID = "4fd754f7e594640989b406850d0bc8f06a121251";

    private static final String PAGES_PATH = "/repos/octocat/Hello-World/pages";
    private static final String BUILDS_PATH = "/repos/octocat/Hello-World/pages/builds";
    private static final String BUILD_ITEM_PATH = "/repos/octocat/Hello-World/pages/builds/1";
    private static final String BUILD_LATEST_PATH = "/repos/octocat/Hello-World/pages/builds/latest";
    private static final String HEALTH_PATH = "/repos/octocat/Hello-World/pages/health";
    private static final String DEPLOYMENTS_PATH = "/repos/octocat/Hello-World/pages/deployments";
    private static final String DEPLOYMENT_ITEM_PATH =
            "/repos/octocat/Hello-World/pages/deployments/4fd754f7e594640989b406850d0bc8f06a121251";
    private static final String DEPLOYMENT_CANCEL_PATH =
            "/repos/octocat/Hello-World/pages/deployments/4fd754f7e594640989b406850d0bc8f06a121251/cancel";

    private static final JsonType<List<GitHubPageBuild>> PAGE_BUILDS_TYPE = JsonTypes.listOf(GitHubPageBuild.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getPages_shouldReturnSite_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, PAGES_PATH, okJson(jsonBytes(GitHubPagesApiIntegrationTest.class, "pages-site.json")));

        final Optional<GitHubPagesSite> site;
        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            site = api.api().getPages(OWNER, REPO);
        }

        final GitHubPagesSite expected =
                CODEC.decode(jsonBytes(GitHubPagesApiIntegrationTest.class, "pages-site.json"), GitHubPagesSite.class);

        assertThat(site).contains(expected);
        HTTP.verifyRequested(GET, PAGES_PATH);
    }

    @Test
    void getPages_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, PAGES_PATH, notFound());

        final Optional<GitHubPagesSite> site;
        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            site = api.api().getPages(OWNER, REPO);
        }

        assertThat(site).isEmpty();
        HTTP.verifyRequested(GET, PAGES_PATH);
    }

    @Test
    void createPagesSite_shouldReturnSite_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, PAGES_PATH, createdJson(jsonBytes(GitHubPagesApiIntegrationTest.class, "pages-site.json")));
        final GitHubCreatePagesSitePayload payload = PagesCatalogFixtures.createPagesSitePayload();

        final GitHubPagesSite site;
        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            site = api.api().createPagesSite(OWNER, REPO, payload);
        }

        final GitHubPagesSite expected =
                CODEC.decode(jsonBytes(GitHubPagesApiIntegrationTest.class, "pages-site.json"), GitHubPagesSite.class);

        assertThat(site).isEqualTo(expected);
        HTTP.verifyRequested(POST, PAGES_PATH);
        HTTP.verifyHeader(POST, PAGES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, PAGES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updateInformationAboutPagesSite_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, PAGES_PATH, noContent());
        final GitHubUpdatePagesSitePayload payload = PagesCatalogFixtures.updatePagesSitePayload();

        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            api.api().updateInformationAboutPagesSite(OWNER, REPO, payload);
        }

        HTTP.verifyRequested(PUT, PAGES_PATH);
        HTTP.verifyHeader(PUT, PAGES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, PAGES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deletePagesSite_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, PAGES_PATH, noContent());

        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            api.api().deletePagesSite(OWNER, REPO);
        }

        HTTP.verifyRequested(DELETE, PAGES_PATH);
    }

    @Test
    void listPagesBuilds_shouldReturnBuilds_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, BUILDS_PATH, okJson(listJsonBytes(GitHubPagesApiIntegrationTest.class, "page-build.json")));

        final GitHubPage<GitHubPageBuild> page;
        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            page = api.api().listPagesBuilds(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedBuilds());
        HTTP.verifyRequested(GET, BUILDS_PATH);
        HTTP.verifyQueryParam(GET, BUILDS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, BUILDS_PATH, "per_page", "100");
    }

    @Test
    void listPagesBuilds_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                BUILDS_PATH,
                okJson(listJsonBytes(GitHubPagesApiIntegrationTest.class, "page-build.json"))
                        .header(LINK, linkNext(HTTP.uri(BUILDS_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubPagesApiIntegrationTest.class, "page-build.json")));

        final GitHubPage<GitHubPageBuild> first;
        final Optional<GitHubPage<GitHubPageBuild>> second;
        final Optional<GitHubPage<GitHubPageBuild>> third;
        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            first = api.api().listPagesBuilds(OWNER, REPO);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedBuilds());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedBuilds());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, BUILDS_PATH, 2);
    }

    @Test
    void listPagesBuilds_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(GET, BUILDS_PATH, okJson(listJsonBytes(GitHubPagesApiIntegrationTest.class, "page-build.json")));

        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            api.api().listPagesBuilds(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, BUILDS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, BUILDS_PATH, "per_page", "10");
    }

    @Test
    void getPagesBuild_shouldReturnBuild_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, BUILD_ITEM_PATH, okJson(jsonBytes(GitHubPagesApiIntegrationTest.class, "page-build.json")));

        final GitHubPageBuild build;
        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            build = api.api().getPagesBuild(OWNER, REPO, BUILD_ID).orElseThrow();
        }

        final GitHubPageBuild expected =
                CODEC.decode(jsonBytes(GitHubPagesApiIntegrationTest.class, "page-build.json"), GitHubPageBuild.class);

        assertThat(build).isEqualTo(expected);
        HTTP.verifyRequested(GET, BUILD_ITEM_PATH);
    }

    @Test
    void getLatestPagesBuild_shouldReturnBuild_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, BUILD_LATEST_PATH, okJson(jsonBytes(GitHubPagesApiIntegrationTest.class, "page-build.json")));

        final GitHubPageBuild build;
        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            build = api.api().getLatestPagesBuild(OWNER, REPO);
        }

        final GitHubPageBuild expected =
                CODEC.decode(jsonBytes(GitHubPagesApiIntegrationTest.class, "page-build.json"), GitHubPageBuild.class);

        assertThat(build).isEqualTo(expected);
        HTTP.verifyRequested(GET, BUILD_LATEST_PATH);
    }

    @Test
    void requestPagesBuild_shouldReturnStatus_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                BUILDS_PATH,
                createdJson(jsonBytes(GitHubPagesApiIntegrationTest.class, "page-build-status.json")));

        final GitHubPageBuildStatus status;
        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            status = api.api().requestPagesBuild(OWNER, REPO);
        }

        final GitHubPageBuildStatus expected = CODEC.decode(
                jsonBytes(GitHubPagesApiIntegrationTest.class, "page-build-status.json"), GitHubPageBuildStatus.class);

        assertThat(status).isEqualTo(expected);
        HTTP.verifyRequested(POST, BUILDS_PATH);
    }

    @Test
    void getPagesHealthCheck_shouldReturnHealth_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, HEALTH_PATH, okJson(jsonBytes(GitHubPagesApiIntegrationTest.class, "pages-health-check.json")));

        final Optional<GitHubPagesHealthCheck> health;
        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            health = api.api().getPagesHealthCheck(OWNER, REPO);
        }

        final GitHubPagesHealthCheck expected = CODEC.decode(
                jsonBytes(GitHubPagesApiIntegrationTest.class, "pages-health-check.json"),
                GitHubPagesHealthCheck.class);

        assertThat(health).contains(expected);
        HTTP.verifyRequested(GET, HEALTH_PATH);
    }

    @Test
    void getPagesHealthCheck_shouldReturnEmpty_whenResponseIsAccepted() throws Exception {
        HTTP.stub(GET, HEALTH_PATH, accepted());

        final Optional<GitHubPagesHealthCheck> health;
        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            health = api.api().getPagesHealthCheck(OWNER, REPO);
        }

        assertThat(health).isEmpty();
        HTTP.verifyRequested(GET, HEALTH_PATH);
    }

    @Test
    void getPagesHealthCheck_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, HEALTH_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            final GitHubPagesApi facade = api.api();

            thrown = catchThrowable(() -> facade.getPagesHealthCheck(OWNER, REPO));
        }

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, HEALTH_PATH);
    }

    @Test
    void getPagesHealthCheck_shouldThrow_whenResponseIsForbidden() throws Exception {
        HTTP.stub(GET, HEALTH_PATH, forbidden());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            final GitHubPagesApi facade = api.api();

            thrown = catchThrowable(() -> facade.getPagesHealthCheck(OWNER, REPO));
        }

        assertThat(thrown).isInstanceOf(GitHubForbiddenException.class);
    }

    @Test
    void createPagesDeployment_shouldReturnDeployment_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                DEPLOYMENTS_PATH,
                okJson(jsonBytes(GitHubPagesApiIntegrationTest.class, "pages-deployment.json")));
        final GitHubCreatePagesDeploymentPayload payload = PagesCatalogFixtures.createPagesDeploymentPayload();

        final GitHubPagesDeployment deployment;
        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            deployment = api.api().createPagesDeployment(OWNER, REPO, payload);
        }

        final GitHubPagesDeployment expected = CODEC.decode(
                jsonBytes(GitHubPagesApiIntegrationTest.class, "pages-deployment.json"), GitHubPagesDeployment.class);

        assertThat(deployment).isEqualTo(expected);
        HTTP.verifyRequested(POST, DEPLOYMENTS_PATH);
        HTTP.verifyHeader(POST, DEPLOYMENTS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, DEPLOYMENTS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getPagesDeployment_shouldReturnStatus_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                DEPLOYMENT_ITEM_PATH,
                okJson(jsonBytes(GitHubPagesApiIntegrationTest.class, "pages-deployment-status.json")));

        final Optional<GitHubPagesDeploymentStatusResponse> status;
        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            status = api.api().getPagesDeployment(OWNER, REPO, DEPLOYMENT_ID);
        }

        final GitHubPagesDeploymentStatusResponse expected = CODEC.decode(
                jsonBytes(GitHubPagesApiIntegrationTest.class, "pages-deployment-status.json"),
                GitHubPagesDeploymentStatusResponse.class);

        assertThat(status).contains(expected);
        HTTP.verifyRequested(GET, DEPLOYMENT_ITEM_PATH);
    }

    @Test
    void getPagesDeployment_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, DEPLOYMENT_ITEM_PATH, notFound());

        final Optional<GitHubPagesDeploymentStatusResponse> status;
        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            status = api.api().getPagesDeployment(OWNER, REPO, DEPLOYMENT_ID);
        }

        assertThat(status).isEmpty();
        HTTP.verifyRequested(GET, DEPLOYMENT_ITEM_PATH);
    }

    @Test
    void cancelPagesDeployment_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(POST, DEPLOYMENT_CANCEL_PATH, noContent());

        try (final GitHubApiWireMockSupport.PagesApiClient api = pagesApi(HTTP)) {
            api.api().cancelPagesDeployment(OWNER, REPO, DEPLOYMENT_ID);
        }

        HTTP.verifyRequested(POST, DEPLOYMENT_CANCEL_PATH);
    }

    private static List<GitHubPageBuild> expectedBuilds() {
        return CODEC.decode(listJsonBytes(GitHubPagesApiIntegrationTest.class, "page-build.json"), PAGE_BUILDS_TYPE);
    }
}
