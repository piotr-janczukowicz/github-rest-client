package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
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

/** GitHub enterprise team organization assignments REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseTeamsEnterpriseTeamOrganizationsApi extends GitHubResourceApi {

    private static final PathTemplate TEAM_ORGANIZATIONS =
            PathTemplate.of("/enterprises/{enterprise}/teams/{enterprise-team}/organizations");
    private static final PathTemplate TEAM_ORGANIZATIONS_ADD =
            PathTemplate.of("/enterprises/{enterprise}/teams/{enterprise-team}/organizations/add");
    private static final PathTemplate TEAM_ORGANIZATIONS_REMOVE =
            PathTemplate.of("/enterprises/{enterprise}/teams/{enterprise-team}/organizations/remove");
    private static final PathTemplate TEAM_ORGANIZATION =
            PathTemplate.of("/enterprises/{enterprise}/teams/{enterprise-team}/organizations/{org}");

    private static final JsonType<List<GitHubOrganizationSimple>> ORGANIZATIONS_TYPE =
            JsonTypes.listOf(GitHubOrganizationSimple.class);

    /**
     * Enterprise team organization assignments resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseTeamsEnterpriseTeamOrganizationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List organization assignments of an enterprise team ({@code GET
     * /enterprises/{enterprise}/teams/{enterprise-team}/organizations}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listAssignments(String, String, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @param enterpriseTeam the slug or id of the enterprise team name
     * @return first HTTP page of organizations
     * @throws IllegalArgumentException if {@code enterprise} or {@code enterpriseTeam} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubOrganizationSimple> listAssignments(final String enterprise, final String enterpriseTeam)
            throws IOException {
        return listAssignments(enterprise, enterpriseTeam, GitHubPageRequest.byPage());
    }

    /**
     * List organization assignments of an enterprise team ({@code GET
     * /enterprises/{enterprise}/teams/{enterprise-team}/organizations}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param enterpriseTeam the slug or id of the enterprise team name
     * @param request pagination token
     * @return first HTTP page of organizations
     * @throws IllegalArgumentException if {@code enterprise} or {@code enterpriseTeam} is empty, or {@code request} is
     *     a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubOrganizationSimple> listAssignments(
            final String enterprise, final String enterpriseTeam, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(TEAM_ORGANIZATIONS, Map.of(), request, ORGANIZATIONS_TYPE, enterprise, enterpriseTeam);
    }

    /**
     * Assign an enterprise team to organizations ({@code POST
     * /enterprises/{enterprise}/teams/{enterprise-team}/organizations/add}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param enterpriseTeam the slug or id of the enterprise team name
     * @param payload slugs of the organizations to assign the team to
     * @return the organizations the team was assigned to
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code enterpriseTeam} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public List<GitHubOrganizationSimple> bulkAddAssignments(
            final String enterprise,
            final String enterpriseTeam,
            final GitHubBulkAddEnterpriseTeamOrganizationsPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(TEAM_ORGANIZATIONS_ADD, enterprise, enterpriseTeam), payload), ORGANIZATIONS_TYPE);
    }

    /**
     * Unassign an enterprise team from organizations ({@code POST
     * /enterprises/{enterprise}/teams/{enterprise-team}/organizations/remove}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param enterpriseTeam the slug or id of the enterprise team name
     * @param payload slugs of the organizations to unassign the team from
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code enterpriseTeam} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void bulkRemoveAssignments(
            final String enterprise,
            final String enterpriseTeam,
            final GitHubBulkRemoveEnterpriseTeamOrganizationsPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(TEAM_ORGANIZATIONS_REMOVE, enterprise, enterpriseTeam), payload));
    }

    /**
     * Get the assignment of an enterprise team to an organization ({@code GET
     * /enterprises/{enterprise}/teams/{enterprise-team}/organizations/{org}}).
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param enterprise the slug version of the enterprise name
     * @param enterpriseTeam the slug or id of the enterprise team name
     * @param org the organization name. The name is not case sensitive.
     * @return the organization, or empty if the team is not assigned to it
     * @throws IllegalArgumentException if {@code enterprise}, {@code enterpriseTeam}, or {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubOrganizationSimple> getAssignment(
            final String enterprise, final String enterpriseTeam, final String org) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(
                get(TEAM_ORGANIZATION, enterprise, enterpriseTeam, org), GitHubOrganizationSimple.class);
    }

    /**
     * Assign an enterprise team to an organization ({@code PUT
     * /enterprises/{enterprise}/teams/{enterprise-team}/organizations/{org}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param enterpriseTeam the slug or id of the enterprise team name
     * @param org the organization name. The name is not case sensitive.
     * @return the organization the team was assigned to
     * @throws IllegalArgumentException if {@code enterprise}, {@code enterpriseTeam}, or {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubOrganizationSimple addAssignment(
            final String enterprise, final String enterpriseTeam, final String org) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(put(TEAM_ORGANIZATION, enterprise, enterpriseTeam, org), GitHubOrganizationSimple.class);
    }

    /**
     * Unassign an enterprise team from an organization ({@code DELETE
     * /enterprises/{enterprise}/teams/{enterprise-team}/organizations/{org}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param enterpriseTeam the slug or id of the enterprise team name
     * @param org the organization name. The name is not case sensitive.
     * @throws IllegalArgumentException if {@code enterprise}, {@code enterpriseTeam}, or {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void removeAssignment(final String enterprise, final String enterpriseTeam, final String org)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(TEAM_ORGANIZATION, enterprise, enterpriseTeam, org));
    }
}
