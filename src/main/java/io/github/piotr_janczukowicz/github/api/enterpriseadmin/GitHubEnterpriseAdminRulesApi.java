package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleset;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRulesetVersion;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRulesetVersionWithState;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
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

/** GitHub Enterprise Cloud enterprise ruleset REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminRulesApi extends GitHubResourceApi {

    private static final PathTemplate RULESETS = PathTemplate.of("/enterprises/{enterprise}/rulesets");
    private static final PathTemplate RULESET = PathTemplate.of("/enterprises/{enterprise}/rulesets/{ruleset_id}");
    private static final PathTemplate RULESET_HISTORY =
            PathTemplate.of("/enterprises/{enterprise}/rulesets/{ruleset_id}/history");
    private static final PathTemplate RULESET_VERSION =
            PathTemplate.of("/enterprises/{enterprise}/rulesets/{ruleset_id}/history/{version_id}");

    private static final JsonType<List<GitHubRulesetVersion>> RULESET_HISTORY_TYPE =
            JsonTypes.listOf(GitHubRulesetVersion.class);

    /**
     * Enterprise ruleset resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminRulesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Create an enterprise ruleset ({@code POST /enterprises/{enterprise}/rulesets}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload the ruleset to create
     * @return the created ruleset
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubRepositoryRuleset createEnterpriseRuleset(
            final String enterprise, final GitHubCreateEnterpriseRulesetPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(RULESETS, enterprise), payload), GitHubRepositoryRuleset.class);
    }

    /**
     * Get an enterprise ruleset ({@code GET /enterprises/{enterprise}/rulesets/{ruleset_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param rulesetId the unique identifier of the ruleset
     * @return the ruleset, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubRepositoryRuleset> getEnterpriseRuleset(final String enterprise, final long rulesetId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(RULESET, enterprise, rulesetId), GitHubRepositoryRuleset.class);
    }

    /**
     * Update an enterprise ruleset ({@code PUT /enterprises/{enterprise}/rulesets/{ruleset_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param rulesetId the unique identifier of the ruleset
     * @param payload the ruleset fields to update, or {@code null} to send no body
     * @return the updated ruleset
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubRepositoryRuleset updateEnterpriseRuleset(
            final String enterprise, final long rulesetId, final @Nullable GitHubUpdateEnterpriseRulesetPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(
                caller.jsonIfPresent(put(RULESET, enterprise, rulesetId), payload), GitHubRepositoryRuleset.class);
    }

    /**
     * Delete an enterprise ruleset ({@code DELETE /enterprises/{enterprise}/rulesets/{ruleset_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param rulesetId the unique identifier of the ruleset
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void deleteEnterpriseRuleset(final String enterprise, final long rulesetId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(delete(RULESET, enterprise, rulesetId));
    }

    /**
     * Get the version history of an enterprise ruleset ({@code GET
     * /enterprises/{enterprise}/rulesets/{ruleset_id}/history}) on the first page.
     *
     * <p>See {@link #getEnterpriseRulesetHistory(String, long, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @param rulesetId the unique identifier of the ruleset
     * @return first HTTP page of ruleset versions
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubRulesetVersion> getEnterpriseRulesetHistory(final String enterprise, final long rulesetId)
            throws IOException {
        return getEnterpriseRulesetHistory(enterprise, rulesetId, GitHubPageRequest.byPage());
    }

    /**
     * Get the version history of an enterprise ruleset ({@code GET
     * /enterprises/{enterprise}/rulesets/{ruleset_id}/history}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param rulesetId the unique identifier of the ruleset
     * @param request page pagination token
     * @return page of ruleset versions
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubRulesetVersion> getEnterpriseRulesetHistory(
            final String enterprise, final long rulesetId, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(RULESET_HISTORY, Map.of(), request, RULESET_HISTORY_TYPE, enterprise, rulesetId);
    }

    /**
     * Get a version of an enterprise ruleset ({@code GET
     * /enterprises/{enterprise}/rulesets/{ruleset_id}/history/{version_id}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param rulesetId the unique identifier of the ruleset
     * @param versionId the unique identifier of the version
     * @return the ruleset version with its state, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubRulesetVersionWithState> getEnterpriseRulesetVersion(
            final String enterprise, final long rulesetId, final long versionId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(
                get(RULESET_VERSION, enterprise, rulesetId, versionId), GitHubRulesetVersionWithState.class);
    }
}
