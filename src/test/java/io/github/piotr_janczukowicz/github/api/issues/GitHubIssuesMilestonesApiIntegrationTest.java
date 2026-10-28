package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.issuesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubIssuesMilestonesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String MILESTONES_PATH = "/repos/octocat/Hello-World/milestones";
    private static final String MILESTONE_PATH = "/repos/octocat/Hello-World/milestones/1";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void createMilestone_shouldReturnMilestone_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                MILESTONES_PATH,
                createdJson(jsonBytes(GitHubIssuesMilestonesApiIntegrationTest.class, "milestone.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api()
                            .milestones()
                            .createMilestone(OWNER, REPO, IssuesCatalogFixtures.createMilestonePayload())
                            .title())
                    .isNotBlank();
        }
    }

    @Test
    void getMilestone_shouldReturnMilestone_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                MILESTONE_PATH,
                okJson(jsonBytes(GitHubIssuesMilestonesApiIntegrationTest.class, "milestone.json")));

        try (final GitHubApiWireMockSupport.IssuesApiClient issues = issuesApi(HTTP)) {
            assertThat(issues.api().milestones().getMilestone(OWNER, REPO, 1)).isPresent();
        }
    }
}
