package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/**
 * GitHub Teams team sync REST resource for API version {@code 2026-03-10}.
 *
 * <p>Inspect and connect identity provider groups with organization teams.
 */
public final class GitHubTeamsTeamSyncApi extends GitHubResourceApi {

    private static final PathTemplate ORG_IDP_GROUPS = PathTemplate.of("/orgs/{org}/team-sync/groups");
    private static final PathTemplate ORG_TEAM_IDP_GROUPS =
            PathTemplate.of("/orgs/{org}/teams/{team_slug}/team-sync/group-mappings");
    private static final PathTemplate TEAM_IDP_GROUPS = PathTemplate.of("/teams/{team_id}/team-sync/group-mappings");

    private static final String DEPRECATED_SINCE = "2020-01-21";

    private static final GitHubListSpec<GitHubGroupMappingGroup> IDP_GROUPS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE, GitHubGroupMapping.class, GitHubGroupMapping::groups, null);

    /**
     * Teams team sync resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubTeamsTeamSyncApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List IdP groups for an organization ({@code GET /orgs/{org}/team-sync/groups}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listIdpGroups(String, GitHubListIdpGroupsForOrgFilter,
     * GitHubPageRequest)}.
     *
     * @param org organization login
     * @return first HTTP page of IdP groups
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubGroupMappingGroup> listIdpGroups(final String org) throws IOException {
        return listIdpGroups(org, GitHubListIdpGroupsForOrgFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List IdP groups for an organization ({@code GET /orgs/{org}/team-sync/groups}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listIdpGroups(String, GitHubListIdpGroupsForOrgFilter,
     * GitHubPageRequest)}.
     *
     * @param org organization login
     * @param filter query filters
     * @return first HTTP page of IdP groups
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubGroupMappingGroup> listIdpGroups(
            final String org, final GitHubListIdpGroupsForOrgFilter filter) throws IOException {
        return listIdpGroups(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List IdP groups for an organization ({@code GET /orgs/{org}/team-sync/groups}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization login
     * @param request pagination token
     * @return first HTTP page of IdP groups
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubGroupMappingGroup> listIdpGroups(final String org, final GitHubPageRequest request)
            throws IOException {
        return listIdpGroups(org, GitHubListIdpGroupsForOrgFilter.empty(), request);
    }

    /**
     * List IdP groups for an organization ({@code GET /orgs/{org}/team-sync/groups}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization login
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of IdP groups
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubPage<GitHubGroupMappingGroup> listIdpGroups(
            final String org, final GitHubListIdpGroupsForOrgFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_IDP_GROUPS, filter.toQueryParameters(), request, IDP_GROUPS_SPEC, org);
    }

    /**
     * Get IdP group connections of a team ({@code GET /orgs/{org}/teams/{team_slug}/team-sync/group-mappings}).
     *
     * @param org organization login
     * @param slug team slug
     * @return the IdP groups connected to the team, or empty if GitHub has no such team
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubGroupMapping> listTeamIdpGroups(final String org, final String slug) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(ORG_TEAM_IDP_GROUPS, org, slug), GitHubGroupMapping.class);
    }

    /**
     * Create or update IdP group connections of a team ({@code PATCH
     * /orgs/{org}/teams/{team_slug}/team-sync/group-mappings}).
     *
     * @param org organization login
     * @param slug team slug
     * @param payload IdP groups that replace the connected groups of the team
     * @return the IdP groups connected to the team
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubGroupMapping createOrUpdateIdpGroupConnections(
            final String org, final String slug, final GitHubCreateOrUpdateIdpGroupConnectionsInOrgPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(ORG_TEAM_IDP_GROUPS, org, slug), payload), GitHubGroupMapping.class);
    }

    /**
     * Get IdP group connections of a team (legacy) ({@code GET /teams/{team_id}/team-sync/group-mappings}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #listTeamIdpGroups(String, String)}.
     *
     * @param teamId team id
     * @return the IdP groups connected to the team, or empty if GitHub has no such team
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHEC)
    public Optional<GitHubGroupMapping> listTeamIdpGroupsLegacy(final long teamId) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(get(TEAM_IDP_GROUPS, teamId), GitHubGroupMapping.class);
    }

    /**
     * Create or update IdP group connections of a team (legacy) ({@code PATCH
     * /teams/{team_id}/team-sync/group-mappings}).
     *
     * <p><strong>Legacy.</strong> Prefer {@link #createOrUpdateIdpGroupConnections(String, String,
     * GitHubCreateOrUpdateIdpGroupConnectionsInOrgPayload)}.
     *
     * @param teamId team id
     * @param payload IdP groups that replace the connected groups of the team
     * @return the IdP groups connected to the team
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams
     *     API. Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
     */
    @Deprecated(since = DEPRECATED_SINCE)
    @Flavors(GitHubFlavor.GHEC)
    public GitHubGroupMapping createOrUpdateIdpGroupConnectionsLegacy(
            final long teamId, final GitHubCreateOrUpdateIdpGroupConnectionsLegacyPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(TEAM_IDP_GROUPS, teamId), payload), GitHubGroupMapping.class);
    }
}
