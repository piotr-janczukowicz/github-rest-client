package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.dependencyGraphApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubDependencyGraphDependencyReviewApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String BASEHEAD = "main...feature";
    private static final String COMPARE_PATH = "/repos/octocat/Hello-World/dependency-graph/compare/main...feature";

    private static final JsonType<List<GitHubDependencyGraphDiffEntry>> DIFF_TYPE =
            JsonTypes.listOf(GitHubDependencyGraphDiffEntry.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void diffRange_shouldReturnEntries_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COMPARE_PATH,
                okJson(listJsonBytes(
                        GitHubDependencyGraphDependencyReviewApiIntegrationTest.class,
                        "dependency-graph-diff-entry.json")));

        final List<GitHubDependencyGraphDiffEntry> entries;
        try (final GitHubApiWireMockSupport.DependencyGraphApiClient api = dependencyGraphApi(HTTP)) {
            entries = api.api().dependencyReview().diffRange(OWNER, REPO, BASEHEAD);
        }

        assertThat(entries).containsExactlyElementsOf(expectedEntries());
        HTTP.verifyRequested(GET, COMPARE_PATH);
    }

    @Test
    void diffRange_shouldSendNameQuery_whenFilterHasName() throws Exception {
        HTTP.stub(
                GET,
                COMPARE_PATH,
                okJson(listJsonBytes(
                        GitHubDependencyGraphDependencyReviewApiIntegrationTest.class,
                        "dependency-graph-diff-entry.json")));

        try (final GitHubApiWireMockSupport.DependencyGraphApiClient api = dependencyGraphApi(HTTP)) {
            api.api()
                    .dependencyReview()
                    .diffRange(OWNER, REPO, BASEHEAD, DependencyGraphCatalogFixtures.diffRangeFilter());
        }

        HTTP.verifyQueryParam(GET, COMPARE_PATH, "name", "package.json");
    }

    private static List<GitHubDependencyGraphDiffEntry> expectedEntries() {
        return CODEC.decode(
                listJsonBytes(
                        GitHubDependencyGraphDependencyReviewApiIntegrationTest.class,
                        "dependency-graph-diff-entry.json"),
                DIFF_TYPE);
    }
}
