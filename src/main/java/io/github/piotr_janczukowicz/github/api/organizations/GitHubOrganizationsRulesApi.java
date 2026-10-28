package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleset;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRulesetVersion;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRulesetVersionWithState;
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

/** GitHub organization rules REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsRulesApi extends GitHubResourceApi {

    private static final PathTemplate RULESETS = PathTemplate.of("/orgs/{org}/rulesets");
    private static final PathTemplate RULESET = PathTemplate.of("/orgs/{org}/rulesets/{ruleset_id}");
    private static final PathTemplate RULESET_HISTORY_VERSION =
            PathTemplate.of("/orgs/{org}/rulesets/{ruleset_id}/history/{version_id}");
    private static final PathTemplate RULESET_HISTORY = PathTemplate.of("/orgs/{org}/rulesets/{ruleset_id}/history");

    private static final JsonType<List<GitHubRepositoryRuleset>> RULESETS_TYPE =
            JsonTypes.listOf(GitHubRepositoryRuleset.class);
    private static final JsonType<List<GitHubRulesetVersion>> RULESET_VERSIONS_TYPE =
            JsonTypes.listOf(GitHubRulesetVersion.class);

    /**
     * Rules resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsRulesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get all organization repository rulesets ({@code GET /orgs/{org}/rulesets}).
     *
     * @param org organization login
     * @return first HTTP page of rulesets
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepositoryRuleset> getRulesets(final String org) throws IOException {
        return getRulesets(org, GitHubListOrgRulesetsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * Get all organization repository rulesets ({@code GET /orgs/{org}/rulesets}).
     *
     * @param org organization login
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of rulesets
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRepositoryRuleset> getRulesets(
            final String org, final GitHubListOrgRulesetsFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(RULESETS, filter.toQueryParameters(), request, RULESETS_TYPE, org);
    }

    /**
     * Get an organization repository ruleset ({@code GET /orgs/{org}/rulesets/{ruleset_id}}).
     *
     * @param org organization login
     * @param rulesetId ruleset ID
     * @return ruleset, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRepositoryRuleset> getRuleset(final String org, final long rulesetId) throws IOException {
        return caller.fetchOrEmpty(get(RULESET, org, rulesetId), GitHubRepositoryRuleset.class);
    }

    /**
     * Create an organization repository ruleset ({@code POST /orgs/{org}/rulesets}).
     *
     * @param org organization login
     * @param payload ruleset configuration
     * @return created ruleset
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRepositoryRuleset createRuleset(final String org, final GitHubCreateOrgRulesetPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(RULESETS, org), payload), GitHubRepositoryRuleset.class);
    }

    /**
     * Update an organization repository ruleset ({@code PUT /orgs/{org}/rulesets/{ruleset_id}}).
     *
     * @param org organization login
     * @param rulesetId ruleset ID
     * @param payload ruleset updates
     * @return updated ruleset
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubRepositoryRuleset updateRuleset(
            final String org, final long rulesetId, final @Nullable GitHubUpdateOrgRulesetPayload payload)
            throws IOException {

        return caller.fetch(caller.jsonIfPresent(put(RULESET, org, rulesetId), payload), GitHubRepositoryRuleset.class);
    }

    /**
     * Delete an organization repository ruleset ({@code DELETE /orgs/{org}/rulesets/{ruleset_id}}).
     *
     * @param org organization login
     * @param rulesetId ruleset ID
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteRuleset(final String org, final long rulesetId) throws IOException {
        caller.send(delete(RULESET, org, rulesetId));
    }

    /**
     * Get organization ruleset history ({@code GET /orgs/{org}/rulesets/{ruleset_id}/history}).
     *
     * @param org organization login
     * @param rulesetId ruleset ID
     * @return first HTTP page of ruleset versions
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRulesetVersion> getRulesetHistory(final String org, final long rulesetId)
            throws IOException {
        return getRulesetHistory(org, rulesetId, GitHubPageRequest.byPage());
    }

    /**
     * Get organization ruleset history ({@code GET /orgs/{org}/rulesets/{ruleset_id}/history}).
     *
     * @param org organization login
     * @param rulesetId ruleset ID
     * @param request pagination token
     * @return first HTTP page of ruleset versions
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubRulesetVersion> getRulesetHistory(
            final String org, final long rulesetId, final GitHubPageRequest request) throws IOException {
        requireNonNull(request, PARAM_REQUEST);

        return pageList(RULESET_HISTORY, Map.of(), request, RULESET_VERSIONS_TYPE, org, rulesetId);
    }

    /**
     * Get an organization ruleset version ({@code GET /orgs/{org}/rulesets/{ruleset_id}/history/{version_id}}).
     *
     * @param org organization login
     * @param rulesetId ruleset ID
     * @param versionId version ID
     * @return ruleset version, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubRulesetVersionWithState> getRulesetVersion(
            final String org, final long rulesetId, final long versionId) throws IOException {

        return caller.fetchOrEmpty(
                get(RULESET_HISTORY_VERSION, org, rulesetId, versionId), GitHubRulesetVersionWithState.class);
    }
}
