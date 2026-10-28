package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.checksApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubChecksRunsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long CHECK_RUN_ID = 42L;
    private static final int CHECK_SUITE_ID = 5;

    private static final String RUNS_PATH = "/repos/octocat/Hello-World/check-runs";
    private static final String RUN_PATH = "/repos/octocat/Hello-World/check-runs/42";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void createCheckRun_shouldReturnCheckRun_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, RUNS_PATH, createdJson(jsonBytes(GitHubChecksRunsApiIntegrationTest.class, "check-run.json")));

        final GitHubCheckRun checkRun;
        try (final GitHubApiWireMockSupport.ChecksApiClient api = checksApi(HTTP)) {
            checkRun = api.api().runs().createCheckRun(OWNER, REPO, ChecksCatalogFixtures.createCheckRunPayload());
        }

        assertThat(checkRun).isEqualTo(ChecksCatalogFixtures.checkRun());
        HTTP.verifyRequested(POST, RUNS_PATH);
    }

    @Test
    void getCheckRun_shouldReturnCheckRun_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, RUN_PATH, okJson(jsonBytes(GitHubChecksRunsApiIntegrationTest.class, "check-run.json")));

        final GitHubCheckRun checkRun;
        try (final GitHubApiWireMockSupport.ChecksApiClient api = checksApi(HTTP)) {
            checkRun = api.api().runs().getCheckRun(OWNER, REPO, CHECK_RUN_ID).orElseThrow();
        }

        assertThat(checkRun).isEqualTo(ChecksCatalogFixtures.checkRun());
        HTTP.verifyRequested(GET, RUN_PATH);
    }

    @Test
    void updateCheckRun_shouldReturnCheckRun_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, RUN_PATH, okJson(jsonBytes(GitHubChecksRunsApiIntegrationTest.class, "check-run.json")));

        final GitHubCheckRun checkRun;
        try (final GitHubApiWireMockSupport.ChecksApiClient api = checksApi(HTTP)) {
            checkRun = api.api()
                    .runs()
                    .updateCheckRun(OWNER, REPO, CHECK_RUN_ID, ChecksCatalogFixtures.updateCheckRunPayload());
        }

        assertThat(checkRun).isEqualTo(ChecksCatalogFixtures.checkRun());
        HTTP.verifyRequested(PATCH, RUN_PATH);
    }

    @Test
    void listAnnotations_shouldSendPageAndPerPage_whenCalled() throws Exception {
        HTTP.stub(
                GET,
                RUN_PATH + "/annotations",
                okJson(listJsonBytes(GitHubChecksRunsApiIntegrationTest.class, "check-annotation.json")));

        final GitHubPage<GitHubCheckAnnotation> page;
        try (final GitHubApiWireMockSupport.ChecksApiClient api = checksApi(HTTP)) {
            page = api.api().runs().listAnnotations(OWNER, REPO, CHECK_RUN_ID);
        }

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ChecksCatalogFixtures.checkAnnotation());
            softly.assertThat(page.totalCount()).isNull();
        });
        HTTP.verifyQueryParam(GET, RUN_PATH + "/annotations", "page", "1");
        HTTP.verifyQueryParam(GET, RUN_PATH + "/annotations", "per_page", "100");
    }

    @Test
    void rerequestRun_shouldSendPost_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, RUN_PATH + "/rerequest", createdJson(new byte[0]));

        try (final GitHubApiWireMockSupport.ChecksApiClient api = checksApi(HTTP)) {
            api.api().runs().rerequestRun(OWNER, REPO, CHECK_RUN_ID);
        }

        HTTP.verifyRequested(POST, RUN_PATH + "/rerequest");
    }

    @Test
    void listForSuite_shouldReturnCheckRunsAndSendQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                "/repos/octocat/Hello-World/check-suites/5/check-runs",
                okJson(jsonBytes(GitHubChecksRunsApiIntegrationTest.class, "check-runs-page.json")));

        final GitHubPage<GitHubCheckRun> page;
        try (final GitHubApiWireMockSupport.ChecksApiClient api = checksApi(HTTP)) {
            page = api.api()
                    .runs()
                    .listForSuite(
                            OWNER,
                            REPO,
                            CHECK_SUITE_ID,
                            ChecksCatalogFixtures.listForSuiteFilter(),
                            GitHubPageRequest.byPage());
        }

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ChecksCatalogFixtures.checkRun());
            softly.assertThat(page.totalCount()).isEqualTo(1);
        });
        HTTP.verifyQueryParam(
                GET, "/repos/octocat/Hello-World/check-suites/5/check-runs", "check_name", "mighty_readme");
        HTTP.verifyQueryParam(GET, "/repos/octocat/Hello-World/check-suites/5/check-runs", "filter", "latest");
    }

    @Test
    void listForRef_shouldEncodeSlashesInRef_whenRefHasSlashes() throws Exception {
        HTTP.stub(
                GET,
                "/repos/octocat/Hello-World/commits/feature/x/check-runs",
                okJson(jsonBytes(GitHubChecksRunsApiIntegrationTest.class, "check-runs-page.json")));

        final GitHubPage<GitHubCheckRun> page;
        try (final GitHubApiWireMockSupport.ChecksApiClient api = checksApi(HTTP)) {
            page = api.api().runs().listForRef(OWNER, REPO, "feature/x");
        }

        assertThat(page.items()).containsExactly(ChecksCatalogFixtures.checkRun());
        HTTP.verifyRequested(GET, "/repos/octocat/Hello-World/commits/feature/x/check-runs");
    }
}
