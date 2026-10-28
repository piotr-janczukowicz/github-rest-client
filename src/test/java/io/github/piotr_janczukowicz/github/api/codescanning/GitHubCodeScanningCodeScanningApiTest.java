package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LOCATION;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.http.HttpBody;
import io.github.piotr_janczukowicz.http.HttpHeaders;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpResponse;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCodeScanningCodeScanningApiTest {

    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int ALERT_NUMBER = 1;
    private static final long ANALYSIS_ID = 6_543_210L;
    private static final long VARIANT_ANALYSIS_ID = 1L;
    private static final String LANGUAGE = "javascript";
    private static final String SARIF_ID = "sarif-id";
    private static final String REPO_PATH = "https://api.github.com/repos/octocat/Hello-World/code-scanning";
    private static final URI ARCHIVE_LOCATION = URI.create("https://example.invalid/codeql-database.zip");

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodeScanningCodeScanningApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listAlertsForOrg_shouldSendFilterAndPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(), "[" + json("code-scanning-organization-alert-items.json") + "]"));

        final GitHubPage<GitHubCodeScanningOrganizationAlertItems> page = api().listAlertsForOrg(
                        ORG, CodeScanningCatalogFixtures.listAlertsForOrgFilter(), GitHubPageNumberRequest.of(2, 50));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(CodeScanningCatalogFixtures.codeScanningOrganizationAlertItems());
            softly.assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "50");
        });
    }

    @Test
    void listAlertsForOrg_shouldUseDefaultPage_whenNoRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listAlertsForOrg(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getPath()).isEqualTo("/orgs/octo-org/code-scanning/alerts");
    }

    @Test
    void listAlertsForRepo_shouldSendFilterAndPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("code-scanning-alert-items.json") + "]"));

        final GitHubPage<GitHubCodeScanningAlertItems> page = api().listAlertsForRepo(
                        OWNER,
                        REPO,
                        CodeScanningCatalogFixtures.listAlertsForRepoFilter(),
                        GitHubPageNumberRequest.of(2, 50));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(CodeScanningCatalogFixtures.codeScanningAlertItems());
            softly.assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "50");
        });
    }

    @Test
    void listAlertsForRepo_shouldUseDefaultPage_whenNoRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listAlertsForRepo(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/Hello-World/code-scanning/alerts");
    }

    @Test
    void getAlert_shouldReturnAlert_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("code-scanning-alert.json")));

        final GitHubCodeScanningAlert alert =
                api().getAlert(OWNER, REPO, ALERT_NUMBER).orElseThrow();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(alert).isEqualTo(CodeScanningCatalogFixtures.codeScanningAlert());
            softly.assertThat(requestUri).hasToString(REPO_PATH + "/alerts/1");
        });
    }

    @Test
    void getAlert_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCodeScanningAlert> result = api().getAlert(OWNER, REPO, ALERT_NUMBER);

        assertThat(result).isEmpty();
    }

    @Test
    void updateAlert_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("code-scanning-alert.json")));

        final GitHubCodeScanningAlert alert =
                api().updateAlert(OWNER, REPO, ALERT_NUMBER, CodeScanningCatalogFixtures.updateAlertPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(alert).isEqualTo(CodeScanningCatalogFixtures.codeScanningAlert());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString(REPO_PATH + "/alerts/1");
        });
    }

    @Test
    void getAutofix_shouldReturnAutofix_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("code-scanning-autofix.json")));

        final GitHubCodeScanningAutofix autofix =
                api().getAutofix(OWNER, REPO, ALERT_NUMBER).orElseThrow();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(autofix).isEqualTo(CodeScanningCatalogFixtures.codeScanningAutofix());
            softly.assertThat(requestUri).hasToString(REPO_PATH + "/alerts/1/autofix");
        });
    }

    @Test
    void getAutofix_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCodeScanningAutofix> result = api().getAutofix(OWNER, REPO, ALERT_NUMBER);

        assertThat(result).isEmpty();
    }

    @Test
    void createAutofix_shouldReturnAutofix_whenAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("code-scanning-autofix.json")));

        final GitHubCodeScanningAutofix autofix = api().createAutofix(OWNER, REPO, ALERT_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(autofix).isEqualTo(CodeScanningCatalogFixtures.codeScanningAutofix());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString(REPO_PATH + "/alerts/1/autofix");
        });
    }

    @Test
    void commitAutofix_shouldPostPayload_whenPayloadProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("code-scanning-autofix-commits-response.json")));

        final GitHubCodeScanningAutofixCommitsResponse response = api().commitAutofix(
                        OWNER, REPO, ALERT_NUMBER, CodeScanningCatalogFixtures.codeScanningAutofixCommits());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(response).isEqualTo(CodeScanningCatalogFixtures.codeScanningAutofixCommitsResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString(REPO_PATH + "/alerts/1/autofix/commits");
        });
    }

    @Test
    void commitAutofix_shouldSendNoBody_whenPayloadNull() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("code-scanning-autofix-commits-response.json")));

        api().commitAutofix(OWNER, REPO, ALERT_NUMBER, null);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.body().isAbsent()).isTrue();
    }

    @Test
    void listAlertInstances_shouldUseDefaultPage_whenNoFilterOrRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listAlertInstances(OWNER, REPO, ALERT_NUMBER);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/Hello-World/code-scanning/alerts/1/instances");
    }

    @Test
    void listAlertInstances_shouldSendPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("code-scanning-alert-instance-list.json") + "]"));

        final GitHubPage<GitHubCodeScanningAlertInstanceList> page = api().listAlertInstances(
                        OWNER,
                        REPO,
                        ALERT_NUMBER,
                        CodeScanningCatalogFixtures.listAlertInstancesFilter(),
                        GitHubPageNumberRequest.of(2, 50));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(CodeScanningCatalogFixtures.codeScanningAlertInstanceList());
            softly.assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "50");
        });
    }

    @Test
    void listRecentAnalyses_shouldSendFilterAndPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("code-scanning-analysis.json") + "]"));

        final GitHubPage<GitHubCodeScanningAnalysis> page = api().listRecentAnalyses(
                        OWNER,
                        REPO,
                        CodeScanningCatalogFixtures.listRecentAnalysesFilter(),
                        GitHubPageNumberRequest.of(2, 50));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(CodeScanningCatalogFixtures.codeScanningAnalysis());
            softly.assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "50");
        });
    }

    @Test
    void listRecentAnalyses_shouldUseDefaultPage_whenNoFilterOrRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listRecentAnalyses(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/Hello-World/code-scanning/analyses");
    }

    @Test
    void getAnalysis_shouldReturnAnalysis_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("code-scanning-analysis.json")));

        final GitHubCodeScanningAnalysis analysis =
                api().getAnalysis(OWNER, REPO, ANALYSIS_ID).orElseThrow();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(analysis).isEqualTo(CodeScanningCatalogFixtures.codeScanningAnalysis());
            softly.assertThat(requestUri).hasToString(REPO_PATH + "/analyses/6543210");
        });
    }

    @Test
    void getAnalysis_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCodeScanningAnalysis> result = api().getAnalysis(OWNER, REPO, ANALYSIS_ID);

        assertThat(result).isEmpty();
    }

    @Test
    void getAnalysisSarif_shouldAskForSarifMediaType_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "{\"version\":\"2.1.0\"}"));

        final Optional<String> sarif = api().getAnalysisSarif(OWNER, REPO, ANALYSIS_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(sarif).contains("{\"version\":\"2.1.0\"}");
            softly.assertThat(request.headers().get("Accept")).contains("application/sarif+json");
            softly.assertThat(request.uri()).hasToString(REPO_PATH + "/analyses/6543210");
        });
    }

    @Test
    void getAnalysisSarif_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<String> sarif = api().getAnalysisSarif(OWNER, REPO, ANALYSIS_ID);

        assertThat(sarif).isEmpty();
    }

    @Test
    void deleteAnalysis_shouldSendFilter_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-scanning-analysis-deletion.json")));

        final GitHubCodeScanningAnalysisDeletion deletion =
                api().deleteAnalysis(OWNER, REPO, ANALYSIS_ID, CodeScanningCatalogFixtures.deleteAnalysisFilter());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(deletion).isEqualTo(CodeScanningCatalogFixtures.codeScanningAnalysisDeletion());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasParameter("confirm_delete", "true");
        });
    }

    @Test
    void deleteAnalysis_shouldSendNoFilter_whenNoFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-scanning-analysis-deletion.json")));

        api().deleteAnalysis(OWNER, REPO, ANALYSIS_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasToString(REPO_PATH + "/analyses/6543210");
    }

    @Test
    void listCodeqlDatabases_shouldReturnDatabases_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("code-scanning-codeql-database.json") + "]"));

        final List<GitHubCodeScanningCodeqlDatabase> databases = api().listCodeqlDatabases(OWNER, REPO);

        assertThat(databases).containsExactly(CodeScanningCatalogFixtures.codeScanningCodeqlDatabase());
    }

    @Test
    void getCodeqlDatabase_shouldReturnDatabase_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-scanning-codeql-database.json")));

        final GitHubCodeScanningCodeqlDatabase database =
                api().getCodeqlDatabase(OWNER, REPO, LANGUAGE).orElseThrow();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(database).isEqualTo(CodeScanningCatalogFixtures.codeScanningCodeqlDatabase());
            softly.assertThat(requestUri).hasToString(REPO_PATH + "/codeql/databases/javascript");
        });
    }

    @Test
    void getCodeqlDatabase_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCodeScanningCodeqlDatabase> result = api().getCodeqlDatabase(OWNER, REPO, LANGUAGE);

        assertThat(result).isEmpty();
    }

    @Test
    void getCodeqlDatabaseDownloadLocation_shouldReturnLocation_whenResponseIsFound() throws Exception {
        when(transport.execute(any()))
                .thenReturn(redirectResponse(HttpStatus.FOUND.code(), ARCHIVE_LOCATION.toString()));

        final URI location = api().getCodeqlDatabaseDownloadLocation(OWNER, REPO, LANGUAGE);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(location).isEqualTo(ARCHIVE_LOCATION);
            softly.assertThat(request.headers().get(ACCEPT)).contains("application/zip");
        });
    }

    @Test
    void deleteCodeqlDatabase_shouldSendDelete_whenNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteCodeqlDatabase(OWNER, REPO, LANGUAGE);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString(REPO_PATH + "/codeql/databases/javascript");
        });
    }

    @Test
    void createVariantAnalysis_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("code-scanning-variant-analysis.json")));

        final GitHubCodeScanningVariantAnalysis analysis =
                api().createVariantAnalysis(OWNER, REPO, CodeScanningCatalogFixtures.createVariantAnalysisPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(analysis).isEqualTo(CodeScanningCatalogFixtures.codeScanningVariantAnalysis());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString(REPO_PATH + "/codeql/variant-analyses");
        });
    }

    @Test
    void getVariantAnalysis_shouldReturnAnalysis_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-scanning-variant-analysis.json")));

        final GitHubCodeScanningVariantAnalysis analysis =
                api().getVariantAnalysis(OWNER, REPO, VARIANT_ANALYSIS_ID).orElseThrow();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(analysis).isEqualTo(CodeScanningCatalogFixtures.codeScanningVariantAnalysis());
            softly.assertThat(requestUri).hasToString(REPO_PATH + "/codeql/variant-analyses/1");
        });
    }

    @Test
    void getVariantAnalysis_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCodeScanningVariantAnalysis> result =
                api().getVariantAnalysis(OWNER, REPO, VARIANT_ANALYSIS_ID);

        assertThat(result).isEmpty();
    }

    @Test
    void getVariantAnalysisRepoTask_shouldReturnTask_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-scanning-variant-analysis-repo-task.json")));

        final Optional<GitHubCodeScanningVariantAnalysisRepoTask> task =
                api().getVariantAnalysisRepoTask(OWNER, REPO, VARIANT_ANALYSIS_ID, ORG, "other-repo");
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(task).contains(CodeScanningCatalogFixtures.codeScanningVariantAnalysisRepoTask());
            softly.assertThat(requestUri)
                    .hasToString(REPO_PATH + "/codeql/variant-analyses/1/repos/octo-org/other-repo");
        });
    }

    @Test
    void getVariantAnalysisRepoTask_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCodeScanningVariantAnalysisRepoTask> result =
                api().getVariantAnalysisRepoTask(OWNER, REPO, VARIANT_ANALYSIS_ID, ORG, "other-repo");

        assertThat(result).isEmpty();
    }

    @Test
    void getAiScanEnablementForOrg_shouldReturnSettings_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-scanning-ai-scan-org-settings.json")));

        final GitHubCodeScanningAiScanOrgSettings settings = api().getAiScanEnablementForOrg(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(settings).isEqualTo(CodeScanningCatalogFixtures.codeScanningAiScanOrgSettings());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/octo-org/code-scanning/ai-scan");
        });
    }

    @Test
    void updateAiScanEnablementForOrg_shouldReturnSettings_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-scanning-ai-scan-org-settings.json")));

        final GitHubCodeScanningAiScanOrgSettings settings = api().updateAiScanEnablementForOrg(
                        ORG, CodeScanningCatalogFixtures.codeScanningAiScanOrgEnablementUpdate());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(settings).isEqualTo(CodeScanningCatalogFixtures.codeScanningAiScanOrgSettings());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/octo-org/code-scanning/ai-scan");
        });
    }

    @Test
    void updateAiScanEnablementForOrg_shouldThrow_whenPayloadNull() {
        final GitHubCodeScanningCodeScanningApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updateAiScanEnablementForOrg(ORG, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getAiScanEnablement_shouldReturnEnablement_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-scanning-ai-scan-enablement.json")));

        final GitHubCodeScanningAiScanEnablement enablement = api().getAiScanEnablement(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(enablement).isEqualTo(CodeScanningCatalogFixtures.codeScanningAiScanEnablement());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(REPO_PATH + "/ai-scan");
        });
    }

    @Test
    void updateAiScanEnablement_shouldReturnEnablement_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-scanning-ai-scan-enablement.json")));

        final GitHubCodeScanningAiScanEnablement enablement = api().updateAiScanEnablement(
                        OWNER, REPO, CodeScanningCatalogFixtures.codeScanningAiScanEnablementUpdate());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(enablement).isEqualTo(CodeScanningCatalogFixtures.codeScanningAiScanEnablement());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString(REPO_PATH + "/ai-scan");
        });
    }

    @Test
    void updateAiScanEnablement_shouldThrow_whenPayloadNull() {
        final GitHubCodeScanningCodeScanningApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updateAiScanEnablement(OWNER, REPO, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getDefaultSetup_shouldReturnSetup_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-scanning-default-setup.json")));

        final GitHubCodeScanningDefaultSetup setup = api().getDefaultSetup(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(setup).isEqualTo(CodeScanningCatalogFixtures.codeScanningDefaultSetup());
            softly.assertThat(requestUri).hasToString(REPO_PATH + "/default-setup");
        });
    }

    @Test
    void updateDefaultSetup_shouldReturnResponse_whenAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.ACCEPTED.code(), json("code-scanning-default-setup-update-response.json")));

        final Optional<GitHubCodeScanningDefaultSetupUpdateResponse> response =
                api().updateDefaultSetup(OWNER, REPO, CodeScanningCatalogFixtures.codeScanningDefaultSetupUpdate());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(response).contains(CodeScanningCatalogFixtures.codeScanningDefaultSetupUpdateResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString(REPO_PATH + "/default-setup");
        });
    }

    @Test
    void updateDefaultSetup_shouldReturnEmpty_whenResponseIsOkWithoutBody() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "{}"));

        final Optional<GitHubCodeScanningDefaultSetupUpdateResponse> response =
                api().updateDefaultSetup(OWNER, REPO, CodeScanningCatalogFixtures.codeScanningDefaultSetupUpdate());

        assertThat(response).isEmpty();
    }

    @Test
    void uploadSarif_shouldPostPayload_whenAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("code-scanning-sarifs-receipt.json")));

        final GitHubCodeScanningSarifsReceipt receipt =
                api().uploadSarif(OWNER, REPO, CodeScanningCatalogFixtures.uploadSarifPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(receipt).isEqualTo(CodeScanningCatalogFixtures.codeScanningSarifsReceipt());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString(REPO_PATH + "/sarifs");
        });
    }

    @Test
    void getSarif_shouldReturnStatus_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-scanning-sarifs-status.json")));

        final GitHubCodeScanningSarifsStatus status =
                api().getSarif(OWNER, REPO, SARIF_ID).orElseThrow();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(status).isEqualTo(CodeScanningCatalogFixtures.codeScanningSarifsStatus());
            softly.assertThat(requestUri).hasToString(REPO_PATH + "/sarifs/sarif-id");
        });
    }

    @Test
    void getSarif_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCodeScanningSarifsStatus> result = api().getSarif(OWNER, REPO, SARIF_ID);

        assertThat(result).isEmpty();
    }

    private GitHubCodeScanningCodeScanningApi api() {
        return new GitHubCodeScanningCodeScanningApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCodeScanningCodeScanningApiTest.class, fileName);
    }

    private static HttpResponse redirectResponse(final int statusCode, final String location) {
        return new HttpResponse(statusCode, HttpHeaders.of(Map.of(LOCATION, location)), HttpBody.absent());
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    @Test
    void listAlertsForEnterprise_shouldSendFilterAndPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(), "[" + json("code-scanning-organization-alert-items.json") + "]"));

        final GitHubPage<GitHubCodeScanningOrganizationAlertItems> page = api().listAlertsForEnterprise(
                        "octo-enterprise",
                        CodeScanningCatalogFixtures.listAlertsForEnterpriseFilter(),
                        GitHubPageNumberRequest.of(2, 50));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(CodeScanningCatalogFixtures.codeScanningOrganizationAlertItems());
            softly.assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "50");
        });
    }

    @Test
    void listAlertsForEnterprise_shouldUseDefaultPage_whenNoRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listAlertsForEnterprise("octo-enterprise");
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getPath()).isEqualTo("/enterprises/octo-enterprise/code-scanning/alerts");
    }
}
