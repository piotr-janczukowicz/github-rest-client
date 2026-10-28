package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.acceptedJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.codeQualityApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCodeQualityApiIntegrationTest {

    private static final String REPO_PATH = "/repos/octocat/Hello-World/code-quality";
    private static final String FINDINGS_PATH = REPO_PATH + "/findings";
    private static final String FINDING_PATH = FINDINGS_PATH + "/1";
    private static final String SETUP_PATH = REPO_PATH + "/setup";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listFindingsForRepo_shouldSendFilterAndCursor_whenCalled() throws Exception {
        HTTP.stub(
                GET,
                FINDINGS_PATH,
                okJson(listJsonBytes(GitHubCodeQualityApiIntegrationTest.class, "code-quality-finding.json")));

        final GitHubPage<GitHubCodeQualityFinding> page;
        try (final GitHubApiWireMockSupport.CodeQualityApiClient api = codeQualityApi(HTTP)) {
            page = api.api()
                    .listFindingsForRepo(
                            "octocat",
                            "Hello-World",
                            CodeQualityCatalogFixtures.listFindingsForRepoFilter(),
                            GitHubCursorRequest.after("cursor-after", 50));
        }

        assertThat(page.items()).containsExactly(CodeQualityCatalogFixtures.codeQualityFinding());
        HTTP.verifyRequested(GET, FINDINGS_PATH);
        HTTP.verifyQueryParam(GET, FINDINGS_PATH, "after", "cursor-after");
        HTTP.verifyQueryParam(GET, FINDINGS_PATH, "per_page", "50");
    }

    @Test
    void getFinding_shouldReturnFinding_whenOk() throws Exception {
        HTTP.stub(
                GET,
                FINDING_PATH,
                okJson(jsonBytes(GitHubCodeQualityApiIntegrationTest.class, "code-quality-finding.json")));

        final GitHubCodeQualityFinding finding;
        try (final GitHubApiWireMockSupport.CodeQualityApiClient api = codeQualityApi(HTTP)) {
            finding = api.api().getFinding("octocat", "Hello-World", 1).orElseThrow();
        }

        assertThat(finding).isEqualTo(CodeQualityCatalogFixtures.codeQualityFinding());
        HTTP.verifyRequested(GET, FINDING_PATH);
    }

    @Test
    void getFinding_shouldReturnEmpty_whenNotFound() throws Exception {
        HTTP.stub(GET, FINDING_PATH, notFound());

        final Optional<GitHubCodeQualityFinding> finding;
        try (final GitHubApiWireMockSupport.CodeQualityApiClient api = codeQualityApi(HTTP)) {
            finding = api.api().getFinding("octocat", "Hello-World", 1);
        }

        assertThat(finding).isEmpty();
        HTTP.verifyRequested(GET, FINDING_PATH);
    }

    @Test
    void getSetup_shouldReturnSetup_whenOk() throws Exception {
        HTTP.stub(
                GET,
                SETUP_PATH,
                okJson(jsonBytes(GitHubCodeQualityApiIntegrationTest.class, "code-quality-setup.json")));

        final GitHubCodeQualitySetup setup;
        try (final GitHubApiWireMockSupport.CodeQualityApiClient api = codeQualityApi(HTTP)) {
            setup = api.api().getSetup("octocat", "Hello-World");
        }

        assertThat(setup).isEqualTo(CodeQualityCatalogFixtures.codeQualitySetup());
        HTTP.verifyRequested(GET, SETUP_PATH);
    }

    @Test
    void updateSetup_shouldReturnResponse_whenAccepted() throws Exception {
        HTTP.stub(
                PATCH,
                SETUP_PATH,
                acceptedJson(jsonBytes(
                        GitHubCodeQualityApiIntegrationTest.class, "code-quality-setup-update-response.json")));

        final Optional<GitHubCodeQualitySetupUpdateResponse> response;
        try (final GitHubApiWireMockSupport.CodeQualityApiClient api = codeQualityApi(HTTP)) {
            response = api.api()
                    .updateSetup("octocat", "Hello-World", CodeQualityCatalogFixtures.codeQualitySetupUpdate());
        }

        assertThat(response).contains(CodeQualityCatalogFixtures.codeQualitySetupUpdateResponse());
        HTTP.verifyRequested(PATCH, SETUP_PATH);
    }

    @Test
    void updateSetup_shouldReturnEmpty_whenOkWithEmptyObject() throws Exception {
        HTTP.stub(PATCH, SETUP_PATH, okJson("{}".getBytes(StandardCharsets.UTF_8)));

        final Optional<GitHubCodeQualitySetupUpdateResponse> response;
        try (final GitHubApiWireMockSupport.CodeQualityApiClient api = codeQualityApi(HTTP)) {
            response = api.api()
                    .updateSetup("octocat", "Hello-World", CodeQualityCatalogFixtures.codeQualitySetupUpdate());
        }

        assertThat(response).isEmpty();
        HTTP.verifyRequested(PATCH, SETUP_PATH);
    }
}
