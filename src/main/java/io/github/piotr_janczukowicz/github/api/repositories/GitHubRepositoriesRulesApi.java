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
import java.util.Map;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/** GitHub repository rules REST resource for API version {@code 2026-03-10}. */
public final class GitHubRepositoriesRulesApi extends GitHubResourceApi {

    private static final PathTemplate RULESETS = PathTemplate.of("/repos/{owner}/{repo}/rulesets");
    private static final PathTemplate RULESET = PathTemplate.of("/repos/{owner}/{repo}/rulesets/{ruleset_id}");
    private static final PathTemplate RULESET_HISTORY_VERSION =
            PathTemplate.of("/repos/{owner}/{repo}/rulesets/{ruleset_id}/history/{version_id}");
    private static final PathTemplate RULESET_HISTORY =
            PathTemplate.of("/repos/{owner}/{repo}/rulesets/{ruleset_id}/history");
    private static final PathTemplate BRANCH_RULES = PathTemplate.of("/repos/{owner}/{repo}/rules/branches/{+branch}");

    private static final JsonType<List<GitHubRepositoryRuleset>> RULESETS_TYPE =
            JsonTypes.listOf(GitHubRepositoryRuleset.class);
    private static final JsonType<List<GitHubRepositoryRuleDetailed>> RULES_DETAILED_TYPE =
            JsonTypes.listOf(GitHubRepositoryRuleDetailed.class);
    private static final JsonType<List<GitHubRulesetVersion>> RULESET_VERSIONS_TYPE =
            JsonTypes.listOf(GitHubRulesetVersion.class);

    /**
     * Rules resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubRepositoriesRulesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get rules for a branch ({@code GET /repos/{owner}/{repo}/rules/branches/{branch}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @return first HTTP page of rules
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepositoryRuleDetailed> getBranchRules(
            final String owner, final String repo, final String branch) throws IOException {
        return getBranchRules(owner, repo, branch, GitHubPageRequest.byPage());
    }

    /**
     * Get rules for a branch ({@code GET /repos/{owner}/{repo}/rules/branches/{branch}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param branch branch name
     * @param request pagination token
     * @return first HTTP page of rules
     * @throws IllegalArgumentException if any argument is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepositoryRuleDetailed> getBranchRules(
            final String owner, final String repo, final String branch, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(BRANCH_RULES, Map.of(), request, RULES_DETAILED_TYPE, owner, repo, branch);
    }

    /**
     * Get all repository rulesets ({@code GET /repos/{owner}/{repo}/rulesets}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first HTTP page of rulesets
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepositoryRuleset> getRepoRulesets(final String owner, final String repo)
            throws IOException {
        return getRepoRulesets(owner, repo, GitHubGetRepoRulesetsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * Get all repository rulesets ({@code GET /repos/{owner}/{repo}/rulesets}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of rulesets
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepositoryRuleset> getRepoRulesets(
            final String owner,
            final String repo,
            final GitHubGetRepoRulesetsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(RULESETS, filter.toQueryParameters(), request, RULESETS_TYPE, owner, repo);
    }

    /**
     * Create a repository ruleset ({@code POST /repos/{owner}/{repo}/rulesets}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload ruleset configuration
     * @return created ruleset
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRepositoryRuleset createRepoRuleset(
            final String owner, final String repo, final GitHubCreateRepoRulesetPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(RULESETS, owner, repo), payload), GitHubRepositoryRuleset.class);
    }

    /**
     * Get a repository ruleset ({@code GET /repos/{owner}/{repo}/rulesets/{ruleset_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param rulesetId identifier of the repository ruleset
     * @return ruleset, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRepositoryRuleset> getRepoRuleset(final String owner, final String repo, final long rulesetId)
            throws IOException {
        return getRepoRuleset(owner, repo, rulesetId, GitHubGetRepoRulesetFilter.empty());
    }

    /**
     * Get a repository ruleset ({@code GET /repos/{owner}/{repo}/rulesets/{ruleset_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param rulesetId identifier of the repository ruleset
     * @param filter query filters
     * @return ruleset, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRepositoryRuleset> getRepoRuleset(
            final String owner, final String repo, final long rulesetId, final GitHubGetRepoRulesetFilter filter)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetchOrEmpty(
                get(RULESET, owner, repo, rulesetId).withQuery(filter.toQueryParameters()),
                GitHubRepositoryRuleset.class);
    }

    /**
     * Update a repository ruleset ({@code PUT /repos/{owner}/{repo}/rulesets/{ruleset_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param rulesetId identifier of the repository ruleset
     * @param payload ruleset updates
     * @return updated ruleset
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRepositoryRuleset updateRepoRuleset(
            final String owner,
            final String repo,
            final long rulesetId,
            final @Nullable GitHubUpdateRepoRulesetPayload payload)
            throws IOException {

        return caller.fetch(
                caller.jsonIfPresent(put(RULESET, owner, repo, rulesetId), payload), GitHubRepositoryRuleset.class);
    }

    /**
     * Delete a repository ruleset ({@code DELETE /repos/{owner}/{repo}/rulesets/{ruleset_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param rulesetId identifier of the repository ruleset
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteRepoRuleset(final String owner, final String repo, final long rulesetId) throws IOException {
        caller.send(delete(RULESET, owner, repo, rulesetId));
    }

    /**
     * Get repository ruleset history ({@code GET /repos/{owner}/{repo}/rulesets/{ruleset_id}/history}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param rulesetId identifier of the repository ruleset
     * @return first HTTP page of ruleset versions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRulesetVersion> getRepoRulesetHistory(
            final String owner, final String repo, final long rulesetId) throws IOException {
        return getRepoRulesetHistory(owner, repo, rulesetId, GitHubPageRequest.byPage());
    }

    /**
     * Get repository ruleset history ({@code GET /repos/{owner}/{repo}/rulesets/{ruleset_id}/history}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param rulesetId identifier of the repository ruleset
     * @param request pagination token
     * @return first HTTP page of ruleset versions
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRulesetVersion> getRepoRulesetHistory(
            final String owner, final String repo, final long rulesetId, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(RULESET_HISTORY, Map.of(), request, RULESET_VERSIONS_TYPE, owner, repo, rulesetId);
    }

    /**
     * Get a repository ruleset version ({@code GET /repos/{owner}/{repo}/rulesets/{ruleset_id}/history/{version_id}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param rulesetId identifier of the repository ruleset
     * @param versionId identifier of the ruleset version
     * @return ruleset version, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRulesetVersionWithState> getRepoRulesetVersion(
            final String owner, final String repo, final long rulesetId, final long versionId) throws IOException {

        return caller.fetchOrEmpty(
                get(RULESET_HISTORY_VERSION, owner, repo, rulesetId, versionId), GitHubRulesetVersionWithState.class);
    }
}
