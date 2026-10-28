package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.issuesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubIssuesIssueFieldValuesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int ISSUE_NUMBER = 1309;
    private static final String FIELD_VALUES_PATH = "/repos/octocat/Hello-World/issues/1309/issue-field-values";
    private static final String FIELD_VALUE_PATH = "/repos/octocat/Hello-World/issues/1309/issue-field-values/1";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listIssueFieldValuesForIssue_shouldReturnValues_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                FIELD_VALUES_PATH,
                okJson(listJsonBytes(GitHubIssuesIssueFieldValuesApiIntegrationTest.class, "issue-field-value.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api()
                            .issueFieldValues()
                            .listIssueFieldValuesForIssue(OWNER, REPO, ISSUE_NUMBER)
                            .items())
                    .hasSize(1);
        }
    }

    @Test
    void addIssueFieldValues_shouldReturnValues_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                FIELD_VALUES_PATH,
                okJson(listJsonBytes(GitHubIssuesIssueFieldValuesApiIntegrationTest.class, "issue-field-value.json")));
        final GitHubAddIssueFieldValuesPayload payload = IssuesCatalogFixtures.addIssueFieldValuesPayload();

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api().issueFieldValues().addIssueFieldValues(OWNER, REPO, ISSUE_NUMBER, payload))
                    .hasSize(1);
        }
    }

    @Test
    void deleteIssueFieldValue_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, FIELD_VALUE_PATH, noContent());

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            issues.api().issueFieldValues().deleteIssueFieldValue(OWNER, REPO, ISSUE_NUMBER, 1L);
        }

        HTTP.verifyRequested(DELETE, FIELD_VALUE_PATH);
    }
}
