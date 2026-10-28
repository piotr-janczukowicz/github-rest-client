package io.github.piotr_janczukowicz.github.api.reactions;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubReactionsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final long COMMENT_ID = 1L;
    private static final int ISSUE_NUMBER = 1347;
    private static final long RELEASE_ID = 1L;
    private static final long REACTION_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubReactionsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @ParameterizedTest
    @MethodSource("listHappyPathCalls")
    void list_shouldReturnReactions_whenResponseIsOk(final ListReactionsCall call, final String expectedUri)
            throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("reaction.json") + "]"));

        final GitHubPage<GitHubReaction> page = call.list(api());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ReactionsCatalogFixtures.reaction());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri).hasToString(expectedUri);
        });
    }

    @Test
    void listForCommitComment_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("reaction.json") + "]"));
        final GitHubListForCommitCommentFilter filter = GitHubListForCommitCommentFilterBuilder.builder()
                .content(GitHubReactionContent.HEART)
                .build();

        api().listForCommitComment(OWNER, REPO, COMMENT_ID, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("content", "heart");
    }

    @Test
    void listForIssueComment_shouldReturnReactions_whenFilterOverloadCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("reaction.json") + "]"));

        final GitHubPage<GitHubReaction> page =
                api().listForIssueComment(OWNER, REPO, COMMENT_ID, GitHubListForCommitCommentFilter.empty());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ReactionsCatalogFixtures.reaction());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/issues/comments/1/reactions?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listForIssue_shouldReturnReactions_whenFilterOverloadCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("reaction.json") + "]"));

        final GitHubPage<GitHubReaction> page =
                api().listForIssue(OWNER, REPO, ISSUE_NUMBER, GitHubListForCommitCommentFilter.empty());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ReactionsCatalogFixtures.reaction());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/issues/1347/reactions?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listForPullRequestReviewComment_shouldReturnReactions_whenFilterOverloadCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("reaction.json") + "]"));

        final GitHubPage<GitHubReaction> page = api().listForPullRequestReviewComment(
                        OWNER, REPO, COMMENT_ID, GitHubListForCommitCommentFilter.empty());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ReactionsCatalogFixtures.reaction());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/pulls/comments/1/reactions?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listForRelease_shouldReturnReactions_whenFilterOverloadCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("reaction.json") + "]"));

        final GitHubPage<GitHubReaction> page =
                api().listForRelease(OWNER, REPO, RELEASE_ID, GitHubListForReleaseFilter.empty());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ReactionsCatalogFixtures.reaction());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/releases/1/reactions?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listForCommitComment_shouldOmitPageQuery_whenGitHubNoneRequest() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("reaction.json") + "]"));

        final GitHubPage<GitHubReaction> page = api().listForCommitComment(
                        OWNER, REPO, COMMENT_ID, GitHubListForCommitCommentFilter.empty(), GitHubPageRequest.none());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ReactionsCatalogFixtures.reaction());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/comments/1/reactions");
            softly.assertThat(requestUri.getQuery()).isNull();
        });
    }

    @ParameterizedTest
    @MethodSource("createCalls")
    void create_shouldPostPayload_whenResponseIsCreated(final CreateReactionsCall call, final String expectedUri)
            throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("reaction.json")));

        final GitHubReaction reaction = call.create(api());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(reaction).isEqualTo(ReactionsCatalogFixtures.reaction());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString(expectedUri);
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo("{\"content\":\"heart\"}");
        });
    }

    @Test
    void createForIssue_shouldReturnReaction_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("reaction.json")));
        final GitHubCreateForCommitCommentPayload payload = ReactionsCatalogFixtures.createForCommitCommentPayload();

        final GitHubReaction reaction = api().createForIssue(OWNER, REPO, ISSUE_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(reaction).isEqualTo(ReactionsCatalogFixtures.reaction());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/issues/1347/reactions");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo("{\"content\":\"heart\"}");
        });
    }

    @ParameterizedTest
    @MethodSource("deleteCalls")
    void delete_shouldSendDelete_whenCalled(final ReactionsCall call, final String expectedUri) throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        call.execute(api());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString(expectedUri);
        });
    }

    @ParameterizedTest
    @MethodSource({"blankArgCalls", "unsupportedPaginationRequests"})
    void call_shouldThrowIllegalArgument_whenInvalid(final ReactionsCall call, final String messageFragment) {
        final GitHubReactionsApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @Test
    void listForCommitComment_shouldThrow_whenFilterNull() {
        final GitHubReactionsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listForCommitComment(OWNER, REPO, COMMENT_ID, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @ParameterizedTest
    @MethodSource("nullPayloadCalls")
    void create_shouldThrow_whenPayloadNull(final CreateReactionsCall call) {
        final GitHubReactionsApi api = api();

        final Throwable thrown = catchThrowable(() -> call.create(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    private GitHubReactionsApi api() {
        return new GitHubReactionsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubReactionsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<Arguments> listHappyPathCalls() {
        return Stream.of(
                Arguments.of(
                        (ListReactionsCall) api -> api.listForCommitComment(OWNER, REPO, COMMENT_ID),
                        "https://api.github.com/repos/octocat/hello-world/comments/1/reactions?page=1&"
                                + TestClients.perPageQuery()),
                Arguments.of(
                        (ListReactionsCall) api -> api.listForIssueComment(OWNER, REPO, COMMENT_ID),
                        "https://api.github.com/repos/octocat/hello-world/issues/comments/1/reactions?page=1&"
                                + TestClients.perPageQuery()),
                Arguments.of(
                        (ListReactionsCall) api -> api.listForIssue(OWNER, REPO, ISSUE_NUMBER),
                        "https://api.github.com/repos/octocat/hello-world/issues/1347/reactions?page=1&"
                                + TestClients.perPageQuery()),
                Arguments.of(
                        (ListReactionsCall) api -> api.listForPullRequestReviewComment(OWNER, REPO, COMMENT_ID),
                        "https://api.github.com/repos/octocat/hello-world/pulls/comments/1/reactions?page=1&"
                                + TestClients.perPageQuery()),
                Arguments.of(
                        (ListReactionsCall) api -> api.listForRelease(OWNER, REPO, RELEASE_ID),
                        "https://api.github.com/repos/octocat/hello-world/releases/1/reactions?page=1&"
                                + TestClients.perPageQuery()));
    }

    private static Stream<Arguments> createCalls() {
        final GitHubCreateForCommitCommentPayload payload = ReactionsCatalogFixtures.createForCommitCommentPayload();
        final GitHubCreateForReleasePayload releasePayload = ReactionsCatalogFixtures.createForReleasePayload();
        return Stream.of(
                Arguments.of(
                        (CreateReactionsCall) api -> api.createForCommitComment(OWNER, REPO, COMMENT_ID, payload),
                        "https://api.github.com/repos/octocat/hello-world/comments/1/reactions"),
                Arguments.of(
                        (CreateReactionsCall) api -> api.createForIssueComment(OWNER, REPO, COMMENT_ID, payload),
                        "https://api.github.com/repos/octocat/hello-world/issues/comments/1/reactions"),
                Arguments.of(
                        (CreateReactionsCall) api -> api.createForIssue(OWNER, REPO, ISSUE_NUMBER, payload),
                        "https://api.github.com/repos/octocat/hello-world/issues/1347/reactions"),
                Arguments.of(
                        (CreateReactionsCall)
                                api -> api.createForPullRequestReviewComment(OWNER, REPO, COMMENT_ID, payload),
                        "https://api.github.com/repos/octocat/hello-world/pulls/comments/1/reactions"),
                Arguments.of(
                        (CreateReactionsCall) api -> api.createForRelease(OWNER, REPO, RELEASE_ID, releasePayload),
                        "https://api.github.com/repos/octocat/hello-world/releases/1/reactions"));
    }

    private static Stream<Arguments> deleteCalls() {
        return Stream.of(
                Arguments.of(
                        (ReactionsCall) api -> api.deleteForCommitComment(OWNER, REPO, COMMENT_ID, REACTION_ID),
                        "https://api.github.com/repos/octocat/hello-world/comments/1/reactions/1"),
                Arguments.of(
                        (ReactionsCall) api -> api.deleteForIssueComment(OWNER, REPO, COMMENT_ID, REACTION_ID),
                        "https://api.github.com/repos/octocat/hello-world/issues/comments/1/reactions/1"),
                Arguments.of(
                        (ReactionsCall) api -> api.deleteForIssue(OWNER, REPO, ISSUE_NUMBER, REACTION_ID),
                        "https://api.github.com/repos/octocat/hello-world/issues/1347/reactions/1"),
                Arguments.of(
                        (ReactionsCall) api -> api.deleteForPullRequestComment(OWNER, REPO, COMMENT_ID, REACTION_ID),
                        "https://api.github.com/repos/octocat/hello-world/pulls/comments/1/reactions/1"),
                Arguments.of(
                        (ReactionsCall) api -> api.deleteForRelease(OWNER, REPO, RELEASE_ID, REACTION_ID),
                        "https://api.github.com/repos/octocat/hello-world/releases/1/reactions/1"));
    }

    private static Stream<Arguments> blankArgCalls() {
        final GitHubCreateForCommitCommentPayload payload = ReactionsCatalogFixtures.createForCommitCommentPayload();
        final GitHubCreateForReleasePayload releasePayload = ReactionsCatalogFixtures.createForReleasePayload();
        final GitHubListForCommitCommentFilter filter = GitHubListForCommitCommentFilter.empty();
        return Stream.of(
                Arguments.of((ReactionsCall) api -> api.listForCommitComment(" ", REPO, COMMENT_ID), "owner"),
                Arguments.of((ReactionsCall) api -> api.listForCommitComment(" ", REPO, COMMENT_ID, filter), "owner"),
                Arguments.of(
                        (ReactionsCall) api ->
                                api.listForCommitComment(" ", REPO, COMMENT_ID, filter, GitHubPageRequest.byPage()),
                        "owner"),
                Arguments.of(
                        (ReactionsCall) api -> api.createForCommitComment(" ", REPO, COMMENT_ID, payload), "owner"),
                Arguments.of(
                        (ReactionsCall) api -> api.deleteForCommitComment(" ", REPO, COMMENT_ID, REACTION_ID), "owner"),
                Arguments.of((ReactionsCall) api -> api.listForCommitComment(OWNER, " ", COMMENT_ID), "repo"),
                Arguments.of((ReactionsCall) api -> api.listForCommitComment(OWNER, " ", COMMENT_ID, filter), "repo"),
                Arguments.of(
                        (ReactionsCall) api ->
                                api.listForCommitComment(OWNER, " ", COMMENT_ID, filter, GitHubPageRequest.byPage()),
                        "repo"),
                Arguments.of(
                        (ReactionsCall) api -> api.createForCommitComment(OWNER, " ", COMMENT_ID, payload), "repo"),
                Arguments.of(
                        (ReactionsCall) api -> api.deleteForCommitComment(OWNER, " ", COMMENT_ID, REACTION_ID), "repo"),
                Arguments.of((ReactionsCall) api -> api.listForIssueComment(" ", REPO, COMMENT_ID), "owner"),
                Arguments.of((ReactionsCall) api -> api.listForIssue(" ", REPO, ISSUE_NUMBER), "owner"),
                Arguments.of(
                        (ReactionsCall) api -> api.listForPullRequestReviewComment(" ", REPO, COMMENT_ID), "owner"),
                Arguments.of((ReactionsCall) api -> api.listForRelease(" ", REPO, RELEASE_ID), "owner"),
                Arguments.of((ReactionsCall) api -> api.listForIssueComment(OWNER, " ", COMMENT_ID), "repo"),
                Arguments.of((ReactionsCall) api -> api.listForIssue(OWNER, " ", ISSUE_NUMBER), "repo"),
                Arguments.of(
                        (ReactionsCall) api -> api.listForPullRequestReviewComment(OWNER, " ", COMMENT_ID), "repo"),
                Arguments.of((ReactionsCall) api -> api.listForRelease(OWNER, " ", RELEASE_ID), "repo"),
                Arguments.of((ReactionsCall) api -> api.createForIssueComment(" ", REPO, COMMENT_ID, payload), "owner"),
                Arguments.of((ReactionsCall) api -> api.createForIssue(" ", REPO, ISSUE_NUMBER, payload), "owner"),
                Arguments.of(
                        (ReactionsCall) api -> api.createForPullRequestReviewComment(" ", REPO, COMMENT_ID, payload),
                        "owner"),
                Arguments.of(
                        (ReactionsCall) api -> api.createForRelease(" ", REPO, RELEASE_ID, releasePayload), "owner"),
                Arguments.of((ReactionsCall) api -> api.createForIssueComment(OWNER, " ", COMMENT_ID, payload), "repo"),
                Arguments.of((ReactionsCall) api -> api.createForIssue(OWNER, " ", ISSUE_NUMBER, payload), "repo"),
                Arguments.of(
                        (ReactionsCall) api -> api.createForPullRequestReviewComment(OWNER, " ", COMMENT_ID, payload),
                        "repo"),
                Arguments.of(
                        (ReactionsCall) api -> api.createForRelease(OWNER, " ", RELEASE_ID, releasePayload), "repo"),
                Arguments.of(
                        (ReactionsCall) api -> api.deleteForIssueComment(" ", REPO, COMMENT_ID, REACTION_ID), "owner"),
                Arguments.of((ReactionsCall) api -> api.deleteForIssue(" ", REPO, ISSUE_NUMBER, REACTION_ID), "owner"),
                Arguments.of(
                        (ReactionsCall) api -> api.deleteForPullRequestComment(" ", REPO, COMMENT_ID, REACTION_ID),
                        "owner"),
                Arguments.of((ReactionsCall) api -> api.deleteForRelease(" ", REPO, RELEASE_ID, REACTION_ID), "owner"),
                Arguments.of(
                        (ReactionsCall) api -> api.deleteForIssueComment(OWNER, " ", COMMENT_ID, REACTION_ID), "repo"),
                Arguments.of((ReactionsCall) api -> api.deleteForIssue(OWNER, " ", ISSUE_NUMBER, REACTION_ID), "repo"),
                Arguments.of(
                        (ReactionsCall) api -> api.deleteForPullRequestComment(OWNER, " ", COMMENT_ID, REACTION_ID),
                        "repo"),
                Arguments.of((ReactionsCall) api -> api.deleteForRelease(OWNER, " ", RELEASE_ID, REACTION_ID), "repo"));
    }

    private static Stream<Arguments> nullPayloadCalls() {
        return Stream.of(
                Arguments.of((CreateReactionsCall) api -> api.createForCommitComment(OWNER, REPO, COMMENT_ID, null)),
                Arguments.of((CreateReactionsCall) api -> api.createForIssueComment(OWNER, REPO, COMMENT_ID, null)),
                Arguments.of((CreateReactionsCall) api -> api.createForIssue(OWNER, REPO, ISSUE_NUMBER, null)),
                Arguments.of((CreateReactionsCall)
                        api -> api.createForPullRequestReviewComment(OWNER, REPO, COMMENT_ID, null)),
                Arguments.of((CreateReactionsCall) api -> api.createForRelease(OWNER, REPO, RELEASE_ID, null)));
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubListForCommitCommentFilter filter = GitHubListForCommitCommentFilter.empty();
        final GitHubPageRequest unsupportedCursor = GitHubPageRequest.byCursor();
        final GitHubPageRequest unsupportedSince = GitHubPageRequest.bySince();
        return Stream.of(
                Arguments.of(
                        (ReactionsCall)
                                api -> api.listForCommitComment(OWNER, REPO, COMMENT_ID, filter, unsupportedCursor),
                        "unsupported pagination request"),
                Arguments.of(
                        (ReactionsCall)
                                api -> api.listForCommitComment(OWNER, REPO, COMMENT_ID, filter, unsupportedSince),
                        "unsupported pagination request"));
    }

    @FunctionalInterface
    interface ListReactionsCall {
        GitHubPage<GitHubReaction> list(GitHubReactionsApi api) throws Exception;
    }

    @FunctionalInterface
    interface CreateReactionsCall {
        GitHubReaction create(GitHubReactionsApi api) throws Exception;
    }

    @FunctionalInterface
    interface ReactionsCall {
        void execute(GitHubReactionsApi api) throws Exception;
    }
}
