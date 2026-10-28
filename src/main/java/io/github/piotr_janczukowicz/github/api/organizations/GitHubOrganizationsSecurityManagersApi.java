package io.github.piotr_janczukowicz.github.api.organizations;

import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamSimple;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;

/**
 * GitHub organization security managers REST resource for API version {@code 2026-03-10}.
 *
 * <p>**Closing down notice**. Prefer {@link GitHubOrganizationsOrganizationRolesApi}.
 *
 * @deprecated Closing down notice: this operation is closing down and will be removed starting January 1, 2026. Prefer
 *     organization roles. Deprecation date {@code 2024-12-01}; removal date {@code 2026-01-01}.
 */
@Deprecated(since = "2024-12-01")
public final class GitHubOrganizationsSecurityManagersApi extends GitHubResourceApi {

    private static final PathTemplate SECURITY_MANAGERS = PathTemplate.of("/orgs/{org}/security-managers");
    private static final PathTemplate SECURITY_MANAGERS_TEAM =
            PathTemplate.of("/orgs/{org}/security-managers/teams/{team_slug}");

    private static final JsonType<List<GitHubTeamSimple>> SECURITY_MANAGER_TEAMS_TYPE =
            JsonTypes.listOf(GitHubTeamSimple.class);

    /**
     * Security managers resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsSecurityManagersApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List security manager teams for an organization ({@code GET /orgs/{org}/security-managers}).
     *
     * @param org organization name
     * @return security manager teams
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Closing down notice: this operation is closing down and will be removed starting January 1, 2026.
     *     Prefer organization roles. Deprecation date {@code 2024-12-01}; removal date {@code 2026-01-01}.
     */
    @Deprecated
    public List<GitHubTeamSimple> list(final String org) throws IOException {
        return caller.fetch(get(SECURITY_MANAGERS, org), SECURITY_MANAGER_TEAMS_TYPE);
    }

    /**
     * Add a security manager team for an organization ({@code PUT /orgs/{org}/security-managers/teams/{team_slug}}).
     *
     * @param org organization name
     * @param teamSlug team slug
     * @throws IllegalArgumentException if {@code org} or {@code teamSlug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Closing down notice: this operation is closing down and will be removed starting January 1, 2026.
     *     Prefer organization roles. Deprecation date {@code 2024-12-01}; removal date {@code 2026-01-01}.
     */
    @Deprecated
    public void addTeam(final String org, final String teamSlug) throws IOException {
        caller.send(put(SECURITY_MANAGERS_TEAM, org, teamSlug));
    }

    /**
     * Remove a security manager team for an organization ({@code DELETE
     * /orgs/{org}/security-managers/teams/{team_slug}}).
     *
     * @param org organization name
     * @param teamSlug team slug
     * @throws IllegalArgumentException if {@code org} or {@code teamSlug} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Closing down notice: this operation is closing down and will be removed starting January 1, 2026.
     *     Prefer organization roles. Deprecation date {@code 2024-12-01}; removal date {@code 2026-01-01}.
     */
    @Deprecated
    public void removeTeam(final String org, final String teamSlug) throws IOException {
        caller.send(delete(SECURITY_MANAGERS_TEAM, org, teamSlug));
    }
}
