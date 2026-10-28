package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
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
class GitHubPullsReviewsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int PULL_NUMBER = 1347;
    private static final long REVIEW_ID = 80L;

    private static final String REVIEWS_PATH = "/repos/octocat/Hello-World/pulls/1347/reviews";
    private static final String REVIEW_PATH = REVIEWS_PATH + "/80";
    private static final String REVIEW_COMMENTS_PATH = REVIEW_PATH + "/comments";
    private static final String DISMISSALS_PATH = REVIEW_PATH + "/dismissals";
    private static final String EVENTS_PATH = REVIEW_PATH + "/events";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void list_shouldReturnReviews_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REVIEWS_PATH,
                okJson(jsonBytes(GitHubPullsReviewsApiIntegrationTest.class, "pull-request-review-items.json")));

        final GitHubPage<GitHubPullRequestReview> page;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsReviewsApi api = new GitHubPullsReviewsApi(client.caller());
            page = api.list(OWNER, REPO, PULL_NUMBER);
        }

        assertSoftly(softly -> {
            softly.assertThat(page.items()).hasSize(1);
            HTTP.verifyRequested(GET, REVIEWS_PATH);
        });
    }

    @Test
    void create_shouldReturnReview_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                REVIEWS_PATH,
                okJson(jsonBytes(GitHubPullsReviewsApiIntegrationTest.class, "pull-request-review.json")));
        final GitHubCreateReviewPayload payload = PullrequestsCatalogFixtures.createReviewPayload();

        final GitHubPullRequestReview review;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsReviewsApi api = new GitHubPullsReviewsApi(client.caller());
            review = api.create(OWNER, REPO, PULL_NUMBER, payload);
        }

        final GitHubPullRequestReview expected = CODEC.decode(
                jsonBytes(GitHubPullsReviewsApiIntegrationTest.class, "pull-request-review.json"),
                GitHubPullRequestReview.class);

        assertSoftly(softly -> {
            softly.assertThat(review).isEqualTo(expected);
            HTTP.verifyRequested(POST, REVIEWS_PATH);
            HTTP.verifyBody(POST, REVIEWS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void get_shouldReturnReview_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REVIEW_PATH,
                okJson(jsonBytes(GitHubPullsReviewsApiIntegrationTest.class, "pull-request-review.json")));

        final Optional<GitHubPullRequestReview> review;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsReviewsApi api = new GitHubPullsReviewsApi(client.caller());
            review = api.get(OWNER, REPO, PULL_NUMBER, REVIEW_ID);
        }

        final GitHubPullRequestReview expected = CODEC.decode(
                jsonBytes(GitHubPullsReviewsApiIntegrationTest.class, "pull-request-review.json"),
                GitHubPullRequestReview.class);

        assertSoftly(softly -> {
            softly.assertThat(review).contains(expected);
            HTTP.verifyRequested(GET, REVIEW_PATH);
        });
    }

    @Test
    void update_shouldReturnReview_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                REVIEW_PATH,
                okJson(jsonBytes(GitHubPullsReviewsApiIntegrationTest.class, "pull-request-review.json")));
        final GitHubUpdateReviewPayload payload = PullrequestsCatalogFixtures.updateReviewPayload();

        final GitHubPullRequestReview review;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsReviewsApi api = new GitHubPullsReviewsApi(client.caller());
            review = api.update(OWNER, REPO, PULL_NUMBER, REVIEW_ID, payload);
        }

        final GitHubPullRequestReview expected = CODEC.decode(
                jsonBytes(GitHubPullsReviewsApiIntegrationTest.class, "pull-request-review.json"),
                GitHubPullRequestReview.class);

        assertSoftly(softly -> {
            softly.assertThat(review).isEqualTo(expected);
            HTTP.verifyRequested(PUT, REVIEW_PATH);
            HTTP.verifyBody(PUT, REVIEW_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void deletePending_shouldReturnReview_whenResponseIsOk() throws Exception {
        HTTP.stub(
                DELETE,
                REVIEW_PATH,
                okJson(jsonBytes(GitHubPullsReviewsApiIntegrationTest.class, "pull-request-review.json")));

        final GitHubPullRequestReview review;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsReviewsApi api = new GitHubPullsReviewsApi(client.caller());
            review = api.deletePending(OWNER, REPO, PULL_NUMBER, REVIEW_ID);
        }

        final GitHubPullRequestReview expected = CODEC.decode(
                jsonBytes(GitHubPullsReviewsApiIntegrationTest.class, "pull-request-review.json"),
                GitHubPullRequestReview.class);

        assertSoftly(softly -> {
            softly.assertThat(review).isEqualTo(expected);
            HTTP.verifyRequested(DELETE, REVIEW_PATH);
        });
    }

    @Test
    void listComments_shouldReturnReviewComments_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REVIEW_COMMENTS_PATH,
                okJson(jsonBytes(GitHubPullsReviewsApiIntegrationTest.class, "review-comment-items.json")));

        final GitHubPage<GitHubReviewComment> page;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsReviewsApi api = new GitHubPullsReviewsApi(client.caller());
            page = api.listComments(OWNER, REPO, PULL_NUMBER, REVIEW_ID);
        }

        final GitHubReviewComment expected = CODEC.decode(
                jsonBytes(GitHubPullsReviewsApiIntegrationTest.class, "review-comment.json"),
                GitHubReviewComment.class);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(expected);
            HTTP.verifyRequested(GET, REVIEW_COMMENTS_PATH);
        });
    }

    @Test
    void dismiss_shouldReturnReview_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                DISMISSALS_PATH,
                okJson(jsonBytes(GitHubPullsReviewsApiIntegrationTest.class, "pull-request-review.json")));
        final GitHubDismissReviewPayload payload = PullrequestsCatalogFixtures.dismissReviewPayload();

        final GitHubPullRequestReview review;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsReviewsApi api = new GitHubPullsReviewsApi(client.caller());
            review = api.dismiss(OWNER, REPO, PULL_NUMBER, REVIEW_ID, payload);
        }

        final GitHubPullRequestReview expected = CODEC.decode(
                jsonBytes(GitHubPullsReviewsApiIntegrationTest.class, "pull-request-review.json"),
                GitHubPullRequestReview.class);

        assertSoftly(softly -> {
            softly.assertThat(review).isEqualTo(expected);
            HTTP.verifyRequested(PUT, DISMISSALS_PATH);
            HTTP.verifyBody(PUT, DISMISSALS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void submit_shouldReturnReview_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                EVENTS_PATH,
                okJson(jsonBytes(GitHubPullsReviewsApiIntegrationTest.class, "pull-request-review.json")));
        final GitHubSubmitReviewPayload payload = PullrequestsCatalogFixtures.submitReviewPayload();

        final GitHubPullRequestReview review;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsReviewsApi api = new GitHubPullsReviewsApi(client.caller());
            review = api.submit(OWNER, REPO, PULL_NUMBER, REVIEW_ID, payload);
        }

        final GitHubPullRequestReview expected = CODEC.decode(
                jsonBytes(GitHubPullsReviewsApiIntegrationTest.class, "pull-request-review.json"),
                GitHubPullRequestReview.class);

        assertSoftly(softly -> {
            softly.assertThat(review).isEqualTo(expected);
            HTTP.verifyRequested(POST, EVENTS_PATH);
            HTTP.verifyBody(POST, EVENTS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
        });
    }
}
