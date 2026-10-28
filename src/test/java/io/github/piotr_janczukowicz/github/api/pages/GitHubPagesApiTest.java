package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubPagesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long BUILD_ID = 1L;
    private static final String DEPLOYMENT_ID = "4fd754f7e594640989b406850d0bc8f06a121251";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubPagesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getPages_shouldReturnSite_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pages-site.json")));

        final Optional<GitHubPagesSite> site = api().getPages(OWNER, REPO);

        assertThat(site).contains(PagesCatalogFixtures.pagesSite());
    }

    @Test
    void getPages_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubPagesSite> site = api().getPages(OWNER, REPO);

        assertThat(site).isEmpty();
    }

    @Test
    void createPagesSite_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("pages-site.json")));
        final GitHubCreatePagesSitePayload payload = PagesCatalogFixtures.createPagesSitePayload();

        final GitHubPagesSite site = api().createPagesSite(OWNER, REPO, payload);

        assertThat(site).isEqualTo(PagesCatalogFixtures.pagesSite());
    }

    @Test
    void updateInformationAboutPagesSite_shouldPutPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubUpdatePagesSitePayload payload = PagesCatalogFixtures.updatePagesSitePayload();

        api().updateInformationAboutPagesSite(OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void deletePagesSite_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deletePagesSite(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/pages");
    }

    @Test
    void listPagesBuilds_shouldReturnBuilds_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("page-build.json") + "]"));

        final GitHubPage<GitHubPageBuild> page = api().listPagesBuilds(OWNER, REPO, GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(PagesCatalogFixtures.pageBuild());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void getPagesBuild_shouldReturnBuild_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("page-build.json")));

        final GitHubPageBuild build = api().getPagesBuild(OWNER, REPO, BUILD_ID).orElseThrow();

        assertThat(build).isEqualTo(PagesCatalogFixtures.pageBuild());
    }

    @Test
    void getPagesBuild_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubPageBuild> build = api().getPagesBuild(OWNER, REPO, BUILD_ID);

        assertThat(build).isEmpty();
    }

    @Test
    void getLatestPagesBuild_shouldReturnBuild_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("page-build.json")));

        final GitHubPageBuild build = api().getLatestPagesBuild(OWNER, REPO);

        assertThat(build).isEqualTo(PagesCatalogFixtures.pageBuild());
    }

    @Test
    void requestPagesBuild_shouldPost_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("page-build-status.json")));

        final GitHubPageBuildStatus status = api().requestPagesBuild(OWNER, REPO);

        assertThat(status).isEqualTo(PagesCatalogFixtures.pageBuildStatus());
    }

    @Test
    void getPagesHealthCheck_shouldReturnEmpty_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        final Optional<GitHubPagesHealthCheck> health = api().getPagesHealthCheck(OWNER, REPO);

        assertThat(health).isEmpty();
    }

    @Test
    void getPagesHealthCheck_shouldReturnHealth_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pages-health-check.json")));

        final Optional<GitHubPagesHealthCheck> health = api().getPagesHealthCheck(OWNER, REPO);

        assertThat(health).contains(PagesCatalogFixtures.pagesHealthCheck());
    }

    @Test
    void createPagesDeployment_shouldPostPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pages-deployment.json")));
        final GitHubCreatePagesDeploymentPayload payload = PagesCatalogFixtures.createPagesDeploymentPayload();

        final GitHubPagesDeployment deployment = api().createPagesDeployment(OWNER, REPO, payload);

        assertThat(deployment).isEqualTo(PagesCatalogFixtures.pagesDeployment());
    }

    @Test
    void getPagesDeployment_shouldReturnStatus_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pages-deployment-status.json")));

        final Optional<GitHubPagesDeploymentStatusResponse> status =
                api().getPagesDeployment(OWNER, REPO, DEPLOYMENT_ID);

        assertThat(status).contains(PagesCatalogFixtures.pagesDeploymentStatusResponse());
    }

    @Test
    void getPagesDeployment_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubPagesDeploymentStatusResponse> status =
                api().getPagesDeployment(OWNER, REPO, DEPLOYMENT_ID);

        assertThat(status).isEmpty();
    }

    @Test
    void cancelPagesDeployment_shouldPost_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().cancelPagesDeployment(OWNER, REPO, DEPLOYMENT_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri())
                .hasToString(
                        "https://api.github.com/repos/octocat/Hello-World/pages/deployments/4fd754f7e594640989b406850d0bc8f06a121251/cancel");
    }

    @Test
    void listPagesBuilds_shouldReturnBuilds_whenDefaultPagination() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("page-build.json") + "]"));

        final GitHubPage<GitHubPageBuild> page = api().listPagesBuilds(OWNER, REPO);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(PagesCatalogFixtures.pageBuild());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void getPagesHealthCheck_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));
        final GitHubPagesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getPagesHealthCheck(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void createPagesSite_shouldThrow_whenPayloadNull() {
        final GitHubPagesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createPagesSite(OWNER, REPO, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    private GitHubPagesApi api() {
        return new GitHubPagesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubPagesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
