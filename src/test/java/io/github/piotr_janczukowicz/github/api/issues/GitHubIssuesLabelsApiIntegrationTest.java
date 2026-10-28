package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.issuesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubIssuesLabelsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String NAME = "bug";
    private static final int ISSUE_NUMBER = 1309;
    private static final String ISSUE_LABELS_PATH = "/repos/octocat/Hello-World/issues/1309/labels";
    private static final String REPO_LABELS_PATH = "/repos/octocat/Hello-World/labels";
    private static final String LABEL_PATH = "/repos/octocat/Hello-World/labels/bug";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void addLabels_shouldReturnLabels_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                ISSUE_LABELS_PATH,
                okJson(listJsonBytes(GitHubIssuesLabelsApiIntegrationTest.class, "label.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api()
                            .labels()
                            .addLabels(OWNER, REPO, ISSUE_NUMBER, IssuesCatalogFixtures.addLabelsPayload()))
                    .hasSize(1);
        }
    }

    @Test
    void createLabel_shouldReturnLabel_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                REPO_LABELS_PATH,
                createdJson(jsonBytes(GitHubIssuesLabelsApiIntegrationTest.class, "label.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api()
                            .labels()
                            .createLabel(OWNER, REPO, IssuesCatalogFixtures.createLabelPayload())
                            .name())
                    .isEqualTo(NAME);
        }
    }

    @Test
    void getLabel_shouldReturnLabel_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, LABEL_PATH, okJson(jsonBytes(GitHubIssuesLabelsApiIntegrationTest.class, "label.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api().labels().getLabel(OWNER, REPO, NAME)).isPresent();
        }
    }
}
