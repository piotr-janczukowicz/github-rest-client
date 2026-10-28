package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.acceptedJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.codeScanningApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
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
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCodeScanningCodeScanningApiIntegrationTest {

    private static final String REPO_PATH = "/repos/octocat/Hello-World/code-scanning";
    private static final String ALERTS_PATH = REPO_PATH + "/alerts";
    private static final String ALERT_PATH = ALERTS_PATH + "/1";
    private static final String DEFAULT_SETUP_PATH = REPO_PATH + "/default-setup";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listAlertsForRepo_shouldSendFilterAndPage_whenCalled() throws Exception {
        HTTP.stub(
                GET,
                ALERTS_PATH,
                okJson(listJsonBytes(
                        GitHubCodeScanningCodeScanningApiIntegrationTest.class, "code-scanning-alert-items.json")));

        final GitHubPage<GitHubCodeScanningAlertItems> page;
        try (final GitHubApiWireMockSupport.CodeScanningApiClient api = codeScanningApi(HTTP)) {
            page = api.api()
                    .listAlertsForRepo(
                            "octocat",
                            "Hello-World",
                            CodeScanningCatalogFixtures.listAlertsForRepoFilter(),
                            GitHubPageNumberRequest.of(2, 50));
        }

        assertThat(page.items()).containsExactly(CodeScanningCatalogFixtures.codeScanningAlertItems());
        HTTP.verifyRequested(GET, ALERTS_PATH);
        HTTP.verifyQueryParam(GET, ALERTS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, ALERTS_PATH, "per_page", "50");
    }

    @Test
    void getAlert_shouldReturnAlert_whenOk() throws Exception {
        HTTP.stub(
                GET,
                ALERT_PATH,
                okJson(jsonBytes(GitHubCodeScanningCodeScanningApiIntegrationTest.class, "code-scanning-alert.json")));

        final GitHubCodeScanningAlert alert;
        try (final GitHubApiWireMockSupport.CodeScanningApiClient api = codeScanningApi(HTTP)) {
            alert = api.api().getAlert("octocat", "Hello-World", 1).orElseThrow();
        }

        assertThat(alert).isEqualTo(CodeScanningCatalogFixtures.codeScanningAlert());
        HTTP.verifyRequested(GET, ALERT_PATH);
    }

    @Test
    void updateAlert_shouldSendPatch_whenOk() throws Exception {
        HTTP.stub(
                PATCH,
                ALERT_PATH,
                okJson(jsonBytes(GitHubCodeScanningCodeScanningApiIntegrationTest.class, "code-scanning-alert.json")));

        final GitHubCodeScanningAlert alert;
        try (final GitHubApiWireMockSupport.CodeScanningApiClient api = codeScanningApi(HTTP)) {
            alert = api.api()
                    .updateAlert("octocat", "Hello-World", 1, CodeScanningCatalogFixtures.updateAlertPayload());
        }

        assertThat(alert).isEqualTo(CodeScanningCatalogFixtures.codeScanningAlert());
        HTTP.verifyRequested(PATCH, ALERT_PATH);
    }

    @Test
    void createAutofix_shouldReturnAutofix_whenAccepted() throws Exception {
        HTTP.stub(
                POST,
                ALERT_PATH + "/autofix",
                acceptedJson(jsonBytes(
                        GitHubCodeScanningCodeScanningApiIntegrationTest.class, "code-scanning-autofix.json")));

        final GitHubCodeScanningAutofix autofix;
        try (final GitHubApiWireMockSupport.CodeScanningApiClient api = codeScanningApi(HTTP)) {
            autofix = api.api().createAutofix("octocat", "Hello-World", 1);
        }

        assertThat(autofix).isEqualTo(CodeScanningCatalogFixtures.codeScanningAutofix());
        HTTP.verifyRequested(POST, ALERT_PATH + "/autofix");
    }

    @Test
    void deleteAnalysis_shouldSendDelete_whenOk() throws Exception {
        HTTP.stub(
                DELETE,
                REPO_PATH + "/analyses/6543210",
                okJson(jsonBytes(
                        GitHubCodeScanningCodeScanningApiIntegrationTest.class,
                        "code-scanning-analysis-deletion.json")));

        final GitHubCodeScanningAnalysisDeletion deletion;
        try (final GitHubApiWireMockSupport.CodeScanningApiClient api = codeScanningApi(HTTP)) {
            deletion = api.api().deleteAnalysis("octocat", "Hello-World", 6_543_210L);
        }

        assertThat(deletion).isEqualTo(CodeScanningCatalogFixtures.codeScanningAnalysisDeletion());
        HTTP.verifyRequested(DELETE, REPO_PATH + "/analyses/6543210");
    }

    @Test
    void deleteCodeqlDatabase_shouldSendDelete_whenNoContent() throws Exception {
        HTTP.stub(DELETE, REPO_PATH + "/codeql/databases/javascript", noContent());

        try (final GitHubApiWireMockSupport.CodeScanningApiClient api = codeScanningApi(HTTP)) {
            api.api().deleteCodeqlDatabase("octocat", "Hello-World", "javascript");
        }

        HTTP.verifyRequested(DELETE, REPO_PATH + "/codeql/databases/javascript");
    }

    @Test
    void getCodeqlDatabase_shouldReturnEmpty_whenNotFound() throws Exception {
        HTTP.stub(GET, REPO_PATH + "/codeql/databases/javascript", notFound());

        final Optional<GitHubCodeScanningCodeqlDatabase> database;
        try (final GitHubApiWireMockSupport.CodeScanningApiClient api = codeScanningApi(HTTP)) {
            database = api.api().getCodeqlDatabase("octocat", "Hello-World", "javascript");
        }

        assertThat(database).isEmpty();
        HTTP.verifyRequested(GET, REPO_PATH + "/codeql/databases/javascript");
    }

    @Test
    void listCodeqlDatabases_shouldReturnDatabases_whenOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_PATH + "/codeql/databases",
                okJson(listJsonBytes(
                        GitHubCodeScanningCodeScanningApiIntegrationTest.class, "code-scanning-codeql-database.json")));

        final List<GitHubCodeScanningCodeqlDatabase> databases;
        try (final GitHubApiWireMockSupport.CodeScanningApiClient api = codeScanningApi(HTTP)) {
            databases = api.api().listCodeqlDatabases("octocat", "Hello-World");
        }

        assertThat(databases).containsExactly(CodeScanningCatalogFixtures.codeScanningCodeqlDatabase());
    }

    @Test
    void updateDefaultSetup_shouldReturnEmpty_whenOkWithEmptyObject() throws Exception {
        HTTP.stub(PATCH, DEFAULT_SETUP_PATH, okJson("{}".getBytes(StandardCharsets.UTF_8)));

        final Optional<GitHubCodeScanningDefaultSetupUpdateResponse> response;
        try (final GitHubApiWireMockSupport.CodeScanningApiClient api = codeScanningApi(HTTP)) {
            response = api.api()
                    .updateDefaultSetup(
                            "octocat", "Hello-World", CodeScanningCatalogFixtures.codeScanningDefaultSetupUpdate());
        }

        assertThat(response).isEmpty();
        HTTP.verifyRequested(PATCH, DEFAULT_SETUP_PATH);
    }

    @Test
    void uploadSarif_shouldReturnReceipt_whenAccepted() throws Exception {
        HTTP.stub(
                POST,
                REPO_PATH + "/sarifs",
                acceptedJson(jsonBytes(
                        GitHubCodeScanningCodeScanningApiIntegrationTest.class, "code-scanning-sarifs-receipt.json")));

        final GitHubCodeScanningSarifsReceipt receipt;
        try (final GitHubApiWireMockSupport.CodeScanningApiClient api = codeScanningApi(HTTP)) {
            receipt = api.api().uploadSarif("octocat", "Hello-World", CodeScanningCatalogFixtures.uploadSarifPayload());
        }

        assertThat(receipt).isEqualTo(CodeScanningCatalogFixtures.codeScanningSarifsReceipt());
        HTTP.verifyRequested(POST, REPO_PATH + "/sarifs");
    }
}
