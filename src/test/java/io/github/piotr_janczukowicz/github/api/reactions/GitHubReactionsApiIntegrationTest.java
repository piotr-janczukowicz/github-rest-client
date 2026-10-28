package io.github.piotr_janczukowicz.github.api.reactions;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.reactionsApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@IntegrationTest
class GitHubReactionsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final long COMMENT_ID = 1L;
    private static final int ISSUE_NUMBER = 1347;
    private static final long RELEASE_ID = 1L;
    private static final long REACTION_ID = 1L;

    private static final String PATH_COMMIT_COMMENT = "/repos/octocat/hello-world/comments/1/reactions";
    private static final String PATH_ISSUE_COMMENT = "/repos/octocat/hello-world/issues/comments/1/reactions";
    private static final String PATH_ISSUE = "/repos/octocat/hello-world/issues/1347/reactions";
    private static final String PATH_PULL_COMMENT = "/repos/octocat/hello-world/pulls/comments/1/reactions";
    private static final String PATH_RELEASE = "/repos/octocat/hello-world/releases/1/reactions";

    private static final JsonType<List<GitHubReaction>> REACTIONS_TYPE = JsonTypes.listOf(GitHubReaction.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @ParameterizedTest
    @MethodSource("defaultListCalls")
    void list_shouldReturnReactions_whenResponseIsOk(final ListCall call) throws Exception {
        HTTP.stub(GET, call.path(), okJson(listJsonBytes(GitHubReactionsApiIntegrationTest.class, "reaction.json")));

        final GitHubPage<GitHubReaction> page;
        try (final GitHubApiWireMockSupport.ReactionsApiClient reactions = reactionsApi(HTTP)) {
            page = call.list(reactions.api());
        }

        final List<GitHubReaction> expected = expectedReactions();

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, call.path());
        HTTP.verifyQueryParam(GET, call.path(), "page", "1");
        HTTP.verifyQueryParam(GET, call.path(), "per_page", "100");
    }

    @ParameterizedTest
    @MethodSource("filterListCalls")
    void list_shouldSendContentFilter_whenFilterProvided(final ListCall call) throws Exception {
        HTTP.stub(GET, call.path(), okJson(listJsonBytes(GitHubReactionsApiIntegrationTest.class, "reaction.json")));

        try (final GitHubApiWireMockSupport.ReactionsApiClient reactions = reactionsApi(HTTP)) {
            call.list(reactions.api());
        }

        HTTP.verifyRequested(GET, call.path());
        HTTP.verifyQueryParam(GET, call.path(), "content", "heart");
    }

    @ParameterizedTest
    @MethodSource("pagedListCalls")
    void list_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest(final ListCall call) throws Exception {
        HTTP.stub(GET, call.path(), okJson(listJsonBytes(GitHubReactionsApiIntegrationTest.class, "reaction.json")));

        try (final GitHubApiWireMockSupport.ReactionsApiClient reactions = reactionsApi(HTTP)) {
            call.list(reactions.api());
        }

        HTTP.verifyQueryParam(GET, call.path(), "page", "2");
        HTTP.verifyQueryParam(GET, call.path(), "per_page", "10");
    }

    @ParameterizedTest
    @MethodSource("defaultListCalls")
    void list_shouldFollowLinkNext_whenNextPagePresent(final ListCall call) throws Exception {
        HTTP.stub(
                GET,
                call.path(),
                okJson(listJsonBytes(GitHubReactionsApiIntegrationTest.class, "reaction.json"))
                        .header(LINK, linkNext(HTTP.uri(call.path() + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubReactionsApiIntegrationTest.class, "reaction.json")));

        final GitHubPage<GitHubReaction> first;
        final Optional<GitHubPage<GitHubReaction>> second;
        final Optional<GitHubPage<GitHubReaction>> third;
        try (final GitHubApiWireMockSupport.ReactionsApiClient reactions = reactionsApi(HTTP)) {
            first = call.list(reactions.api());
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        final List<GitHubReaction> expected = expectedReactions();
        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expected);
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expected);
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, call.path(), 2);
    }

    @ParameterizedTest
    @MethodSource("createCalls")
    void create_shouldReturnReaction_whenResponseIsCreated(final CreateCall call) throws Exception {
        assertCreateReturnsReaction(
                call, createdJson(jsonBytes(GitHubReactionsApiIntegrationTest.class, "reaction.json")));
    }

    @ParameterizedTest
    @MethodSource("createCalls")
    void create_shouldReturnReaction_whenResponseIsOk(final CreateCall call) throws Exception {
        assertCreateReturnsReaction(call, okJson(jsonBytes(GitHubReactionsApiIntegrationTest.class, "reaction.json")));
    }

    @ParameterizedTest
    @MethodSource("createCalls")
    void create_shouldDecodeNullUser_whenFixtureHasNullUser(final CreateCall call) throws Exception {
        HTTP.stub(
                POST,
                call.path(),
                createdJson(jsonBytes(GitHubReactionsApiIntegrationTest.class, "reaction-null-user.json")));

        final GitHubReaction reaction;
        try (final GitHubApiWireMockSupport.ReactionsApiClient reactions = reactionsApi(HTTP)) {
            reaction = call.create(reactions.api());
        }

        final GitHubReaction expected = CODEC.decode(
                jsonBytes(GitHubReactionsApiIntegrationTest.class, "reaction-null-user.json"), GitHubReaction.class);

        assertSoftly(softly -> {
            softly.assertThat(reaction).isEqualTo(expected);
            softly.assertThat(reaction.user()).isNull();
        });
        HTTP.verifyRequested(POST, call.path());
    }

    @ParameterizedTest
    @MethodSource("deleteCalls")
    void delete_shouldComplete_whenResponseIsNoContent(final DeleteCall call) throws Exception {
        HTTP.stub(DELETE, call.path(), noContent());

        try (final GitHubApiWireMockSupport.ReactionsApiClient reactions = reactionsApi(HTTP)) {
            call.delete(reactions.api());
        }

        HTTP.verifyRequested(DELETE, call.path());
    }

    private static Stream<Arguments> defaultListCalls() {
        return Stream.of(
                Arguments.of(
                        new ListCall(PATH_COMMIT_COMMENT, api -> api.listForCommitComment(OWNER, REPO, COMMENT_ID))),
                Arguments.of(new ListCall(PATH_ISSUE_COMMENT, api -> api.listForIssueComment(OWNER, REPO, COMMENT_ID))),
                Arguments.of(new ListCall(PATH_ISSUE, api -> api.listForIssue(OWNER, REPO, ISSUE_NUMBER))),
                Arguments.of(new ListCall(
                        PATH_PULL_COMMENT, api -> api.listForPullRequestReviewComment(OWNER, REPO, COMMENT_ID))),
                Arguments.of(new ListCall(PATH_RELEASE, api -> api.listForRelease(OWNER, REPO, RELEASE_ID))));
    }

    private static Stream<Arguments> filterListCalls() {
        final GitHubListForCommitCommentFilter filter = GitHubListForCommitCommentFilterBuilder.builder()
                .content(GitHubReactionContent.HEART)
                .build();
        final GitHubListForReleaseFilter releaseFilter = GitHubListForReleaseFilterBuilder.builder()
                .content(GitHubReleaseReactionContent.HEART)
                .build();
        return Stream.of(
                Arguments.of(new ListCall(
                        PATH_COMMIT_COMMENT, api -> api.listForCommitComment(OWNER, REPO, COMMENT_ID, filter))),
                Arguments.of(new ListCall(
                        PATH_ISSUE_COMMENT, api -> api.listForIssueComment(OWNER, REPO, COMMENT_ID, filter))),
                Arguments.of(new ListCall(PATH_ISSUE, api -> api.listForIssue(OWNER, REPO, ISSUE_NUMBER, filter))),
                Arguments.of(new ListCall(
                        PATH_PULL_COMMENT,
                        api -> api.listForPullRequestReviewComment(OWNER, REPO, COMMENT_ID, filter))),
                Arguments.of(
                        new ListCall(PATH_RELEASE, api -> api.listForRelease(OWNER, REPO, RELEASE_ID, releaseFilter))));
    }

    private static Stream<Arguments> pagedListCalls() {
        final GitHubListForCommitCommentFilter filter = GitHubListForCommitCommentFilter.empty();
        final GitHubListForReleaseFilter releaseFilter = GitHubListForReleaseFilter.empty();
        final GitHubPageRequest request = GitHubPageNumberRequest.of(2, 10);
        return Stream.of(
                Arguments.of(new ListCall(
                        PATH_COMMIT_COMMENT,
                        api -> api.listForCommitComment(OWNER, REPO, COMMENT_ID, filter, request))),
                Arguments.of(new ListCall(
                        PATH_ISSUE_COMMENT, api -> api.listForIssueComment(OWNER, REPO, COMMENT_ID, filter, request))),
                Arguments.of(
                        new ListCall(PATH_ISSUE, api -> api.listForIssue(OWNER, REPO, ISSUE_NUMBER, filter, request))),
                Arguments.of(new ListCall(
                        PATH_PULL_COMMENT,
                        api -> api.listForPullRequestReviewComment(OWNER, REPO, COMMENT_ID, filter, request))),
                Arguments.of(new ListCall(
                        PATH_RELEASE, api -> api.listForRelease(OWNER, REPO, RELEASE_ID, releaseFilter, request))));
    }

    private static void assertCreateReturnsReaction(final CreateCall call, final StubHttpServer.StubResponse response)
            throws Exception {
        HTTP.stub(POST, call.path(), response);

        final GitHubReaction reaction;
        try (final GitHubApiWireMockSupport.ReactionsApiClient reactions = reactionsApi(HTTP)) {
            reaction = call.create(reactions.api());
        }

        final GitHubReaction expected =
                CODEC.decode(jsonBytes(GitHubReactionsApiIntegrationTest.class, "reaction.json"), GitHubReaction.class);

        assertThat(reaction).isEqualTo(expected);
        HTTP.verifyRequested(POST, call.path());
        HTTP.verifyHeader(POST, call.path(), CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, call.path(), "{\"content\":\"heart\"}");
    }

    private static Stream<Arguments> createCalls() {
        final GitHubCreateForCommitCommentPayload payload = ReactionsCatalogFixtures.createForCommitCommentPayload();
        final GitHubCreateForReleasePayload releasePayload = ReactionsCatalogFixtures.createForReleasePayload();
        return Stream.of(
                Arguments.of(new CreateCall(
                        PATH_COMMIT_COMMENT, api -> api.createForCommitComment(OWNER, REPO, COMMENT_ID, payload))),
                Arguments.of(new CreateCall(
                        PATH_ISSUE_COMMENT, api -> api.createForIssueComment(OWNER, REPO, COMMENT_ID, payload))),
                Arguments.of(new CreateCall(PATH_ISSUE, api -> api.createForIssue(OWNER, REPO, ISSUE_NUMBER, payload))),
                Arguments.of(new CreateCall(
                        PATH_PULL_COMMENT,
                        api -> api.createForPullRequestReviewComment(OWNER, REPO, COMMENT_ID, payload))),
                Arguments.of(new CreateCall(
                        PATH_RELEASE, api -> api.createForRelease(OWNER, REPO, RELEASE_ID, releasePayload))));
    }

    private static Stream<Arguments> deleteCalls() {
        return Stream.of(
                Arguments.of(new DeleteCall(
                        PATH_COMMIT_COMMENT + "/" + REACTION_ID,
                        api -> api.deleteForCommitComment(OWNER, REPO, COMMENT_ID, REACTION_ID))),
                Arguments.of(new DeleteCall(
                        PATH_ISSUE_COMMENT + "/" + REACTION_ID,
                        api -> api.deleteForIssueComment(OWNER, REPO, COMMENT_ID, REACTION_ID))),
                Arguments.of(new DeleteCall(
                        PATH_ISSUE + "/" + REACTION_ID,
                        api -> api.deleteForIssue(OWNER, REPO, ISSUE_NUMBER, REACTION_ID))),
                Arguments.of(new DeleteCall(
                        PATH_PULL_COMMENT + "/" + REACTION_ID,
                        api -> api.deleteForPullRequestComment(OWNER, REPO, COMMENT_ID, REACTION_ID))),
                Arguments.of(new DeleteCall(
                        PATH_RELEASE + "/" + REACTION_ID,
                        api -> api.deleteForRelease(OWNER, REPO, RELEASE_ID, REACTION_ID))));
    }

    private static List<GitHubReaction> expectedReactions() {
        return CODEC.decode(listJsonBytes(GitHubReactionsApiIntegrationTest.class, "reaction.json"), REACTIONS_TYPE);
    }

    @FunctionalInterface
    interface ListExecutor {
        GitHubPage<GitHubReaction> list(GitHubReactionsApi api) throws Exception;
    }

    record ListCall(String path, ListExecutor executor) {
        GitHubPage<GitHubReaction> list(final GitHubReactionsApi api) throws Exception {
            return executor.list(api);
        }
    }

    @FunctionalInterface
    interface CreateExecutor {
        GitHubReaction create(GitHubReactionsApi api) throws Exception;
    }

    record CreateCall(String path, CreateExecutor executor) {
        GitHubReaction create(final GitHubReactionsApi api) throws Exception {
            return executor.create(api);
        }
    }

    @FunctionalInterface
    interface DeleteExecutor {
        void delete(GitHubReactionsApi api) throws Exception;
    }

    record DeleteCall(String path, DeleteExecutor executor) {
        void delete(final GitHubReactionsApi api) throws Exception {
            executor.delete(api);
        }
    }
}
