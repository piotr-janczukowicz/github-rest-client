package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.gitDatabaseApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubGitBlobsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String FILE_SHA = "3a0f86fb8db8eea7ccbb9a95f325ddbedfb25e15";
    private static final String COLLECTION_PATH = "/repos/octocat/Hello-World/git/blobs";
    private static final String ITEM_PATH = COLLECTION_PATH + "/" + FILE_SHA;

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void createBlob_shouldReturnShortBlob_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                COLLECTION_PATH,
                createdJson(jsonBytes(GitHubGitBlobsApiIntegrationTest.class, "short-blob.json")));
        final GitHubCreateBlobPayload payload = GitDatabaseCatalogFixtures.createBlobPayload();

        final GitHubShortBlob blob;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            blob = api.api().blobs().createBlob(OWNER, REPO, payload);
        }

        final GitHubShortBlob expected = CODEC.decode(
                jsonBytes(GitHubGitBlobsApiIntegrationTest.class, "short-blob.json"), GitHubShortBlob.class);

        assertThat(blob).isEqualTo(expected);
        HTTP.verifyRequested(POST, COLLECTION_PATH);
        HTTP.verifyHeader(POST, COLLECTION_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, COLLECTION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getBlob_shouldReturnBlob_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ITEM_PATH, okJson(jsonBytes(GitHubGitBlobsApiIntegrationTest.class, "blob.json")));

        final Optional<GitHubBlob> blob;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            blob = api.api().blobs().getBlob(OWNER, REPO, FILE_SHA);
        }

        final GitHubBlob expected =
                CODEC.decode(jsonBytes(GitHubGitBlobsApiIntegrationTest.class, "blob.json"), GitHubBlob.class);

        assertThat(blob).contains(expected);
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void getBlob_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ITEM_PATH, notFound());

        final Optional<GitHubBlob> blob;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            blob = api.api().blobs().getBlob(OWNER, REPO, FILE_SHA);
        }

        assertThat(blob).isEmpty();
        HTTP.verifyRequested(GET, ITEM_PATH);
    }
}
