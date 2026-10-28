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
class GitHubGitTreesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String TREE_SHA = "9fb037999f264ba9a7fc6274d15fa3ae2ab98312";
    private static final String COLLECTION_PATH = "/repos/octocat/Hello-World/git/trees";
    private static final String ITEM_PATH = COLLECTION_PATH + "/" + TREE_SHA;

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void createTree_shouldReturnTree_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST, COLLECTION_PATH, createdJson(jsonBytes(GitHubGitTreesApiIntegrationTest.class, "git-tree.json")));
        final GitHubCreateTreePayload payload = GitDatabaseCatalogFixtures.createTreePayload();

        final GitHubGitTree tree;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            tree = api.api().trees().createTree(OWNER, REPO, payload);
        }

        final GitHubGitTree expected =
                CODEC.decode(jsonBytes(GitHubGitTreesApiIntegrationTest.class, "git-tree.json"), GitHubGitTree.class);

        assertThat(tree).isEqualTo(expected);
        HTTP.verifyRequested(POST, COLLECTION_PATH);
        HTTP.verifyHeader(POST, COLLECTION_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, COLLECTION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getTree_shouldReturnTree_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ITEM_PATH, okJson(jsonBytes(GitHubGitTreesApiIntegrationTest.class, "git-tree.json")));

        final Optional<GitHubGitTree> tree;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            tree = api.api().trees().getTree(OWNER, REPO, TREE_SHA);
        }

        final GitHubGitTree expected =
                CODEC.decode(jsonBytes(GitHubGitTreesApiIntegrationTest.class, "git-tree.json"), GitHubGitTree.class);

        assertThat(tree).contains(expected);
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void getTree_shouldSendRecursiveQuery_whenFilterProvided() throws Exception {
        HTTP.stub(GET, ITEM_PATH, okJson(jsonBytes(GitHubGitTreesApiIntegrationTest.class, "git-tree.json")));

        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            api.api().trees().getTree(OWNER, REPO, TREE_SHA, GitDatabaseCatalogFixtures.getTreeFilter());
        }

        HTTP.verifyQueryParam(GET, ITEM_PATH, "recursive", "1");
    }

    @Test
    void getTree_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ITEM_PATH, notFound());

        final Optional<GitHubGitTree> tree;
        try (final GitHubApiWireMockSupport.GitDatabaseApiClient api = gitDatabaseApi(HTTP)) {
            tree = api.api().trees().getTree(OWNER, REPO, TREE_SHA);
        }

        assertThat(tree).isEmpty();
        HTTP.verifyRequested(GET, ITEM_PATH);
    }
}
