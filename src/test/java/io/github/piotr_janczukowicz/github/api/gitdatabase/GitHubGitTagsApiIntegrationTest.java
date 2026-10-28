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
class GitHubGitTagsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String TAG_SHA = "940bd336248efae0f9ee5bc7b2d5c985887b16ac";
    private static final String COLLECTION_PATH = "/repos/octocat/Hello-World/git/tags";
    private static final String ITEM_PATH = COLLECTION_PATH + "/" + TAG_SHA;

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void createTag_shouldReturnTag_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, COLLECTION_PATH, createdJson(jsonBytes(GitHubGitTagsApiIntegrationTest.class, "git-tag.json")));
        final GitHubCreateTagPayload payload = GitDatabaseCatalogFixtures.createTagPayload();

        final GitHubGitTag tag;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            tag = api.api().tags().createTag(OWNER, REPO, payload);
        }

        final GitHubGitTag expected =
                CODEC.decode(jsonBytes(GitHubGitTagsApiIntegrationTest.class, "git-tag.json"), GitHubGitTag.class);

        assertThat(tag).isEqualTo(expected);
        HTTP.verifyRequested(POST, COLLECTION_PATH);
        HTTP.verifyHeader(POST, COLLECTION_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, COLLECTION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getTag_shouldReturnTag_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ITEM_PATH, okJson(jsonBytes(GitHubGitTagsApiIntegrationTest.class, "git-tag.json")));

        final Optional<GitHubGitTag> tag;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            tag = api.api().tags().getTag(OWNER, REPO, TAG_SHA);
        }

        final GitHubGitTag expected =
                CODEC.decode(jsonBytes(GitHubGitTagsApiIntegrationTest.class, "git-tag.json"), GitHubGitTag.class);

        assertThat(tag).contains(expected);
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void getTag_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ITEM_PATH, notFound());

        final Optional<GitHubGitTag> tag;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            tag = api.api().tags().getTag(OWNER, REPO, TAG_SHA);
        }

        assertThat(tag).isEmpty();
        HTTP.verifyRequested(GET, ITEM_PATH);
    }
}
