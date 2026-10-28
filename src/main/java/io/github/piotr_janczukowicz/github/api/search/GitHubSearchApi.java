package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubSearchEnvelope;
import io.github.piotr_janczukowicz.http.client.HttpCall;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Map;

/**
 * GitHub Search REST resource for API version {@code 2026-03-10}.
 *
 * <p>GitHub returns at most {@link GitHubPage#SEARCH_RESULT_LIMIT} results per query, with at most 100 per page; a page
 * walk stops at that cap or at {@code total_count}, whichever is smaller. GitHub answers a request for a page beyond
 * the cap with HTTP 422, which surfaces as {@link GitHubApiException}. When GitHub timed out computing a query it
 * returns partial results and sets {@code incomplete_results}, readable through
 * {@link GitHubPage#areResultsIncomplete()}; the spec does not describe the cap or the 422.
 */
public final class GitHubSearchApi extends GitHubResourceApi {

    private static final PathTemplate SEARCH_TOPICS = PathTemplate.of("/search/topics");
    private static final PathTemplate SEARCH_LABELS = PathTemplate.of("/search/labels");
    private static final PathTemplate SEARCH_CODE = PathTemplate.of("/search/code");
    private static final PathTemplate SEARCH_USERS = PathTemplate.of("/search/users");
    private static final PathTemplate SEARCH_COMMITS = PathTemplate.of("/search/commits");
    private static final PathTemplate SEARCH_REPOSITORIES = PathTemplate.of("/search/repositories");
    private static final PathTemplate SEARCH_ISSUES = PathTemplate.of("/search/issues");

    private static final String TEXT_MATCH_MEDIA_TYPE = "application/vnd.github.text-match+json";

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

    private static final JsonType<GitHubSearchResponseBody<GitHubRepoSearchResultItem>> REPOSITORIES_RESPONSE_TYPE =
            JsonTypes.of(GitHubSearchResponseBody.class, GitHubRepoSearchResultItem.class);

    private static final JsonType<GitHubSearchResponseBody<GitHubIssueSearchResultItem>> ISSUES_RESPONSE_TYPE =
            JsonTypes.of(GitHubSearchResponseBody.class, GitHubIssueSearchResultItem.class);

