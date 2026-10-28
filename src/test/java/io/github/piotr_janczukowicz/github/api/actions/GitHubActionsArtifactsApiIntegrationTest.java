package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpStatus.FOUND;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.actionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActionsArtifactsApiIntegrationTest {

    private static final String ARTIFACTS_PATH = "/repos/octocat/Hello-World/actions/artifacts";
    private static final String ARTIFACT_PATH = "/repos/octocat/Hello-World/actions/artifacts/1";
    private static final String ARCHIVE_PATH = "/repos/octocat/Hello-World/actions/artifacts/1/zip";
    private static final URI ARCHIVE_LOCATION = URI.create("https://example.com/archive.zip");

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listArtifactsForRepo_shouldReturnArtifacts_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ARTIFACTS_PATH,
                okJson(jsonBytes(GitHubActionsArtifactsApiIntegrationTest.class, "artifacts.json")));

        final GitHubPage<GitHubArtifact> page;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            page = actions.api().artifacts().listArtifactsForRepo("octocat", "Hello-World");
        }

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.artifacts().artifacts());
        HTTP.verifyRequested(GET, ARTIFACTS_PATH);
    }

    @Test
    void getArtifact_shouldReturnArtifact_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, ARTIFACT_PATH, okJson(jsonBytes(GitHubActionsArtifactsApiIntegrationTest.class, "artifact.json")));

        final GitHubArtifact artifact;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            artifact = actions.api()
                    .artifacts()
                    .getArtifact("octocat", "Hello-World", 1L)
                    .orElseThrow();
        }

        assertThat(artifact).isEqualTo(ActionsCatalogFixtures.artifact());
        HTTP.verifyRequested(GET, ARTIFACT_PATH);
    }

    @Test
    void deleteArtifact_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ARTIFACT_PATH, noContent());

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api().artifacts().deleteArtifact("octocat", "Hello-World", 1L);
        }

        HTTP.verifyRequested(DELETE, ARTIFACT_PATH);
    }

    @Test
    void downloadArtifact_shouldReturnLocation_whenResponseIsFound() throws Exception {
        HTTP.stubRedirect(ARCHIVE_PATH, ARCHIVE_LOCATION.toString(), FOUND.code());

        final URI location;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            location = actions.api().artifacts().downloadArtifact("octocat", "Hello-World", 1L, "zip");
        }

        assertThat(location).isEqualTo(ARCHIVE_LOCATION);
        HTTP.verifyRequested(GET, ARCHIVE_PATH);
    }
}
