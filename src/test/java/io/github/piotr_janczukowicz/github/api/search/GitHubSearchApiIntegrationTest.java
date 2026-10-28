package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.forbidden;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.searchApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.serviceUnavailable;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.unprocessable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.exception.GitHubForbiddenException;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.client.exception.GitHubValidationException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@IntegrationTest
class GitHubSearchApiIntegrationTest {

    private static final String QUERY = "ruby";
    private static final String CODE_QUERY = "addClass in:file repo:jquery/jquery";
    private static final String USER_QUERY = "octocat";
    private static final String COMMIT_QUERY = "repo:octocat/Spoon-Knife+css";
    private static final String REPO_QUERY = "tetris+language:assembly";
    private static final String ISSUE_QUERY = "windows+label:bug+language:python+state:open";
    private static final long REPOSITORY_ID = 64_778_136L;

    private static final String PATH_TOPICS = "/search/topics";
    private static final String PATH_LABELS = "/search/labels";
    private static final String PATH_CODE = "/search/code";
    private static final String PATH_USERS = "/search/users";
    private static final String PATH_COMMITS = "/search/commits";
    private static final String PATH_REPOSITORIES = "/search/repositories";
    private static final String PATH_ISSUES = "/search/issues";

    private static final JsonType<GitHubSearchResponseBody<GitHubTopicSearchResultItem>> TOPICS_RESPONSE_TYPE =
            JsonTypes.of(GitHubSearchResponseBody.class, GitHubTopicSearchResultItem.class);
    private static final JsonType<GitHubSearchResponseBody<GitHubLabelSearchResultItem>> LABELS_RESPONSE_TYPE =
            JsonTypes.of(GitHubSearchResponseBody.class, GitHubLabelSearchResultItem.class);
    private static final JsonType<GitHubSearchResponseBody<GitHubCodeSearchResultItem>> CODE_RESPONSE_TYPE =
            JsonTypes.of(GitHubSearchResponseBody.class, GitHubCodeSearchResultItem.class);
    private static final JsonType<GitHubSearchResponseBody<GitHubUserSearchResultItem>> USERS_RESPONSE_TYPE =
            JsonTypes.of(GitHubSearchResponseBody.class, GitHubUserSearchResultItem.class);
    private static final JsonType<GitHubSearchResponseBody<GitHubCommitSearchResultItem>> COMMITS_RESPONSE_TYPE =
            JsonTypes.of(GitHubSearchResponseBody.class, GitHubCommitSearchResultItem.class);
    private static final JsonType<GitHubSearchResponseBody<GitHubRepoSearchResultItem>> REPOS_RESPONSE_TYPE =
            JsonTypes.of(GitHubSearchResponseBody.class, GitHubRepoSearchResultItem.class);
    private static final JsonType<GitHubSearchResponseBody<GitHubIssueSearchResultItem>> ISSUES_RESPONSE_TYPE =
            JsonTypes.of(GitHubSearchResponseBody.class, GitHubIssueSearchResultItem.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @ParameterizedTest
    @MethodSource("happyPathCalls")
    void search_shouldReturnResults_whenResponseIsOk(final HappyPathCall<?> call) throws Exception {
        HTTP.stub(GET, call.path(), okJson(jsonBytes(GitHubSearchApiIntegrationTest.class, call.fixture())));

        final GitHubPage<?> page;
        try (final GitHubApiWireMockSupport.SearchApiClient search = searchApi(HTTP)) {
            page = call.execute(search.api());
        }

        call.verify(page);
        HTTP.verifyRequested(GET, call.path());
    }

    @Test
    void searchCode_shouldSendTextMatchAccept_whenTextMatchEnabled() throws Exception {
        HTTP.stub(
                GET,
                PATH_CODE,
                okJson(jsonBytes(GitHubSearchApiIntegrationTest.class, "code-search-result-item-paginated.json")));
        final GitHubSearchCodeFilter filter = GitHubSearchCodeFilterBuilder.builder()
                .q(CODE_QUERY)
                .textMatch(true)
                .build();

        try (final GitHubApiWireMockSupport.SearchApiClient search = searchApi(HTTP)) {
            search.api().searchCode(filter);
        }

        HTTP.verifyHeader(GET, PATH_CODE, ACCEPT, "application/vnd.github.text-match+json");
    }

    @Test
    void searchTopics_shouldFollowNextPage_whenPerPageIsOne() throws Exception {
        final GitHubSearchResponseBody<GitHubTopicSearchResultItem> fullTopics = decodeTopicsFixture();
        final byte[] firstPage = CODEC.encode(new GitHubSearchResponseBody<>(
                2, false, List.of(fullTopics.items().getFirst()), null, null));
        final byte[] secondPage = CODEC.encode(new GitHubSearchResponseBody<>(
                2, false, List.of(fullTopics.items().get(1)), null, null));

        HTTP.stub(
                GET,
                PATH_TOPICS,
                okJson(firstPage).header(LINK, linkNext(HTTP.uri(PATH_TOPICS + "?q=" + QUERY + "&page=2&per_page=1"))),
                okJson(secondPage));

        final GitHubPage<GitHubTopicSearchResultItem> first;
        final Optional<GitHubPage<GitHubTopicSearchResultItem>> second;
        final Optional<GitHubPage<GitHubTopicSearchResultItem>> third;
        try (final GitHubApiWireMockSupport.SearchApiClient search = searchApi(HTTP)) {
            first = search.api()
                    .searchTopics(
                            GitHubSearchTopicsFilterBuilder.builder().q(QUERY).build(),
                            GitHubPageNumberRequest.of(1, 1));
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactly(fullTopics.items().getFirst());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items())
                    .containsExactly(fullTopics.items().get(1));
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, PATH_TOPICS, 2);
        HTTP.verifyQueryParam(GET, PATH_TOPICS, "page", "1");
        HTTP.verifyQueryParam(GET, PATH_TOPICS, "per_page", "1");
    }

