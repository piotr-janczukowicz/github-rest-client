package io.github.piotr_janczukowicz.github.api.gists;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.gistsApi;
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
class GitHubGistsCommentsApiIntegrationTest {

    private static final String GIST_ID = "aa5a315d61ae9438b18d";
    private static final long COMMENT_ID = 1L;

    private static final String COMMENTS_PATH = "/gists/aa5a315d61ae9438b18d/comments";
    private static final String COMMENT_PATH = "/gists/aa5a315d61ae9438b18d/comments/1";

    private static final JsonType<List<GitHubGistComment>> COMMENTS_TYPE = JsonTypes.listOf(GitHubGistComment.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listComments_shouldReturnComments_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COMMENTS_PATH,
                okJson(listJsonBytes(GitHubGistsCommentsApiIntegrationTest.class, "gist-comment.json")));

        final GitHubPage<GitHubGistComment> page;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            page = api.api().comments().listComments(GIST_ID);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedComments());
        HTTP.verifyQueryParam(GET, COMMENTS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, COMMENTS_PATH, "per_page", "100");
    }

    @Test
    void listComments_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                COMMENTS_PATH,
                okJson(listJsonBytes(GitHubGistsCommentsApiIntegrationTest.class, "gist-comment.json"))
                        .header(LINK, linkNext(HTTP.uri(COMMENTS_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubGistsCommentsApiIntegrationTest.class, "gist-comment.json")));

        final GitHubPage<GitHubGistComment> first;
        final Optional<GitHubPage<GitHubGistComment>> second;
        final Optional<GitHubPage<GitHubGistComment>> third;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            first = api.api().comments().listComments(GIST_ID);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedComments());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedComments());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, COMMENTS_PATH, 2);
    }

    @Test
    void listComments_shouldSendPageAndPerPage_whenCustomPageRequest() throws Exception {
        HTTP.stub(
                GET,
                COMMENTS_PATH,
                okJson(listJsonBytes(GitHubGistsCommentsApiIntegrationTest.class, "gist-comment.json")));

        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            api.api().comments().listComments(GIST_ID, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, COMMENTS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, COMMENTS_PATH, "per_page", "10");
    }

    @Test
    void createComment_shouldReturnComment_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                COMMENTS_PATH,
                createdJson(jsonBytes(GitHubGistsCommentsApiIntegrationTest.class, "gist-comment.json")));
        final GitHubCreateCommentPayload payload = GistsCatalogFixtures.createCommentPayload();

        final GitHubGistComment comment;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            comment = api.api().comments().createComment(GIST_ID, payload);
        }

        assertThat(comment).isEqualTo(expectedComment());
        HTTP.verifyRequested(POST, COMMENTS_PATH);
        HTTP.verifyHeader(POST, COMMENTS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, COMMENTS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getComment_shouldReturnComment_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, COMMENT_PATH, okJson(jsonBytes(GitHubGistsCommentsApiIntegrationTest.class, "gist-comment.json")));

        final Optional<GitHubGistComment> comment;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            comment = api.api().comments().getComment(GIST_ID, COMMENT_ID);
        }

        assertThat(comment).contains(expectedComment());
        HTTP.verifyRequested(GET, COMMENT_PATH);
    }

    @Test
    void getComment_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, COMMENT_PATH, notFound());

        final Optional<GitHubGistComment> comment;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            comment = api.api().comments().getComment(GIST_ID, COMMENT_ID);
        }

        assertThat(comment).isEmpty();
    }

    @Test
    void updateComment_shouldReturnComment_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                COMMENT_PATH,
                okJson(jsonBytes(GitHubGistsCommentsApiIntegrationTest.class, "gist-comment.json")));
        final GitHubUpdateCommentPayload payload = GistsCatalogFixtures.updateCommentPayload();

        final GitHubGistComment comment;
        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            comment = api.api().comments().updateComment(GIST_ID, COMMENT_ID, payload);
        }

        assertThat(comment).isEqualTo(expectedComment());
        HTTP.verifyRequested(PATCH, COMMENT_PATH);
        HTTP.verifyHeader(PATCH, COMMENT_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, COMMENT_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteComment_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, COMMENT_PATH, noContent());

        try (final GitHubApiWireMockSupport.GistsApiClient api = gistsApi(HTTP)) {
            api.api().comments().deleteComment(GIST_ID, COMMENT_ID);
        }

        HTTP.verifyRequested(DELETE, COMMENT_PATH);
    }

    private static List<GitHubGistComment> expectedComments() {
        return CODEC.decode(
                listJsonBytes(GitHubGistsCommentsApiIntegrationTest.class, "gist-comment.json"), COMMENTS_TYPE);
    }

    private static GitHubGistComment expectedComment() {
        return CODEC.decode(
                jsonBytes(GitHubGistsCommentsApiIntegrationTest.class, "gist-comment.json"), GitHubGistComment.class);
    }
}
