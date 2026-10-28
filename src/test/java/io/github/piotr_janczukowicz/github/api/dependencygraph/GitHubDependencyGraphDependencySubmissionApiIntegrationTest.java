package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.dependencyGraphApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubDependencyGraphDependencySubmissionApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String SNAPSHOTS_PATH = "/repos/octocat/Hello-World/dependency-graph/snapshots";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void createRepositorySnapshot_shouldReturnResponse_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                SNAPSHOTS_PATH,
                createdJson(jsonBytes(
                        GitHubDependencyGraphDependencySubmissionApiIntegrationTest.class,
                        "create-repository-snapshot-response.json")));
        final GitHubCreateRepositorySnapshotPayload payload =
                DependencyGraphCatalogFixtures.createRepositorySnapshotPayload();

        final GitHubCreateRepositorySnapshotResponse response;
        try (final GitHubApiWireMockSupport.DependencyGraphApiClient api = dependencyGraphApi(HTTP)) {
            response = api.api().dependencySubmission().createRepositorySnapshot(OWNER, REPO, payload);
        }

        final GitHubCreateRepositorySnapshotResponse expected = CODEC.decode(
                jsonBytes(
                        GitHubDependencyGraphDependencySubmissionApiIntegrationTest.class,
                        "create-repository-snapshot-response.json"),
                GitHubCreateRepositorySnapshotResponse.class);

        assertThat(response).isEqualTo(expected);
        HTTP.verifyRequested(POST, SNAPSHOTS_PATH);
        HTTP.verifyHeader(POST, SNAPSHOTS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, SNAPSHOTS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }
}
