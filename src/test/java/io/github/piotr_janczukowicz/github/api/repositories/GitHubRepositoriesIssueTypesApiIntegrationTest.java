package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.reposIssueTypesApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueType;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubRepositoriesIssueTypesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ISSUE_TYPES_PATH = "/repos/octocat/Hello-World/issue-types";

    private static final JsonType<List<GitHubIssueType>> ISSUE_TYPES_TYPE = JsonTypes.listOf(GitHubIssueType.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listIssueTypes_shouldReturnIssueTypes_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ISSUE_TYPES_PATH,
                okJson(listJsonBytes(GitHubRepositoriesIssueTypesApiIntegrationTest.class, "issue-type.json")));

        final List<GitHubIssueType> issueTypes;
        try (final GitHubApiWireMockSupport.ReposIssueTypesApiClient api = reposIssueTypesApi(HTTP)) {
            issueTypes = api.api().listIssueTypes(OWNER, REPO);
        }

        assertThat(issueTypes).containsExactlyElementsOf(expectedIssueTypes());
        HTTP.verifyRequested(GET, ISSUE_TYPES_PATH);
    }

    private static List<GitHubIssueType> expectedIssueTypes() {
        return CODEC.decode(
                listJsonBytes(GitHubRepositoriesIssueTypesApiIntegrationTest.class, "issue-type.json"),
                ISSUE_TYPES_TYPE);
    }
}
