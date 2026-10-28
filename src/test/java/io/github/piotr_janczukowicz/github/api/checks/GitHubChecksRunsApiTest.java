package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubChecksRunsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long CHECK_RUN_ID = 42L;
    private static final int CHECK_SUITE_ID = 5;
    private static final String RUNS_PATH = "https://api.github.com/repos/octocat/Hello-World/check-runs";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubChecksRunsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void createCheckRun_shouldReturnCheckRun_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("check-run.json")));

        final GitHubCheckRun checkRun =
                api().createCheckRun(OWNER, REPO, ChecksCatalogFixtures.createCheckRunPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(checkRun).isEqualTo(ChecksCatalogFixtures.checkRun());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString(RUNS_PATH);
        });
    }

    @Test
    void getCheckRun_shouldReturnCheckRun_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("check-run.json")));

        final GitHubCheckRun checkRun =
                api().getCheckRun(OWNER, REPO, CHECK_RUN_ID).orElseThrow();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(checkRun).isEqualTo(ChecksCatalogFixtures.checkRun());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(RUNS_PATH + "/42");
        });
    }

    @Test
    void getCheckRun_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCheckRun> result = api().getCheckRun(OWNER, REPO, CHECK_RUN_ID);

        assertThat(result).isEmpty();
    }

    @Test
    void updateCheckRun_shouldReturnCheckRun_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("check-run.json")));

        final GitHubCheckRun checkRun =
                api().updateCheckRun(OWNER, REPO, CHECK_RUN_ID, ChecksCatalogFixtures.updateCheckRunPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(checkRun).isEqualTo(ChecksCatalogFixtures.checkRun());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString(RUNS_PATH + "/42");
        });
    }

    @Test
    void listAnnotations_shouldReturnAnnotations_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("check-annotation.json") + "]"));

        final GitHubPage<GitHubCheckAnnotation> page = api().listAnnotations(OWNER, REPO, CHECK_RUN_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ChecksCatalogFixtures.checkAnnotation());
            softly.assertThat(requestUri)
                    .hasToString(RUNS_PATH + "/42/annotations?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void rerequestRun_shouldSendPost_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), ""));

        api().rerequestRun(OWNER, REPO, CHECK_RUN_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString(RUNS_PATH + "/42/rerequest");
        });
    }

    @Test
    void listForSuite_shouldReturnCheckRuns_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("check-runs-page.json")));

        final GitHubPage<GitHubCheckRun> page = api().listForSuite(OWNER, REPO, CHECK_SUITE_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ChecksCatalogFixtures.checkRun());
            softly.assertThat(page.totalCount()).isEqualTo(1);
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/check-suites/5/check-runs?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listForSuite_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("check-runs-page.json")));

        api().listForSuite(
                        OWNER,
                        REPO,
                        CHECK_SUITE_ID,
                        ChecksCatalogFixtures.listForSuiteFilter(),
                        GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("check_name", "mighty_readme");
            softly.assertThat(requestUri).hasParameter("status", "completed");
            softly.assertThat(requestUri).hasParameter("filter", "latest");
        });
    }

    @Test
    void listForRef_shouldKeepSlashesInRef_whenRefHasSlashes() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("check-runs-page.json")));

        final GitHubPage<GitHubCheckRun> page = api().listForRef(OWNER, REPO, "feature/x");
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ChecksCatalogFixtures.checkRun());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/commits/feature/x/check-runs?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listForRef_shouldSendAppIdFilter_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("check-runs-page.json")));

        api().listForRef(OWNER, REPO, "main", ChecksCatalogFixtures.listForRefFilter(), GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("app_id", "1");
    }

    @Test
    void listForSuite_shouldPutLongIdInPath_whenIdExceedsIntRange() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("check-runs-page.json")));

        api().listForSuite(OWNER, REPO, 3_000_000_000L);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString(
                        "https://api.github.com/repos/octocat/Hello-World/check-suites/3000000000/check-runs?page=1&"
                                + TestClients.perPageQuery());
    }

    private GitHubChecksRunsApi api() {
        return new GitHubChecksRunsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubChecksRunsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
