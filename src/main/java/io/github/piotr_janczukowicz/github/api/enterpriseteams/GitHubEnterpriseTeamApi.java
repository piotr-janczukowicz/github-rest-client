package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.teams.GitHubEnterpriseTeam;
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

/** GitHub enterprise teams REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseTeamApi extends GitHubResourceApi {

    private static final PathTemplate ENTERPRISE_TEAMS = PathTemplate.of("/enterprises/{enterprise}/teams");
    private static final PathTemplate ENTERPRISE_TEAM = PathTemplate.of("/enterprises/{enterprise}/teams/{team_slug}");

    private static final JsonType<List<GitHubEnterpriseTeam>> TEAMS_TYPE = JsonTypes.listOf(GitHubEnterpriseTeam.class);

    /**
     * Enterprise teams resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseTeamApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List enterprise teams ({@code GET /enterprises/{enterprise}/teams}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listEnterpriseTeams(String, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @return first HTTP page of enterprise teams
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubEnterpriseTeam> listEnterpriseTeams(final String enterprise) throws IOException {
        return listEnterpriseTeams(enterprise, GitHubPageRequest.byPage());
    }

    /**
     * List enterprise teams ({@code GET /enterprises/{enterprise}/teams}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param request pagination token
     * @return first HTTP page of enterprise teams
     * @throws IllegalArgumentException if {@code enterprise} is empty, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubEnterpriseTeam> listEnterpriseTeams(
            final String enterprise, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ENTERPRISE_TEAMS, Map.of(), request, TEAMS_TYPE, enterprise);
    }

    /**
     * Create an enterprise team ({@code POST /enterprises/{enterprise}/teams}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload enterprise team to create
     * @return the created enterprise team
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubEnterpriseTeam createEnterpriseTeam(
            final String enterprise, final GitHubCreateEnterpriseTeamPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ENTERPRISE_TEAMS, enterprise), payload), GitHubEnterpriseTeam.class);
    }

    /**
     * Get an enterprise team ({@code GET /enterprises/{enterprise}/teams/{team_slug}}).
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param enterprise the slug version of the enterprise name
     * @param teamSlug the slug of the team name
     * @return the enterprise team with its member count, or empty if it does not exist
     * @throws IllegalArgumentException if {@code enterprise} or {@code teamSlug} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubEnterpriseTeamWithMemberCount> getEnterpriseTeam(
            final String enterprise, final String teamSlug) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(
                get(ENTERPRISE_TEAM, enterprise, teamSlug), GitHubEnterpriseTeamWithMemberCount.class);
    }

    /**
     * Update an enterprise team ({@code PATCH /enterprises/{enterprise}/teams/{team_slug}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param teamSlug the slug of the team name
     * @param payload fields of the enterprise team to change
     * @return the updated enterprise team
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code teamSlug} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubEnterpriseTeam updateEnterpriseTeam(
            final String enterprise, final String teamSlug, final GitHubUpdateEnterpriseTeamPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(patch(ENTERPRISE_TEAM, enterprise, teamSlug), payload), GitHubEnterpriseTeam.class);
    }

    /**
     * Delete an enterprise team ({@code DELETE /enterprises/{enterprise}/teams/{team_slug}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param teamSlug the slug of the team name
     * @throws IllegalArgumentException if {@code enterprise} or {@code teamSlug} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void deleteEnterpriseTeam(final String enterprise, final String teamSlug) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(ENTERPRISE_TEAM, enterprise, teamSlug));
    }
}
