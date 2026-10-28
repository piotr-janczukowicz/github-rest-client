package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.ok;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubIssueSearchType;
import io.github.piotr_janczukowicz.github.pagination.GitHubLexicalFallbackReason;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubSearchApiTest {

    private static final String QUERY = "ruby";
    private static final String CODE_QUERY = "addClass in:file repo:jquery/jquery";
    private static final String USER_QUERY = "octocat";
    private static final String COMMIT_QUERY = "repo:octocat/Spoon-Knife+css";
    private static final String REPO_QUERY = "tetris+language:assembly";
    private static final String ISSUE_QUERY = "windows+label:bug+language:python+state:open";
    private static final long REPOSITORY_ID = 64_778_136L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSearchApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void searchTopics_shouldReturnTopics_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("topic-search-result-item-paginated.json")));

        final GitHubPage<GitHubTopicSearchResultItem> page =
                new GitHubSearchApi(TestClients.caller(transport)).searchTopics(QUERY);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.totalCount()).isEqualTo(6);
            softly.assertThat(page.areResultsIncomplete()).isFalse();
            softly.assertThat(page.items())
                    .hasSize(6)
                    .first()
                    .extracting(GitHubTopicSearchResultItem::name)
                    .isEqualTo("ruby");
            softly.assertThat(requestUri).hasParameter("q", QUERY);
            softly.assertThat(requestUri).hasParameter("per_page", "100");
        });
    }

    @Test
    void searchTopics_shouldSendTextMatchAccept_whenTextMatchEnabled() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("topic-search-result-item-paginated.json")));
        final GitHubSearchTopicsFilter filter = GitHubSearchTopicsFilterBuilder.builder()
                .q(QUERY)
                .textMatch(true)
                .build();

        new GitHubSearchApi(TestClients.caller(transport)).searchTopics(filter);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.text-match+json");
    }

    @Test
    void searchTopics_shouldAcceptPageRequest_whenPageProvided() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("topic-search-result-item-paginated.json")));

        new GitHubSearchApi(TestClients.caller(transport))
                .searchTopics(
                        GitHubSearchTopicsFilterBuilder.builder().q(QUERY).build(), GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("page", "2");
            softly.assertThat(requestUri).hasParameter("per_page", "10");
        });
    }

    @Test
    void toQueryParameters_shouldReturnQ_whenFilterBuilt() {
        final GitHubSearchTopicsFilter filter =
                GitHubSearchTopicsFilterBuilder.builder().q(QUERY).build();

        assertSoftly(softly -> {
            softly.assertThat(filter.q()).isEqualTo(QUERY);
            softly.assertThat(filter.textMatch()).isFalse();
            softly.assertThat(filter.toQueryParameters()).containsExactlyEntriesOf(Map.of("q", QUERY));
        });
    }

    @Test
    void constructor_shouldThrow_whenTopicsQueryNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubSearchTopicsFilterBuilder.builder().build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("q");
    }

    @ParameterizedTest
    @MethodSource("blankQueryCalls")
    void searchTopics_shouldThrow_whenQueryBlank(final SearchCall call, final String messageFragment) {
        final GitHubSearchApi api = new GitHubSearchApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("nullFilterCalls")
    void searchTopics_shouldThrow_whenFilterNull(final SearchCall call) {
        final GitHubSearchApi api = new GitHubSearchApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void searchLabels_shouldReturnLabels_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("label-search-result-item-paginated.json")));

        final GitHubPage<GitHubLabelSearchResultItem> page =
                new GitHubSearchApi(TestClients.caller(transport)).searchLabels(REPOSITORY_ID, QUERY);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.totalCount()).isEqualTo(2);
            softly.assertThat(page.areResultsIncomplete()).isFalse();
            softly.assertThat(page.items())
                    .hasSize(2)
                    .first()
                    .extracting(GitHubLabelSearchResultItem::name)
                    .isEqualTo("enhancement");
            softly.assertThat(requestUri).hasParameter("repository_id", String.valueOf(REPOSITORY_ID));
            softly.assertThat(requestUri).hasParameter("q", QUERY);
            softly.assertThat(requestUri).hasParameter("per_page", "100");
        });
    }

    @Test
    void searchLabels_shouldSendTextMatchAccept_whenTextMatchEnabled() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("label-search-result-item-paginated.json")));
        final GitHubSearchLabelsFilter filter = GitHubSearchLabelsFilterBuilder.builder()
                .repositoryId(REPOSITORY_ID)
                .q(QUERY)
                .textMatch(true)
                .build();

        new GitHubSearchApi(TestClients.caller(transport)).searchLabels(filter);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.text-match+json");
    }

    @Test
    void searchLabels_shouldAcceptPageRequest_whenPageProvided() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("label-search-result-item-paginated.json")));

        new GitHubSearchApi(TestClients.caller(transport))
                .searchLabels(
                        GitHubSearchLabelsFilterBuilder.builder()
                                .repositoryId(REPOSITORY_ID)
                                .q(QUERY)
                                .build(),
                        GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("page", "2");
            softly.assertThat(requestUri).hasParameter("per_page", "10");
        });
    }

    @Test
    void searchLabels_shouldSendSortAndOrder_whenFilterIncludesThem() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("label-search-result-item-paginated.json")));
        final GitHubSearchLabelsFilter filter = GitHubSearchLabelsFilterBuilder.builder()
                .repositoryId(REPOSITORY_ID)
                .q(QUERY)
                .sort(GitHubLabelSearchSort.CREATED)
                .order(GitHubSearchOrder.ASC)
                .build();

        new GitHubSearchApi(TestClients.caller(transport)).searchLabels(filter);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("sort", "created");
            softly.assertThat(requestUri).hasParameter("order", "asc");
        });
    }

    @Test
    void toQueryParameters_shouldReturnRepositoryIdAndQ_whenLabelsFilterBuilt() {
        final GitHubSearchLabelsFilter filter = GitHubSearchLabelsFilterBuilder.builder()
                .repositoryId(REPOSITORY_ID)
                .q(QUERY)
                .build();

        assertSoftly(softly -> {
            softly.assertThat(filter.repositoryId()).isEqualTo(REPOSITORY_ID);
            softly.assertThat(filter.q()).isEqualTo(QUERY);
            softly.assertThat(filter.sort()).isNull();
            softly.assertThat(filter.order()).isNull();
            softly.assertThat(filter.textMatch()).isFalse();
            softly.assertThat(filter.toQueryParameters())
                    .containsExactly(Map.entry("repository_id", String.valueOf(REPOSITORY_ID)), Map.entry("q", QUERY));
        });
    }

    @Test
    void constructor_shouldThrow_whenLabelsQueryNull() {
        final Throwable thrown = catchThrowable(() -> GitHubSearchLabelsFilterBuilder.builder()
                .repositoryId(REPOSITORY_ID)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("q");
    }

    @Test
    void toQueryParameters_shouldEmitSort_whenLabelsSortPresent() {
        final GitHubSearchLabelsFilter filter = GitHubSearchLabelsFilterBuilder.builder()
                .repositoryId(REPOSITORY_ID)
                .q(QUERY)
                .sort(GitHubLabelSearchSort.UPDATED)
                .build();

        assertThat(filter.toQueryParameters())
                .containsExactly(
                        Map.entry("repository_id", String.valueOf(REPOSITORY_ID)),
                        Map.entry("q", QUERY),
                        Map.entry("sort", "updated"));
    }

    @Test
    void toQueryParameters_shouldEmitOrder_whenLabelsOrderPresent() {
        final GitHubSearchLabelsFilter filter = GitHubSearchLabelsFilterBuilder.builder()
                .repositoryId(REPOSITORY_ID)
                .q(QUERY)
                .order(GitHubSearchOrder.DESC)
                .build();

        assertThat(filter.toQueryParameters())
                .containsExactly(
                        Map.entry("repository_id", String.valueOf(REPOSITORY_ID)),
                        Map.entry("q", QUERY),
                        Map.entry("order", "desc"));
    }

    @ParameterizedTest
    @MethodSource("blankLabelQueryCalls")
    void searchLabels_shouldThrow_whenQueryBlank(final LabelSearchCall call, final String messageFragment) {
        final GitHubSearchApi api = new GitHubSearchApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("nullLabelFilterCalls")
    void searchLabels_shouldThrow_whenFilterNull(final LabelSearchCall call) {
        final GitHubSearchApi api = new GitHubSearchApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void searchCode_shouldReturnCode_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("code-search-result-item-paginated.json")));

        final GitHubPage<GitHubCodeSearchResultItem> page =
                new GitHubSearchApi(TestClients.caller(transport)).searchCode(CODE_QUERY);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.totalCount()).isEqualTo(2);
            softly.assertThat(page.areResultsIncomplete()).isFalse();
            softly.assertThat(page.items())
                    .hasSize(2)
                    .first()
                    .extracting(GitHubCodeSearchResultItem::name)
                    .isEqualTo("classes.js");
            softly.assertThat(requestUri).hasParameter("q", CODE_QUERY);
            softly.assertThat(requestUri).hasParameter("per_page", "100");
        });
    }

    @Test
    void searchCode_shouldSendSortAndOrder_whenFilterIncludesThem() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("code-search-result-item-paginated.json")));
        final GitHubSearchCodeFilter filter = GitHubSearchCodeFilterBuilder.builder()
                .q(CODE_QUERY)
                .sort(GitHubCodeSearchSort.INDEXED)
                .order(GitHubSearchOrder.ASC)
                .build();

        new GitHubSearchApi(TestClients.caller(transport)).searchCode(filter);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("sort", "indexed");
            softly.assertThat(requestUri).hasParameter("order", "asc");
        });
    }

    @Test
    void searchCode_shouldSendTextMatchAccept_whenTextMatchEnabled() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("code-search-result-item-paginated.json")));
        final GitHubSearchCodeFilter filter = GitHubSearchCodeFilterBuilder.builder()
                .q(CODE_QUERY)
                .textMatch(true)
                .build();

        new GitHubSearchApi(TestClients.caller(transport)).searchCode(filter);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.text-match+json");
    }

    @Test
    void toQueryParameters_shouldReturnQ_whenCodeFilterBuilt() {
        final GitHubSearchCodeFilter filter =
                GitHubSearchCodeFilterBuilder.builder().q(CODE_QUERY).build();

        assertSoftly(softly -> {
            softly.assertThat(filter.q()).isEqualTo(CODE_QUERY);
            softly.assertThat(filter.sort()).isNull();
            softly.assertThat(filter.order()).isNull();
            softly.assertThat(filter.textMatch()).isFalse();
            softly.assertThat(filter.toQueryParameters()).containsExactlyEntriesOf(Map.of("q", CODE_QUERY));
        });
    }

    @Test
    void constructor_shouldThrow_whenCodeQueryNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubSearchCodeFilterBuilder.builder().build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("q");
    }

    @ParameterizedTest
    @MethodSource("blankCodeQueryCalls")
    void searchCode_shouldThrow_whenQueryBlank(final CodeSearchCall call, final String messageFragment) {
        final GitHubSearchApi api = new GitHubSearchApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("nullCodeFilterCalls")
    void searchCode_shouldThrow_whenFilterNull(final CodeSearchCall call) {
        final GitHubSearchApi api = new GitHubSearchApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void searchUsers_shouldReturnUsers_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("user-search-result-item-paginated.json")));

        final GitHubPage<GitHubUserSearchResultItem> page =
                new GitHubSearchApi(TestClients.caller(transport)).searchUsers(USER_QUERY);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.totalCount()).isEqualTo(2);
            softly.assertThat(page.areResultsIncomplete()).isFalse();
            softly.assertThat(page.items())
                    .hasSize(2)
                    .first()
                    .extracting(GitHubUserSearchResultItem::login)
                    .isEqualTo("octocat");
            softly.assertThat(requestUri).hasParameter("q", USER_QUERY);
            softly.assertThat(requestUri).hasParameter("per_page", "100");
        });
    }

    @Test
    void searchUsers_shouldSendSortAndOrder_whenFilterIncludesThem() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("user-search-result-item-paginated.json")));
        final GitHubSearchUsersFilter filter = GitHubSearchUsersFilterBuilder.builder()
                .q(USER_QUERY)
                .sort(GitHubUserSearchSort.FOLLOWERS)
                .order(GitHubSearchOrder.DESC)
                .build();

        new GitHubSearchApi(TestClients.caller(transport)).searchUsers(filter);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("sort", "followers");
            softly.assertThat(requestUri).hasParameter("order", "desc");
        });
    }

    @Test
    void searchUsers_shouldSendTextMatchAccept_whenTextMatchEnabled() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("user-search-result-item-paginated.json")));
        final GitHubSearchUsersFilter filter = GitHubSearchUsersFilterBuilder.builder()
                .q(USER_QUERY)
                .textMatch(true)
                .build();

        new GitHubSearchApi(TestClients.caller(transport)).searchUsers(filter);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.text-match+json");
    }

    @Test
    void toQueryParameters_shouldReturnQ_whenUsersFilterBuilt() {
        final GitHubSearchUsersFilter filter =
                GitHubSearchUsersFilterBuilder.builder().q(USER_QUERY).build();

        assertSoftly(softly -> {
            softly.assertThat(filter.q()).isEqualTo(USER_QUERY);
            softly.assertThat(filter.sort()).isNull();
            softly.assertThat(filter.order()).isNull();
            softly.assertThat(filter.textMatch()).isFalse();
            softly.assertThat(filter.toQueryParameters()).containsExactlyEntriesOf(Map.of("q", USER_QUERY));
        });
    }

    @Test
    void constructor_shouldThrow_whenUsersQueryNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubSearchUsersFilterBuilder.builder().build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("q");
    }

    @ParameterizedTest
    @MethodSource("blankUserQueryCalls")
    void searchUsers_shouldThrow_whenQueryBlank(final UserSearchCall call, final String messageFragment) {
        final GitHubSearchApi api = new GitHubSearchApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("nullUserFilterCalls")
    void searchUsers_shouldThrow_whenFilterNull(final UserSearchCall call) {
        final GitHubSearchApi api = new GitHubSearchApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void searchCommits_shouldReturnCommits_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("commit-search-result-item-paginated.json")));

        final GitHubPage<GitHubCommitSearchResultItem> page =
                new GitHubSearchApi(TestClients.caller(transport)).searchCommits(COMMIT_QUERY);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.totalCount()).isEqualTo(2);
            softly.assertThat(page.areResultsIncomplete()).isFalse();
            softly.assertThat(page.items())
                    .hasSize(2)
                    .first()
                    .extracting(GitHubCommitSearchResultItem::sha)
                    .isEqualTo("bb4cc8d3b2e14b3af5df699876dd4ff3acd00b7f");
            softly.assertThat(requestUri).hasParameter("q", COMMIT_QUERY);
            softly.assertThat(requestUri).hasParameter("per_page", "100");
        });
    }

    @Test
    void searchCommits_shouldSendSortAndOrder_whenFilterIncludesThem() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("commit-search-result-item-paginated.json")));
        final GitHubSearchCommitsFilter filter = GitHubSearchCommitsFilterBuilder.builder()
                .q(COMMIT_QUERY)
                .sort(GitHubCommitSearchSort.AUTHOR_DATE)
                .order(GitHubSearchOrder.DESC)
                .build();

        new GitHubSearchApi(TestClients.caller(transport)).searchCommits(filter);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("sort", "author-date");
            softly.assertThat(requestUri).hasParameter("order", "desc");
        });
    }

    @Test
    void searchCommits_shouldSendTextMatchAccept_whenTextMatchEnabled() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("commit-search-result-item-paginated.json")));
        final GitHubSearchCommitsFilter filter = GitHubSearchCommitsFilterBuilder.builder()
                .q(COMMIT_QUERY)
                .textMatch(true)
                .build();

        new GitHubSearchApi(TestClients.caller(transport)).searchCommits(filter);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.text-match+json");
    }

    @Test
    void toQueryParameters_shouldReturnQ_whenCommitsFilterBuilt() {
        final GitHubSearchCommitsFilter filter =
                GitHubSearchCommitsFilterBuilder.builder().q(COMMIT_QUERY).build();

        assertSoftly(softly -> {
            softly.assertThat(filter.q()).isEqualTo(COMMIT_QUERY);
            softly.assertThat(filter.sort()).isNull();
            softly.assertThat(filter.order()).isNull();
            softly.assertThat(filter.textMatch()).isFalse();
            softly.assertThat(filter.toQueryParameters()).containsExactlyEntriesOf(Map.of("q", COMMIT_QUERY));
        });
    }

    @Test
    void constructor_shouldThrow_whenCommitsQueryNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubSearchCommitsFilterBuilder.builder().build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("q");
    }

    @ParameterizedTest
    @MethodSource("blankCommitQueryCalls")
    void searchCommits_shouldThrow_whenQueryBlank(final CommitSearchCall call, final String messageFragment) {
        final GitHubSearchApi api = new GitHubSearchApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("nullCommitFilterCalls")
    void searchCommits_shouldThrow_whenFilterNull(final CommitSearchCall call) {
        final GitHubSearchApi api = new GitHubSearchApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void searchRepos_shouldReturnRepos_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("repo-search-result-item-paginated.json")));

        final GitHubPage<GitHubRepoSearchResultItem> page =
                new GitHubSearchApi(TestClients.caller(transport)).searchRepos(REPO_QUERY);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.totalCount()).isEqualTo(2);
            softly.assertThat(page.areResultsIncomplete()).isFalse();
            softly.assertThat(page.items())
                    .hasSize(2)
                    .first()
                    .extracting(GitHubRepoSearchResultItem::name)
                    .isEqualTo("Tetris");
            softly.assertThat(requestUri).hasParameter("q", REPO_QUERY);
            softly.assertThat(requestUri).hasParameter("per_page", "100");
        });
    }

    @Test
    void searchRepos_shouldSendSortAndOrder_whenFilterIncludesThem() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("repo-search-result-item-paginated.json")));
        final GitHubSearchReposFilter filter = GitHubSearchReposFilterBuilder.builder()
                .q(REPO_QUERY)
                .sort(GitHubRepoSearchSort.STARS)
                .order(GitHubSearchOrder.DESC)
                .build();

        new GitHubSearchApi(TestClients.caller(transport)).searchRepos(filter);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("sort", "stars");
            softly.assertThat(requestUri).hasParameter("order", "desc");
        });
    }

    @Test
    void searchRepos_shouldSendTextMatchAccept_whenTextMatchEnabled() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("repo-search-result-item-paginated.json")));
        final GitHubSearchReposFilter filter = GitHubSearchReposFilterBuilder.builder()
                .q(REPO_QUERY)
                .textMatch(true)
                .build();

        new GitHubSearchApi(TestClients.caller(transport)).searchRepos(filter);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.text-match+json");
    }

    @Test
    void toQueryParameters_shouldReturnQ_whenReposFilterBuilt() {
        final GitHubSearchReposFilter filter =
                GitHubSearchReposFilterBuilder.builder().q(REPO_QUERY).build();

        assertSoftly(softly -> {
            softly.assertThat(filter.q()).isEqualTo(REPO_QUERY);
            softly.assertThat(filter.sort()).isNull();
            softly.assertThat(filter.order()).isNull();
            softly.assertThat(filter.textMatch()).isFalse();
            softly.assertThat(filter.toQueryParameters()).containsExactlyEntriesOf(Map.of("q", REPO_QUERY));
        });
    }

    @Test
    void constructor_shouldThrow_whenReposQueryNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubSearchReposFilterBuilder.builder().build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("q");
    }

    @ParameterizedTest
    @MethodSource("blankRepoQueryCalls")
    void searchRepos_shouldThrow_whenQueryBlank(final RepoSearchCall call, final String messageFragment) {
        final GitHubSearchApi api = new GitHubSearchApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("nullRepoFilterCalls")
    void searchRepos_shouldThrow_whenFilterNull(final RepoSearchCall call) {
        final GitHubSearchApi api = new GitHubSearchApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubSearchApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<Arguments> blankQueryCalls() {
        return Stream.of(Arguments.of((SearchCall) api -> api.searchTopics(" "), "q"));
    }

    private static Stream<SearchCall> nullFilterCalls() {
        return Stream.of(
                api -> api.searchTopics((GitHubSearchTopicsFilter) null),
                api -> api.searchTopics(null, GitHubPageRequest.byPage()));
    }

    private static Stream<Arguments> blankLabelQueryCalls() {
        return Stream.of(Arguments.of((LabelSearchCall) api -> api.searchLabels(REPOSITORY_ID, " "), "q"));
    }

    private static Stream<LabelSearchCall> nullLabelFilterCalls() {
        return Stream.of(api -> api.searchLabels(null), api -> api.searchLabels(null, GitHubPageRequest.byPage()));
    }

    private static Stream<Arguments> blankCodeQueryCalls() {
        return Stream.of(Arguments.of((CodeSearchCall) api -> api.searchCode(" "), "q"));
    }

    private static Stream<CodeSearchCall> nullCodeFilterCalls() {
        return Stream.of(
                api -> api.searchCode((GitHubSearchCodeFilter) null),
                api -> api.searchCode(null, GitHubPageRequest.byPage()));
    }

    private static Stream<Arguments> blankUserQueryCalls() {
        return Stream.of(Arguments.of((UserSearchCall) api -> api.searchUsers(" "), "q"));
    }

    private static Stream<UserSearchCall> nullUserFilterCalls() {
        return Stream.of(
                api -> api.searchUsers((GitHubSearchUsersFilter) null),
                api -> api.searchUsers(null, GitHubPageRequest.byPage()));
    }

    private static Stream<Arguments> blankCommitQueryCalls() {
        return Stream.of(Arguments.of((CommitSearchCall) api -> api.searchCommits(" "), "q"));
    }

    private static Stream<CommitSearchCall> nullCommitFilterCalls() {
        return Stream.of(
                api -> api.searchCommits((GitHubSearchCommitsFilter) null),
                api -> api.searchCommits(null, GitHubPageRequest.byPage()));
    }

    private static Stream<Arguments> blankRepoQueryCalls() {
        return Stream.of(Arguments.of((RepoSearchCall) api -> api.searchRepos(" "), "q"));
    }

    private static Stream<RepoSearchCall> nullRepoFilterCalls() {
        return Stream.of(
                api -> api.searchRepos((GitHubSearchReposFilter) null),
                api -> api.searchRepos(null, GitHubPageRequest.byPage()));
    }

    @Test
    void searchIssues_shouldReturnIssues_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("issue-search-result-item-paginated.json")));

        final GitHubPage<GitHubIssueSearchResultItem> page =
                new GitHubSearchApi(TestClients.caller(transport)).searchIssues(ISSUE_QUERY);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.totalCount()).isEqualTo(2);
            softly.assertThat(page.areResultsIncomplete()).isFalse();
            softly.assertThat(page.searchType()).isEqualTo(GitHubIssueSearchType.LEXICAL);
            softly.assertThat(page.items())
                    .hasSize(2)
                    .first()
                    .extracting(GitHubIssueSearchResultItem::title)
                    .isEqualTo("Found a bug");
            softly.assertThat(requestUri).hasParameter("q", ISSUE_QUERY);
            softly.assertThat(requestUri).hasParameter("per_page", "100");
        });
    }

    @Test
    void searchIssues_shouldExposeLexicalFallbackReasons_whenResponseHasThem() throws Exception {
        when(transport.execute(any())).thenReturn(ok("""
                {"total_count":0,"incomplete_results":false,"search_type":"lexical",
                 "lexical_fallback_reason":["quoted_text"],"items":[]}
                """));

        final GitHubPage<GitHubIssueSearchResultItem> page =
                new GitHubSearchApi(TestClients.caller(transport)).searchIssues(ISSUE_QUERY);

        assertThat(page.lexicalFallbackReasons()).containsExactly(GitHubLexicalFallbackReason.QUOTED_TEXT);
    }

    @Test
    void searchIssues_shouldSendAdvancedSearchAndSearchType_whenFilterIncludesThem() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("issue-search-result-item-paginated.json")));
        final GitHubSearchIssuesFilter filter = GitHubSearchIssuesFilterBuilder.builder()
                .q(ISSUE_QUERY)
                .advancedSearch("true")
                .searchType(GitHubIssueSearchQueryType.SEMANTIC)
                .sort(GitHubIssueSearchSort.CREATED)
                .order(GitHubSearchOrder.ASC)
                .build();

        new GitHubSearchApi(TestClients.caller(transport)).searchIssues(filter);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("advanced_search", "true");
            softly.assertThat(requestUri).hasParameter("search_type", "semantic");
            softly.assertThat(requestUri).hasParameter("sort", "created");
            softly.assertThat(requestUri).hasParameter("order", "asc");
        });
    }

    @Test
    void searchIssues_shouldSendTextMatchAccept_whenTextMatchEnabled() throws Exception {
        when(transport.execute(any())).thenReturn(ok(json("issue-search-result-item-paginated.json")));
        final GitHubSearchIssuesFilter filter = GitHubSearchIssuesFilterBuilder.builder()
                .q(ISSUE_QUERY)
                .textMatch(true)
                .build();

        new GitHubSearchApi(TestClients.caller(transport)).searchIssues(filter);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.headers().get(ACCEPT)).contains("application/vnd.github.text-match+json");
    }

    @Test
    void toQueryParameters_shouldReturnQ_whenIssuesFilterBuilt() {
        final GitHubSearchIssuesFilter filter =
                GitHubSearchIssuesFilterBuilder.builder().q(ISSUE_QUERY).build();

        assertSoftly(softly -> {
            softly.assertThat(filter.q()).isEqualTo(ISSUE_QUERY);
            softly.assertThat(filter.sort()).isNull();
            softly.assertThat(filter.order()).isNull();
            softly.assertThat(filter.advancedSearch()).isNull();
            softly.assertThat(filter.searchType()).isNull();
            softly.assertThat(filter.textMatch()).isFalse();
            softly.assertThat(filter.toQueryParameters()).containsExactlyEntriesOf(Map.of("q", ISSUE_QUERY));
        });
    }

    @Test
    void constructor_shouldThrow_whenIssuesQueryNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubSearchIssuesFilterBuilder.builder().build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("q");
    }

    @Test
    void toQueryParameters_shouldEmitAdvancedSearch_whenAdvancedSearchTrue() {
        final GitHubSearchIssuesFilter filter = GitHubSearchIssuesFilterBuilder.builder()
                .q(ISSUE_QUERY)
                .advancedSearch("true")
                .build();

        assertThat(filter.toQueryParameters())
                .containsExactlyInAnyOrderEntriesOf(Map.of("q", ISSUE_QUERY, "advanced_search", "true"));
    }

    @ParameterizedTest
    @MethodSource("blankIssueQueryCalls")
    void searchIssues_shouldThrow_whenQueryBlank(final IssueSearchCall call, final String messageFragment) {
        final GitHubSearchApi api = new GitHubSearchApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("nullIssueFilterCalls")
    void searchIssues_shouldThrow_whenFilterNull(final IssueSearchCall call) {
        final GitHubSearchApi api = new GitHubSearchApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    private static Stream<Arguments> blankIssueQueryCalls() {
        return Stream.of(Arguments.of((IssueSearchCall) api -> api.searchIssues(" "), "q"));
    }

    private static Stream<IssueSearchCall> nullIssueFilterCalls() {
        return Stream.of(
                api -> api.searchIssues((GitHubSearchIssuesFilter) null),
                api -> api.searchIssues(null, GitHubPageRequest.byPage()));
    }

    @FunctionalInterface
    interface SearchCall {
        void execute(GitHubSearchApi api) throws Exception;
    }

    @FunctionalInterface
    interface LabelSearchCall {
        void execute(GitHubSearchApi api) throws Exception;
    }

    @FunctionalInterface
    interface CodeSearchCall {
        void execute(GitHubSearchApi api) throws Exception;
    }

    @FunctionalInterface
    interface UserSearchCall {
        void execute(GitHubSearchApi api) throws Exception;
    }

    @FunctionalInterface
    interface CommitSearchCall {
        void execute(GitHubSearchApi api) throws Exception;
    }

    @FunctionalInterface
    interface RepoSearchCall {
        void execute(GitHubSearchApi api) throws Exception;
    }

    @FunctionalInterface
    interface IssueSearchCall {
        void execute(GitHubSearchApi api) throws Exception;
    }
}
