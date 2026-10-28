package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.commitsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCommitsCommentsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String SHA = "6dcb09b5b57875f334f61aebed695e2e4193db5e";
    private static final long COMMENT_ID = 1L;

    private static final String COMMIT_ITEM_PATH = "/repos/octocat/Hello-World/commits/" + SHA;
    private static final String COMMIT_COMMENTS_PATH = COMMIT_ITEM_PATH + "/comments";
    private static final String REPO_COMMENTS_PATH = "/repos/octocat/Hello-World/comments";
    private static final String COMMENT_ITEM_PATH = "/repos/octocat/Hello-World/comments/1";

    private static final JsonType<List<GitHubCommitComment>> COMMENTS_TYPE =
            JsonTypes.listOf(GitHubCommitComment.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listCommentsForCommit_shouldReturnComments_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COMMIT_COMMENTS_PATH,
                okJson(listJsonBytes(GitHubCommitsCommentsApiIntegrationTest.class, "commit-comment.json")));

        final GitHubPage<GitHubCommitComment> page;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            page = api.api().comments().listCommentsForCommit(OWNER, REPO, SHA);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedComments());
        HTTP.verifyRequested(GET, COMMIT_COMMENTS_PATH);
        HTTP.verifyQueryParam(GET, COMMIT_COMMENTS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, COMMIT_COMMENTS_PATH, "per_page", "100");
    }

    @Test
    void listCommentsForCommit_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        assertFollowsLinkNext(COMMIT_COMMENTS_PATH, api -> api.comments().listCommentsForCommit(OWNER, REPO, SHA));
    }

    @Test
    void listCommentsForCommit_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                COMMIT_COMMENTS_PATH,
                okJson(listJsonBytes(GitHubCommitsCommentsApiIntegrationTest.class, "commit-comment.json")));

        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            api.api().comments().listCommentsForCommit(OWNER, REPO, SHA, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, COMMIT_COMMENTS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, COMMIT_COMMENTS_PATH, "per_page", "10");
    }

    @Test
    void listCommitCommentsForRepo_shouldReturnComments_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_COMMENTS_PATH,
                okJson(listJsonBytes(GitHubCommitsCommentsApiIntegrationTest.class, "commit-comment.json")));

        final GitHubPage<GitHubCommitComment> page;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            page = api.api().comments().listCommitCommentsForRepo(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedComments());
        HTTP.verifyRequested(GET, REPO_COMMENTS_PATH);
        HTTP.verifyQueryParam(GET, REPO_COMMENTS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, REPO_COMMENTS_PATH, "per_page", "100");
    }

    @Test
    void listCommitCommentsForRepo_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        assertFollowsLinkNext(REPO_COMMENTS_PATH, api -> api.comments().listCommitCommentsForRepo(OWNER, REPO));
    }

    @Test
    void listCommitCommentsForRepo_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                REPO_COMMENTS_PATH,
                okJson(listJsonBytes(GitHubCommitsCommentsApiIntegrationTest.class, "commit-comment.json")));

        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            api.api().comments().listCommitCommentsForRepo(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, REPO_COMMENTS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, REPO_COMMENTS_PATH, "per_page", "10");
    }

    @Test
    void createCommitComment_shouldReturnComment_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                COMMIT_COMMENTS_PATH,
                createdJson(jsonBytes(GitHubCommitsCommentsApiIntegrationTest.class, "commit-comment.json")));
        final GitHubCreateCommitCommentPayload payload = CommitsCatalogFixtures.createCommitCommentPayload();

        final GitHubCommitComment comment;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            comment = api.api().comments().createCommitComment(OWNER, REPO, SHA, payload);
        }

        assertThat(comment).isEqualTo(expectedComment());
        HTTP.verifyRequested(POST, COMMIT_COMMENTS_PATH);
        HTTP.verifyHeader(POST, COMMIT_COMMENTS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, COMMIT_COMMENTS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getCommitComment_shouldReturnComment_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COMMENT_ITEM_PATH,
                okJson(jsonBytes(GitHubCommitsCommentsApiIntegrationTest.class, "commit-comment.json")));

        final Optional<GitHubCommitComment> comment;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            comment = api.api().comments().getCommitComment(OWNER, REPO, COMMENT_ID);
        }

        assertThat(comment).contains(expectedComment());
        HTTP.verifyRequested(GET, COMMENT_ITEM_PATH);
    }

    @Test
    void getCommitComment_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, COMMENT_ITEM_PATH, notFound());

        final Optional<GitHubCommitComment> comment;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            comment = api.api().comments().getCommitComment(OWNER, REPO, COMMENT_ID);
        }

        assertThat(comment).isEmpty();
        HTTP.verifyRequested(GET, COMMENT_ITEM_PATH);
    }

    @Test
    void updateCommitComment_shouldReturnComment_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                COMMENT_ITEM_PATH,
                okJson(jsonBytes(GitHubCommitsCommentsApiIntegrationTest.class, "commit-comment.json")));
        final GitHubUpdateCommitCommentPayload payload = CommitsCatalogFixtures.updateCommitCommentPayload();

        final GitHubCommitComment comment;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            comment = api.api().comments().updateCommitComment(OWNER, REPO, COMMENT_ID, payload);
        }

        assertThat(comment).isEqualTo(expectedComment());
        HTTP.verifyRequested(PATCH, COMMENT_ITEM_PATH);
        HTTP.verifyHeader(PATCH, COMMENT_ITEM_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, COMMENT_ITEM_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteCommitComment_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, COMMENT_ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            api.api().comments().deleteCommitComment(OWNER, REPO, COMMENT_ID);
        }

        HTTP.verifyRequested(DELETE, COMMENT_ITEM_PATH);
    }

    private static GitHubCommitComment expectedComment() {
        return CODEC.decode(
                jsonBytes(GitHubCommitsCommentsApiIntegrationTest.class, "commit-comment.json"),
                GitHubCommitComment.class);
    }

    private static List<GitHubCommitComment> expectedComments() {
        return CODEC.decode(
                listJsonBytes(GitHubCommitsCommentsApiIntegrationTest.class, "commit-comment.json"), COMMENTS_TYPE);
    }

    private static void assertFollowsLinkNext(final String path, final CommentsPageLoader loader) throws Exception {
        HTTP.stub(
                GET,
                path,
                okJson(listJsonBytes(GitHubCommitsCommentsApiIntegrationTest.class, "commit-comment.json"))
                        .header(LINK, linkNext(HTTP.uri(path + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubCommitsCommentsApiIntegrationTest.class, "commit-comment.json")));

        final GitHubPage<GitHubCommitComment> first;
        final Optional<GitHubPage<GitHubCommitComment>> second;
        final Optional<GitHubPage<GitHubCommitComment>> third;
        try (final GitHubApiWireMockSupport.CommitsApiClient api = commitsApi(HTTP)) {
            first = loader.load(api.api());
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedComments());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedComments());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, path, 2);
    }

    @FunctionalInterface
    private interface CommentsPageLoader {
        GitHubPage<GitHubCommitComment> load(GitHubCommitsApi api) throws Exception;
    }
}