    @ParameterizedTest
    @MethodSource("forbiddenCalls")
    void search_shouldThrowAuthorizationException_whenForbidden(final SearchCall call, final String path)
            throws Exception {
        HTTP.stub(GET, path, forbidden());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.SearchApiClient search = searchApi(HTTP)) {
            final GitHubSearchApi facade = search.api();

            thrown = catchThrowable(() -> call.execute(facade));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubForbiddenException.class);
        HTTP.verifyRequested(GET, path);
    }

    @ParameterizedTest
    @MethodSource("notFoundCalls")
    void search_shouldThrowNotFound_whenResponseIsNotFound(final SearchCall call, final String path) throws Exception {
        HTTP.stub(GET, path, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.SearchApiClient search = searchApi(HTTP)) {
            final GitHubSearchApi facade = search.api();

            thrown = catchThrowable(() -> call.execute(facade));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, path);
    }

    @ParameterizedTest
    @MethodSource("validationCalls")
    void search_shouldThrowValidationException_whenUnprocessableEntity(final SearchCall call, final String path)
            throws Exception {
        HTTP.stub(GET, path, unprocessable());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.SearchApiClient search = searchApi(HTTP)) {
            final GitHubSearchApi facade = search.api();

            thrown = catchThrowable(() -> call.execute(facade));
        }

        assertThat(thrown).isInstanceOf(GitHubValidationException.class);
        HTTP.verifyRequested(GET, path);
    }

    @ParameterizedTest
    @MethodSource("serviceUnavailableCalls")
    void search_shouldThrowApiException_whenServiceUnavailable(final SearchCall call, final String path)
            throws Exception {
        HTTP.stub(GET, path, serviceUnavailable());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.SearchApiClient search = searchApi(HTTP)) {
            final GitHubSearchApi facade = search.api();

            thrown = catchThrowable(() -> call.execute(facade));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubApiException.class);
        HTTP.verifyRequested(GET, path);
    }

    private static Stream<Arguments> happyPathCalls() {
        return Stream.of(
                Arguments.of(new HappyPathCall<>(
                        PATH_CODE,
                        "code-search-result-item-paginated.json",
                        CODE_RESPONSE_TYPE,
                        api -> api.searchCode(CODE_QUERY))),
                Arguments.of(new HappyPathCall<>(
                        PATH_COMMITS,
                        "commit-search-result-item-paginated.json",
                        COMMITS_RESPONSE_TYPE,
                        api -> api.searchCommits(COMMIT_QUERY))),
                Arguments.of(new HappyPathCall<>(
                        PATH_ISSUES,
                        "issue-search-result-item-paginated.json",
                        ISSUES_RESPONSE_TYPE,
                        api -> api.searchIssues(ISSUE_QUERY))),
                Arguments.of(new HappyPathCall<>(
                        PATH_LABELS,
                        "label-search-result-item-paginated.json",
                        LABELS_RESPONSE_TYPE,
                        api -> api.searchLabels(REPOSITORY_ID, QUERY))),
                Arguments.of(new HappyPathCall<>(
                        PATH_REPOSITORIES,
                        "repo-search-result-item-paginated.json",
                        REPOS_RESPONSE_TYPE,
                        api -> api.searchRepos(REPO_QUERY))),
                Arguments.of(new HappyPathCall<>(
                        PATH_TOPICS,
                        "topic-search-result-item-paginated.json",
                        TOPICS_RESPONSE_TYPE,
                        api -> api.searchTopics(QUERY))),
                Arguments.of(new HappyPathCall<>(
                        PATH_USERS,
                        "user-search-result-item-paginated.json",
                        USERS_RESPONSE_TYPE,
                        api -> api.searchUsers(USER_QUERY))));
    }

    private static Stream<Arguments> forbiddenCalls() {
        return Stream.of(
                Arguments.of((SearchCall) api -> api.searchCode(CODE_QUERY), PATH_CODE),
                Arguments.of((SearchCall) api -> api.searchIssues(ISSUE_QUERY), PATH_ISSUES),
                Arguments.of((SearchCall) api -> api.searchLabels(labelsFilter()), PATH_LABELS));
    }

    private static Stream<Arguments> notFoundCalls() {
        return Stream.of(Arguments.of((SearchCall) api -> api.searchLabels(labelsFilter()), PATH_LABELS));
    }

    private static Stream<Arguments> validationCalls() {
        return Stream.of(
                Arguments.of((SearchCall) api -> api.searchCode(CODE_QUERY), PATH_CODE),
                Arguments.of((SearchCall) api -> api.searchIssues(ISSUE_QUERY), PATH_ISSUES),
                Arguments.of((SearchCall) api -> api.searchLabels(labelsFilter()), PATH_LABELS),
                Arguments.of((SearchCall) api -> api.searchRepos(REPO_QUERY), PATH_REPOSITORIES),
                Arguments.of((SearchCall) api -> api.searchUsers(USER_QUERY), PATH_USERS));
    }

    private static Stream<Arguments> serviceUnavailableCalls() {
        return Stream.of(
                Arguments.of((SearchCall) api -> api.searchCode(CODE_QUERY), PATH_CODE),
                Arguments.of((SearchCall) api -> api.searchIssues(ISSUE_QUERY), PATH_ISSUES),
                Arguments.of((SearchCall) api -> api.searchRepos(REPO_QUERY), PATH_REPOSITORIES),
                Arguments.of((SearchCall) api -> api.searchUsers(USER_QUERY), PATH_USERS));
    }

    private static GitHubSearchLabelsFilter labelsFilter() {
        return GitHubSearchLabelsFilterBuilder.builder()
                .repositoryId(REPOSITORY_ID)
                .q(QUERY)
                .build();
    }

    private static GitHubSearchResponseBody<GitHubTopicSearchResultItem> decodeTopicsFixture() {
        return CODEC.decode(
                jsonBytes(GitHubSearchApiIntegrationTest.class, "topic-search-result-item-paginated.json"),
                TOPICS_RESPONSE_TYPE);
    }

    @FunctionalInterface
    interface SearchCall {
        void execute(GitHubSearchApi api) throws Exception;
    }

    record HappyPathCall<T>(
            String path, String fixture, JsonType<GitHubSearchResponseBody<T>> responseType, SearchApiCall<T> apiCall) {

        GitHubPage<T> execute(final GitHubSearchApi api) throws Exception {
            return apiCall.execute(api);
        }

        void verify(final GitHubPage<?> page) {
            final GitHubSearchResponseBody<T> expected =
                    CODEC.decode(jsonBytes(GitHubSearchApiIntegrationTest.class, fixture), responseType);

            @SuppressWarnings("unchecked")
            final List<T> actualItems = (List<T>) page.items();

            assertSoftly(softly -> {
                softly.assertThat(page.totalCount()).isEqualTo(expected.totalCount());
                softly.assertThat(page.areResultsIncomplete()).isEqualTo(expected.incompleteResults());
                softly.assertThat(actualItems).containsExactlyElementsOf(expected.items());
            });
        }
    }

    @FunctionalInterface
    interface SearchApiCall<T> {
        GitHubPage<T> execute(GitHubSearchApi api) throws Exception;
    }
}
