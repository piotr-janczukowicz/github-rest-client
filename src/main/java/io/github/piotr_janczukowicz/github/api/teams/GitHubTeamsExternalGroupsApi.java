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
 * GitHub Teams external groups REST resource for API version {@code 2026-03-10}.
 *
 * <p>Inspect external groups and link or unlink them with organization teams.
 */
public final class GitHubTeamsExternalGroupsApi extends GitHubResourceApi {

    private static final PathTemplate ORG_EXTERNAL_GROUP = PathTemplate.of("/orgs/{org}/external-group/{group_id}");
    private static final PathTemplate ORG_EXTERNAL_GROUPS = PathTemplate.of("/orgs/{org}/external-groups");
    private static final PathTemplate ORG_TEAM_EXTERNAL_GROUPS =
            PathTemplate.of("/orgs/{org}/teams/{team_slug}/external-groups");

    private static final GitHubListSpec<GitHubExternalGroupsGroup> EXTERNAL_GROUPS_SPEC = GitHubListSpec.wrapper(
            GitHubPageRequestKindValidators.PAGE, GitHubExternalGroups.class, GitHubExternalGroups::groups, null);

    /**
     * Teams external groups resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubTeamsExternalGroupsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get an external group ({@code GET /orgs/{org}/external-group/{group_id}}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #externalIdpGroupInfo(String, long, GitHubPageRequest)}.
     *
     * @param org organization login
     * @param groupId external group ID
     * @return the external group with its teams and members, or empty if GitHub has no such group
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubExternalGroup> externalIdpGroupInfo(final String org, final long groupId) throws IOException {
        return externalIdpGroupInfo(org, groupId, GitHubPageRequest.byPage());
    }

    /**
     * Get an external group ({@code GET /orgs/{org}/external-group/{group_id}}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only. The pagination applies to the
     * members array.
     *
     * @param org organization login
     * @param groupId external group ID
     * @param request pagination token for the members array
     * @return the external group with its teams and members, or empty if GitHub has no such group
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubExternalGroup> externalIdpGroupInfo(
            final String org, final long groupId, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return caller.fetchOrEmpty(
                caller.pageParameters(
                        get(ORG_EXTERNAL_GROUP, org, groupId), request, GitHubPageRequestKindValidators.PAGE),
                GitHubExternalGroup.class);
    }

    /**
     * List external groups ({@code GET /orgs/{org}/external-groups}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listExternalIdpGroups(String,
     * GitHubListExternalIdpGroupsForOrgFilter, GitHubPageRequest)}.
     *
     * @param org organization login
     * @return first HTTP page of external groups
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubExternalGroupsGroup> listExternalIdpGroups(final String org) throws IOException {
        return listExternalIdpGroups(org, GitHubListExternalIdpGroupsForOrgFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List external groups ({@code GET /orgs/{org}/external-groups}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listExternalIdpGroups(String,
     * GitHubListExternalIdpGroupsForOrgFilter, GitHubPageRequest)}.
     *
     * @param org organization login
     * @param filter query filters
     * @return first HTTP page of external groups
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubExternalGroupsGroup> listExternalIdpGroups(
            final String org, final GitHubListExternalIdpGroupsForOrgFilter filter) throws IOException {
        return listExternalIdpGroups(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List external groups ({@code GET /orgs/{org}/external-groups}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization login
     * @param request pagination token
     * @return first HTTP page of external groups
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubExternalGroupsGroup> listExternalIdpGroups(
            final String org, final GitHubPageRequest request) throws IOException {
        return listExternalIdpGroups(org, GitHubListExternalIdpGroupsForOrgFilter.empty(), request);
    }

    /**
     * List external groups ({@code GET /orgs/{org}/external-groups}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization login
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of external groups
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubExternalGroupsGroup> listExternalIdpGroups(
            final String org, final GitHubListExternalIdpGroupsForOrgFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(ORG_EXTERNAL_GROUPS, filter.toQueryParameters(), request, EXTERNAL_GROUPS_SPEC, org);
    }

    /**
     * List external groups linked to a team ({@code GET /orgs/{org}/teams/{team_slug}/external-groups}).
     *
     * @param org organization login
     * @param slug team slug
     * @return the external groups linked to the team, or empty if GitHub has no such team
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubExternalGroups> listLinkedExternalIdpGroupsToTeam(final String org, final String slug)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(get(ORG_TEAM_EXTERNAL_GROUPS, org, slug), GitHubExternalGroups.class);
    }

    /**
     * Link an external group to a team ({@code PATCH /orgs/{org}/teams/{team_slug}/external-groups}).
     *
     * @param org organization login
     * @param slug team slug
     * @param payload external group to link
     * @return the linked external group
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubExternalGroup linkExternalIdpGroupToTeam(
            final String org, final String slug, final GitHubLinkExternalIdpGroupToTeamForOrgPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ORG_TEAM_EXTERNAL_GROUPS, org, slug), payload), GitHubExternalGroup.class);
    }

    /**
     * Unlink external groups from a team ({@code DELETE /orgs/{org}/teams/{team_slug}/external-groups}).
     *
     * @param org organization login
     * @param slug team slug
     * @throws IllegalArgumentException if {@code org} or {@code slug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void unlinkExternalIdpGroupFromTeam(final String org, final String slug) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(ORG_TEAM_EXTERNAL_GROUPS, org, slug));
    }
}
