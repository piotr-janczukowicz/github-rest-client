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
class GitHubGitCommitsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String COMMIT_SHA = "7638417db6d59f3c431d3e1f261cc637155684cd";
    private static final String COLLECTION_PATH = "/repos/octocat/Hello-World/git/commits";
    private static final String ITEM_PATH = COLLECTION_PATH + "/" + COMMIT_SHA;

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void createCommit_shouldReturnCommit_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                COLLECTION_PATH,
                createdJson(jsonBytes(GitHubGitCommitsApiIntegrationTest.class, "git-commit.json")));
        final GitHubCreateCommitPayload payload = GitDatabaseCatalogFixtures.createCommitPayload();

        final GitHubGitCommit commit;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            commit = api.api().commits().createCommit(OWNER, REPO, payload);
        }

        final GitHubGitCommit expected = CODEC.decode(
                jsonBytes(GitHubGitCommitsApiIntegrationTest.class, "git-commit.json"), GitHubGitCommit.class);

        assertThat(commit).isEqualTo(expected);
        HTTP.verifyRequested(POST, COLLECTION_PATH);
        HTTP.verifyHeader(POST, COLLECTION_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, COLLECTION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getCommit_shouldReturnCommit_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ITEM_PATH, okJson(jsonBytes(GitHubGitCommitsApiIntegrationTest.class, "git-commit.json")));

        final Optional<GitHubGitCommit> commit;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            commit = api.api().commits().getCommit(OWNER, REPO, COMMIT_SHA);
        }

        final GitHubGitCommit expected = CODEC.decode(
                jsonBytes(GitHubGitCommitsApiIntegrationTest.class, "git-commit.json"), GitHubGitCommit.class);

        assertThat(commit).contains(expected);
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void getCommit_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ITEM_PATH, notFound());

        final Optional<GitHubGitCommit> commit;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            commit = api.api().commits().getCommit(OWNER, REPO, COMMIT_SHA);
        }

        assertThat(commit).isEmpty();
        HTTP.verifyRequested(GET, ITEM_PATH);
    }
}