    /**
     * Search resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubSearchApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Search topics ({@code GET /search/topics}).
     *
     * @param q search query
     * @return first HTTP page of topic search results
     * @throws IllegalArgumentException if {@code q} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTopicSearchResultItem> searchTopics(final String q) throws IOException {
        return searchTopics(GitHubSearchTopicsFilterBuilder.builder().q(q).build());
    }

    /**
     * Search topics ({@code GET /search/topics}).
     *
     * @param filter query filters
     * @return first HTTP page of topic search results
     * @throws IllegalArgumentException if {@code filter.q()} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTopicSearchResultItem> searchTopics(final GitHubSearchTopicsFilter filter)
            throws IOException {
        return searchTopics(filter, GitHubPageRequest.byPage());
    }

    /**
     * Search topics ({@code GET /search/topics}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} only.
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of topic search results
     * @throws IllegalArgumentException if {@code filter.q()} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubTopicSearchResultItem> searchTopics(
            final GitHubSearchTopicsFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.search(
                searchCall(SEARCH_TOPICS, filter.toQueryParameters(), filter.textMatch()),
                request,
                TOPICS_RESPONSE_TYPE,
                GitHubSearchApi::envelope);
    }

    /**
     * Search labels ({@code GET /search/labels}).
     *
     * @param repositoryId repository id
     * @param q search query
     * @return first HTTP page of label search results
     * @throws IllegalArgumentException if {@code q} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubLabelSearchResultItem> searchLabels(final long repositoryId, final String q)
            throws IOException {
        return searchLabels(GitHubSearchLabelsFilterBuilder.builder()
                .repositoryId(repositoryId)
                .q(q)
                .build());
    }

    /**
     * Search labels ({@code GET /search/labels}).
     *
     * @param filter query filters
     * @return first HTTP page of label search results
     * @throws IllegalArgumentException if {@code filter.q()} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubLabelSearchResultItem> searchLabels(final GitHubSearchLabelsFilter filter)
            throws IOException {
        return searchLabels(filter, GitHubPageRequest.byPage());
    }

    /**
     * Search labels ({@code GET /search/labels}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} only.
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of label search results
     * @throws IllegalArgumentException if {@code filter.q()} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubLabelSearchResultItem> searchLabels(
            final GitHubSearchLabelsFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.search(
                searchCall(SEARCH_LABELS, filter.toQueryParameters(), filter.textMatch()),
                request,
                LABELS_RESPONSE_TYPE,
                GitHubSearchApi::envelope);
    }

    /**
     * Search code ({@code GET /search/code}).
     *
     * @param q search query
     * @return first HTTP page of code search results
     * @throws IllegalArgumentException if {@code q} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeSearchResultItem> searchCode(final String q) throws IOException {
        return searchCode(GitHubSearchCodeFilterBuilder.builder().q(q).build());
    }

    /**
     * Search code ({@code GET /search/code}).
     *
     * @param filter query filters
     * @return first HTTP page of code search results
     * @throws IllegalArgumentException if {@code filter.q()} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeSearchResultItem> searchCode(final GitHubSearchCodeFilter filter) throws IOException {
        return searchCode(filter, GitHubPageRequest.byPage());
    }

    /**
     * Search code ({@code GET /search/code}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} only.
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of code search results
     * @throws IllegalArgumentException if {@code filter.q()} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCodeSearchResultItem> searchCode(
            final GitHubSearchCodeFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.search(
                searchCall(SEARCH_CODE, filter.toQueryParameters(), filter.textMatch()),
                request,
                CODE_RESPONSE_TYPE,
                GitHubSearchApi::envelope);
    }

    /**
     * Search users ({@code GET /search/users}).
     *
     * @param q search query
     * @return first HTTP page of user search results
     * @throws IllegalArgumentException if {@code q} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubUserSearchResultItem> searchUsers(final String q) throws IOException {
        return searchUsers(GitHubSearchUsersFilterBuilder.builder().q(q).build());
    }

    /**
     * Search users ({@code GET /search/users}).
     *
     * @param filter query filters
     * @return first HTTP page of user search results
     * @throws IllegalArgumentException if {@code filter.q()} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubUserSearchResultItem> searchUsers(final GitHubSearchUsersFilter filter) throws IOException {
        return searchUsers(filter, GitHubPageRequest.byPage());
    }

    /**
     * Search users ({@code GET /search/users}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} only.
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of user search results
     * @throws IllegalArgumentException if {@code filter.q()} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubUserSearchResultItem> searchUsers(
            final GitHubSearchUsersFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.search(
                searchCall(SEARCH_USERS, filter.toQueryParameters(), filter.textMatch()),
                request,
                USERS_RESPONSE_TYPE,
                GitHubSearchApi::envelope);
    }

    /**
     * Search commits ({@code GET /search/commits}).
     *
     * @param q search query
     * @return first HTTP page of commit search results
     * @throws IllegalArgumentException if {@code q} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCommitSearchResultItem> searchCommits(final String q) throws IOException {
        return searchCommits(GitHubSearchCommitsFilterBuilder.builder().q(q).build());
    }

    /**
     * Search commits ({@code GET /search/commits}).
     *
     * @param filter query filters
     * @return first HTTP page of commit search results
     * @throws IllegalArgumentException if {@code filter.q()} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCommitSearchResultItem> searchCommits(final GitHubSearchCommitsFilter filter)
            throws IOException {
        return searchCommits(filter, GitHubPageRequest.byPage());
    }

    /**
     * Search commits ({@code GET /search/commits}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} only.
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of commit search results
     * @throws IllegalArgumentException if {@code filter.q()} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubCommitSearchResultItem> searchCommits(
            final GitHubSearchCommitsFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.search(
                searchCall(SEARCH_COMMITS, filter.toQueryParameters(), filter.textMatch()),
                request,
                COMMITS_RESPONSE_TYPE,
                GitHubSearchApi::envelope);
    }

    /**
     * Search repositories ({@code GET /search/repositories}).
     *
     * @param q search query
     * @return first HTTP page of repository search results
     * @throws IllegalArgumentException if {@code q} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepoSearchResultItem> searchRepos(final String q) throws IOException {
        return searchRepos(GitHubSearchReposFilterBuilder.builder().q(q).build());
    }

    /**
     * Search repositories ({@code GET /search/repositories}).
     *
     * @param filter query filters
     * @return first HTTP page of repository search results
     * @throws IllegalArgumentException if {@code filter.q()} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepoSearchResultItem> searchRepos(final GitHubSearchReposFilter filter) throws IOException {
        return searchRepos(filter, GitHubPageRequest.byPage());
    }

    /**
     * Search repositories ({@code GET /search/repositories}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} only.
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of repository search results
     * @throws IllegalArgumentException if {@code filter.q()} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepoSearchResultItem> searchRepos(
            final GitHubSearchReposFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.search(
                searchCall(SEARCH_REPOSITORIES, filter.toQueryParameters(), filter.textMatch()),
                request,
                REPOSITORIES_RESPONSE_TYPE,
                GitHubSearchApi::envelope);
    }

    /**
     * Search issues and pull requests ({@code GET /search/issues}).
     *
     * @param q search query
     * @return first HTTP page of issue search results
     * @throws IllegalArgumentException if {@code q} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssueSearchResultItem> searchIssues(final String q) throws IOException {
        return searchIssues(GitHubSearchIssuesFilterBuilder.builder().q(q).build());
    }

    /**
     * Search issues and pull requests ({@code GET /search/issues}).
     *
     * @param filter query filters
     * @return first HTTP page of issue search results
     * @throws IllegalArgumentException if {@code filter.q()} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssueSearchResultItem> searchIssues(final GitHubSearchIssuesFilter filter)
            throws IOException {
        return searchIssues(filter, GitHubPageRequest.byPage());
    }

    /**
     * Search issues and pull requests ({@code GET /search/issues}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} only.
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of issue search results
     * @throws IllegalArgumentException if {@code filter.q()} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubIssueSearchResultItem> searchIssues(
            final GitHubSearchIssuesFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.search(
                searchCall(SEARCH_ISSUES, filter.toQueryParameters(), filter.textMatch()),
                request,
                ISSUES_RESPONSE_TYPE,
                GitHubSearchApi::envelope);
    }

    private HttpCall searchCall(final PathTemplate path, final Map<String, String> query, final boolean textMatch) {
        final HttpCall call = get(path).withQuery(query);
        if (textMatch) {
            return call.withHeader(ACCEPT, TEXT_MATCH_MEDIA_TYPE);
        }
        return call;
    }

    private static <T> GitHubSearchEnvelope<T> envelope(final GitHubSearchResponseBody<T> dto) {
        return new GitHubSearchEnvelope<>(
                dto.totalCount(), dto.incompleteResults(), dto.items(), dto.searchType(), dto.lexicalFallbackReasons());
    }
}
