package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubPullsCommentsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int PULL_NUMBER = 1347;
    private static final long COMMENT_ID = 1L;

    private static final String REPO_COMMENTS_PATH = "/repos/octocat/Hello-World/pulls/comments";
    private static final String COMMENT_PATH = "/repos/octocat/Hello-World/pulls/comments/1";
    private static final String PULL_COMMENTS_PATH = "/repos/octocat/Hello-World/pulls/1347/comments";
    private static final String REPLY_PATH = "/repos/octocat/Hello-World/pulls/1347/comments/1/replies";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listForRepo_shouldReturnComments_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_COMMENTS_PATH,
                okJson(listJsonBytes(GitHubPullsCommentsApiIntegrationTest.class, "pull-request-review-comment.json")));

        final GitHubPage<GitHubPullRequestReviewComment> page;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsCommentsApi api = new GitHubPullsCommentsApi(client.caller());
            page = api.listForRepo(OWNER, REPO);
        }

        final GitHubPullRequestReviewComment expected = CODEC.decode(
                jsonBytes(GitHubPullsCommentsApiIntegrationTest.class, "pull-request-review-comment.json"),
                GitHubPullRequestReviewComment.class);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(expected);
            HTTP.verifyRequested(GET, REPO_COMMENTS_PATH);
        });
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, COMMENT_PATH, notFound());

        final Optional<GitHubPullRequestReviewComment> comment;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsCommentsApi api = new GitHubPullsCommentsApi(client.caller());
            comment = api.get(OWNER, REPO, COMMENT_ID);
        }

        assertSoftly(softly -> {
            softly.assertThat(comment).isEmpty();
            HTTP.verifyRequested(GET, COMMENT_PATH);
        });
    }

    @Test
    void update_shouldReturnComment_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                COMMENT_PATH,
                okJson(jsonBytes(GitHubPullsCommentsApiIntegrationTest.class, "pull-request-review-comment.json")));
        final GitHubUpdateReviewCommentPayload payload = PullrequestsCatalogFixtures.updateReviewCommentPayload();

        final GitHubPullRequestReviewComment comment;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsCommentsApi api = new GitHubPullsCommentsApi(client.caller());
            comment = api.update(OWNER, REPO, COMMENT_ID, payload);
        }

        final GitHubPullRequestReviewComment expected = CODEC.decode(
                jsonBytes(GitHubPullsCommentsApiIntegrationTest.class, "pull-request-review-comment.json"),
                GitHubPullRequestReviewComment.class);

        assertSoftly(softly -> {
            softly.assertThat(comment).isEqualTo(expected);
            HTTP.verifyRequested(PATCH, COMMENT_PATH);
            HTTP.verifyBody(PATCH, COMMENT_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void delete_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, COMMENT_PATH, noContent());

        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsCommentsApi api = new GitHubPullsCommentsApi(client.caller());
            api.delete(OWNER, REPO, COMMENT_ID);
        }

        HTTP.verifyRequested(DELETE, COMMENT_PATH);
    }

    @Test
    void create_shouldReturnComment_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                PULL_COMMENTS_PATH,
                createdJson(jsonBytes(
                        GitHubPullsCommentsApiIntegrationTest.class, "pull-request-review-comment-multi-line.json")));
        final GitHubCreateReviewCommentPayload payload = PullrequestsCatalogFixtures.createReviewCommentPayload();

        final GitHubPullRequestReviewComment comment;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsCommentsApi api = new GitHubPullsCommentsApi(client.caller());
            comment = api.create(OWNER, REPO, PULL_NUMBER, payload);
        }

        final GitHubPullRequestReviewComment expected = CODEC.decode(
                jsonBytes(GitHubPullsCommentsApiIntegrationTest.class, "pull-request-review-comment-multi-line.json"),
                GitHubPullRequestReviewComment.class);

        assertSoftly(softly -> {
            softly.assertThat(comment).isEqualTo(expected);
            HTTP.verifyRequested(POST, PULL_COMMENTS_PATH);
            HTTP.verifyBody(POST, PULL_COMMENTS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void createReply_shouldReturnComment_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                REPLY_PATH,
                createdJson(
                        jsonBytes(GitHubPullsCommentsApiIntegrationTest.class, "pull-request-review-comment.json")));
        final GitHubCreateReviewCommentReplyPayload payload =
                PullrequestsCatalogFixtures.createReviewCommentReplyPayload();

        final GitHubPullRequestReviewComment comment;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsCommentsApi api = new GitHubPullsCommentsApi(client.caller());
            comment = api.createReply(OWNER, REPO, PULL_NUMBER, COMMENT_ID, payload);
        }

        final GitHubPullRequestReviewComment expected = CODEC.decode(
                jsonBytes(GitHubPullsCommentsApiIntegrationTest.class, "pull-request-review-comment.json"),
                GitHubPullRequestReviewComment.class);

        assertSoftly(softly -> {
            softly.assertThat(comment).isEqualTo(expected);
            HTTP.verifyRequested(POST, REPLY_PATH);
            HTTP.verifyBody(POST, REPLY_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
        });
    }
}
