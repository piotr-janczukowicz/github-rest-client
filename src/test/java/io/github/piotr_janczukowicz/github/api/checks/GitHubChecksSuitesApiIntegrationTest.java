package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.checksApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
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
class GitHubChecksSuitesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int CHECK_SUITE_ID = 5;

    private static final String SUITES_PATH = "/repos/octocat/Hello-World/check-suites";
    private static final String SUITE_PATH = "/repos/octocat/Hello-World/check-suites/5";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void createSuite_shouldReturnCheckSuite_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                SUITES_PATH,
                createdJson(jsonBytes(GitHubChecksSuitesApiIntegrationTest.class, "check-suite.json")));

        final GitHubCheckSuite suite;
        try (final GitHubApiWireMockSupport.ChecksApiClient api = checksApi(HTTP)) {
            suite = api.api().suites().createSuite(OWNER, REPO, ChecksCatalogFixtures.createSuitePayload());
        }

        assertThat(suite).isEqualTo(ChecksCatalogFixtures.checkSuite());
        HTTP.verifyRequested(POST, SUITES_PATH);
    }

    @Test
    void updateSuitesPreferences_shouldReturnPreference_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                SUITES_PATH + "/preferences",
                okJson(jsonBytes(GitHubChecksSuitesApiIntegrationTest.class, "check-suite-preference.json")));

        final GitHubCheckSuitePreference preference;
        try (final GitHubApiWireMockSupport.ChecksApiClient api = checksApi(HTTP)) {
            preference = api.api()
                    .suites()
                    .updateSuitesPreferences(OWNER, REPO, ChecksCatalogFixtures.setSuitesPreferencesPayload());
        }

        assertThat(preference).isEqualTo(ChecksCatalogFixtures.checkSuitePreference());
        HTTP.verifyRequested(PATCH, SUITES_PATH + "/preferences");
    }

    @Test
    void getSuite_shouldReturnCheckSuite_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, SUITE_PATH, okJson(jsonBytes(GitHubChecksSuitesApiIntegrationTest.class, "check-suite.json")));

        final GitHubCheckSuite suite;
        try (final GitHubApiWireMockSupport.ChecksApiClient api = checksApi(HTTP)) {
            suite = api.api().suites().getSuite(OWNER, REPO, CHECK_SUITE_ID).orElseThrow();
        }

        assertThat(suite).isEqualTo(ChecksCatalogFixtures.checkSuite());
        HTTP.verifyRequested(GET, SUITE_PATH);
    }

    @Test
    void rerequestSuite_shouldSendPost_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, SUITE_PATH + "/rerequest", createdJson(new byte[0]));

        try (final GitHubApiWireMockSupport.ChecksApiClient api = checksApi(HTTP)) {
            api.api().suites().rerequestSuite(OWNER, REPO, CHECK_SUITE_ID);
        }

        HTTP.verifyRequested(POST, SUITE_PATH + "/rerequest");
    }

    @Test
    void listSuitesForRef_shouldReturnCheckSuites_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/repos/octocat/Hello-World/commits/main/check-suites",
                okJson(jsonBytes(GitHubChecksSuitesApiIntegrationTest.class, "check-suites-page.json")));

        final GitHubPage<GitHubCheckSuite> page;
        try (final GitHubApiWireMockSupport.ChecksApiClient api = checksApi(HTTP)) {
            page = api.api()
                    .suites()
                    .listSuitesForRef(
                            OWNER,
                            REPO,
                            "main",
                            ChecksCatalogFixtures.listSuitesForRefFilter(),
                            GitHubPageRequest.byPage());
        }

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ChecksCatalogFixtures.checkSuite());
            softly.assertThat(page.totalCount()).isEqualTo(1);
        });
        HTTP.verifyQueryParam(GET, "/repos/octocat/Hello-World/commits/main/check-suites", "app_id", "1");
        HTTP.verifyQueryParam(
                GET, "/repos/octocat/Hello-World/commits/main/check-suites", "check_name", "mighty_readme");
    }
}
