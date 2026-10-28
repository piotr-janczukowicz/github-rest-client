package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub repository rule suites REST resource for API version {@code 2026-03-10}. */
public final class GitHubRepositoriesRuleSuitesApi extends GitHubResourceApi {

    private static final PathTemplate RULE_SUITE =
            PathTemplate.of("/repos/{owner}/{repo}/rulesets/rule-suites/{rule_suite_id}");
    private static final PathTemplate RULE_SUITES = PathTemplate.of("/repos/{owner}/{repo}/rulesets/rule-suites");

    private static final JsonType<List<GitHubRuleSuiteListItem>> RULE_SUITES_TYPE =
            JsonTypes.listOf(GitHubRuleSuiteListItem.class);

    /**
     * Rule suites resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubRepositoriesRuleSuitesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List repository rule suites ({@code GET /repos/{owner}/{repo}/rulesets/rule-suites}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of rule suites
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRuleSuiteListItem> getRepoRuleSuites(final String owner, final String repo)
            throws IOException {
        return getRepoRuleSuites(owner, repo, GitHubGetRepoRuleSuitesFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List repository rule suites ({@code GET /repos/{owner}/{repo}/rulesets/rule-suites}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of rule suites
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRuleSuiteListItem> getRepoRuleSuites(
            final String owner,
            final String repo,
            final GitHubGetRepoRuleSuitesFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(RULE_SUITES, filter.toQueryParameters(), request, RULE_SUITES_TYPE, owner, repo);
    }

    /**
     * Get a repository rule suite ({@code GET /repos/{owner}/{repo}/rulesets/rule-suites/{rule_suite_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ruleSuiteId identifier of the rule suite
     * @return rule suite, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRuleSuite> getRepoRuleSuite(final String owner, final String repo, final long ruleSuiteId)
            throws IOException {

        return getRepoRuleSuite(owner, repo, ruleSuiteId, GitHubGetRepoRuleSuiteFilter.empty());
    }

    /**
     * Get a repository rule suite ({@code GET /repos/{owner}/{repo}/rulesets/rule-suites/{rule_suite_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param ruleSuiteId identifier of the rule suite
     * @param filter query filters
     * @return rule suite, or empty on HTTP 404
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRuleSuite> getRepoRuleSuite(
            final String owner, final String repo, final long ruleSuiteId, final GitHubGetRepoRuleSuiteFilter filter)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(RULE_SUITE, owner, repo, ruleSuiteId).withQuery(filter.toQueryParameters()), GitHubRuleSuite.class);
    }
}
