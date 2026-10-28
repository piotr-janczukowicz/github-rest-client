package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;

/** GitHub dependency review REST resource for API version {@code 2026-03-10}. */
public final class GitHubDependencyGraphDependencyReviewApi extends GitHubResourceApi {

    private static final PathTemplate COMPARE =
            PathTemplate.of("/repos/{owner}/{repo}/dependency-graph/compare/{basehead}");

    private static final JsonType<List<GitHubDependencyGraphDiffEntry>> DIFF_TYPE =
            JsonTypes.listOf(GitHubDependencyGraphDiffEntry.class);

    /**
     * Dependency review resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDependencyGraphDependencyReviewApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get a diff of the dependencies between commits ({@code GET
     * /repos/{owner}/{repo}/dependency-graph/compare/{basehead}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param basehead base and head Git revisions separated by {@code ...}
     * @return dependency changes (the endpoint is not paginated)
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code basehead} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubDependencyGraphDiffEntry> diffRange(final String owner, final String repo, final String basehead)
            throws IOException {
        return diffRange(owner, repo, basehead, GitHubDiffRangeFilter.empty());
    }

    /**
     * Get a diff of the dependencies between commits ({@code GET
     * /repos/{owner}/{repo}/dependency-graph/compare/{basehead}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param basehead base and head Git revisions separated by {@code ...}
     * @param filter query filters
     * @return dependency changes (the endpoint is not paginated)
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code basehead} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubDependencyGraphDiffEntry> diffRange(
            final String owner, final String repo, final String basehead, final GitHubDiffRangeFilter filter)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(get(COMPARE, owner, repo, basehead).withQuery(filter.toQueryParameters()), DIFF_TYPE);
    }
}
