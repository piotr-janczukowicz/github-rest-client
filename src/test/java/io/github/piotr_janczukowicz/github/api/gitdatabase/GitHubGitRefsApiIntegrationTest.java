package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.gitDatabaseApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubGitRefsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String REF = "heads/featureA";
    private static final String BASE_PATH = "/repos/octocat/Hello-World/git";
    private static final String MATCHING_PATH = BASE_PATH + "/matching-refs/" + REF;
    private static final String LOOKUP_PATH = BASE_PATH + "/ref/" + REF;
    private static final String COLLECTION_PATH = BASE_PATH + "/refs";
    private static final String ITEM_PATH = COLLECTION_PATH + "/" + REF;

    private static final JsonType<List<GitHubGitRef>> GIT_REFS_TYPE = JsonTypes.listOf(GitHubGitRef.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listMatchingRefs_shouldReturnRefsWithoutPagingQuery_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, MATCHING_PATH, okJson(listJsonBytes(GitHubGitRefsApiIntegrationTest.class, "git-ref.json")));

        final GitHubPage<GitHubGitRef> page;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            page = api.api().refs().listMatchingRefs(OWNER, REPO, REF);
        }

        final List<GitHubGitRef> expected =
                CODEC.decode(listJsonBytes(GitHubGitRefsApiIntegrationTest.class, "git-ref.json"), GIT_REFS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, MATCHING_PATH);
    }

    @Test
    void getRef_shouldReturnRef_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, LOOKUP_PATH, okJson(jsonBytes(GitHubGitRefsApiIntegrationTest.class, "git-ref.json")));

        final Optional<GitHubGitRef> ref;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            ref = api.api().refs().getRef(OWNER, REPO, REF);
        }

        final GitHubGitRef expected =
                CODEC.decode(jsonBytes(GitHubGitRefsApiIntegrationTest.class, "git-ref.json"), GitHubGitRef.class);

        assertThat(ref).contains(expected);
        HTTP.verifyRequested(GET, LOOKUP_PATH);
    }

    @Test
    void getRef_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, LOOKUP_PATH, notFound());

        final Optional<GitHubGitRef> ref;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            ref = api.api().refs().getRef(OWNER, REPO, REF);
        }

        assertThat(ref).isEmpty();
        HTTP.verifyRequested(GET, LOOKUP_PATH);
    }

    @Test
    void createRef_shouldReturnRef_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, COLLECTION_PATH, createdJson(jsonBytes(GitHubGitRefsApiIntegrationTest.class, "git-ref.json")));
        final GitHubCreateRefPayload payload = GitDatabaseCatalogFixtures.createRefPayload();

        final GitHubGitRef ref;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            ref = api.api().refs().createRef(OWNER, REPO, payload);
        }

        final GitHubGitRef expected =
                CODEC.decode(jsonBytes(GitHubGitRefsApiIntegrationTest.class, "git-ref.json"), GitHubGitRef.class);

        assertThat(ref).isEqualTo(expected);
        HTTP.verifyRequested(POST, COLLECTION_PATH);
        HTTP.verifyHeader(POST, COLLECTION_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, COLLECTION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updateRef_shouldReturnRef_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, ITEM_PATH, okJson(jsonBytes(GitHubGitRefsApiIntegrationTest.class, "git-ref.json")));
        final GitHubUpdateRefPayload payload = GitDatabaseCatalogFixtures.updateRefPayload();

        final GitHubGitRef ref;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            ref = api.api().refs().updateRef(OWNER, REPO, REF, payload);
        }

        final GitHubGitRef expected =
                CODEC.decode(jsonBytes(GitHubGitRefsApiIntegrationTest.class, "git-ref.json"), GitHubGitRef.class);

        assertThat(ref).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, ITEM_PATH);
        HTTP.verifyHeader(PATCH, ITEM_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, ITEM_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteRef_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            api.api().refs().deleteRef(OWNER, REPO, REF);
        }

        HTTP.verifyRequested(DELETE, ITEM_PATH);
    }
}
