package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.issuesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubIssuesArtifactsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int ISSUE_NUMBER = 1347;
    private static final int ARTIFACT_NUMBER = 3;
    private static final String ARTIFACTS_PATH = "/repos/octocat/Hello-World/issues/1347/artifacts";
    private static final String ARTIFACT_PATH = ARTIFACTS_PATH + "/3";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listIssueArtifacts_shouldReturnArtifacts_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ARTIFACTS_PATH,
                okJson(listJsonBytes(GitHubIssuesArtifactsApiIntegrationTest.class, "issue-artifact.json")));

        final GitHubPage<GitHubIssueArtifact> page;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            page = issues.api().artifacts().listIssueArtifacts(OWNER, REPO, ISSUE_NUMBER);
        }

        assertThat(page.items()).containsExactly(IssuesCatalogFixtures.issueArtifact());
        HTTP.verifyRequested(GET, ARTIFACTS_PATH);
        HTTP.verifyQueryParam(GET, ARTIFACTS_PATH, "page", "1");
    }

    @Test
    void listIssueArtifacts_shouldSendTypeQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                ARTIFACTS_PATH,
                okJson(listJsonBytes(GitHubIssuesArtifactsApiIntegrationTest.class, "issue-artifact.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            issues.api()
                    .artifacts()
                    .listIssueArtifacts(
                            OWNER,
                            REPO,
                            ISSUE_NUMBER,
                            IssuesCatalogFixtures.listIssueArtifactsFilter(),
                            io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest.byPage());
        }

        HTTP.verifyQueryParam(GET, ARTIFACTS_PATH, "type", "generic");
    }

    @Test
    void createIssueArtifact_shouldReturnArtifact_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                ARTIFACTS_PATH,
                createdJson(jsonBytes(GitHubIssuesArtifactsApiIntegrationTest.class, "issue-artifact.json")));

        final GitHubIssueArtifact artifact;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            artifact = issues.api()
                    .artifacts()
                    .createIssueArtifact(OWNER, REPO, ISSUE_NUMBER, IssuesCatalogFixtures.createIssueArtifactPayload());
        }

        assertThat(artifact).isEqualTo(IssuesCatalogFixtures.issueArtifact());
        HTTP.verifyRequested(POST, ARTIFACTS_PATH);
    }

    @Test
    void getIssueArtifact_shouldReturnArtifact_whenResponseIsTheArtifact() throws Exception {
        HTTP.stub(
                GET,
                ARTIFACT_PATH,
                okJson(jsonBytes(GitHubIssuesArtifactsApiIntegrationTest.class, "issue-artifact.json")));

        final Optional<GitHubGetIssueArtifactResponse> artifact;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            artifact = issues.api().artifacts().getIssueArtifact(OWNER, REPO, ISSUE_NUMBER, ARTIFACT_NUMBER);
        }

        assertThat(artifact).contains(IssuesCatalogFixtures.issueArtifact());
        HTTP.verifyRequested(GET, ARTIFACT_PATH);
    }

    @Test
    void getIssueArtifact_shouldReturnVersions_whenResponseHasVersionHistory() throws Exception {
        HTTP.stub(
                GET,
                ARTIFACT_PATH,
                okJson(jsonBytes(
                        GitHubIssuesArtifactsApiIntegrationTest.class,
                        "get-issue-artifact-response-with-versions.json")));

        final Optional<GitHubGetIssueArtifactResponse> artifact;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            artifact = issues.api().artifacts().getIssueArtifact(OWNER, REPO, ISSUE_NUMBER, ARTIFACT_NUMBER);
        }

        assertThat(artifact).contains(IssuesCatalogFixtures.getIssueArtifactResponseWithVersions());
    }

    @Test
    void getIssueArtifact_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ARTIFACT_PATH, notFound());

        final Optional<GitHubGetIssueArtifactResponse> artifact;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            artifact = issues.api().artifacts().getIssueArtifact(OWNER, REPO, ISSUE_NUMBER, ARTIFACT_NUMBER);
        }

        assertThat(artifact).isEmpty();
    }

    @Test
    void updateIssueArtifact_shouldReturnArtifact_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                ARTIFACT_PATH,
                okJson(jsonBytes(GitHubIssuesArtifactsApiIntegrationTest.class, "issue-artifact.json")));

        final GitHubIssueArtifact artifact;
        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            artifact = issues.api()
                    .artifacts()
                    .updateIssueArtifact(
                            OWNER,
                            REPO,
                            ISSUE_NUMBER,
                            ARTIFACT_NUMBER,
                            IssuesCatalogFixtures.updateIssueArtifactPayload());
        }

        assertThat(artifact).isEqualTo(IssuesCatalogFixtures.issueArtifact());
        HTTP.verifyRequested(PATCH, ARTIFACT_PATH);
    }

    @Test
    void deleteIssueArtifact_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ARTIFACT_PATH, noContent());

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            issues.api().artifacts().deleteIssueArtifact(OWNER, REPO, ISSUE_NUMBER, ARTIFACT_NUMBER);
        }

        HTTP.verifyRequested(DELETE, ARTIFACT_PATH);
    }
}
