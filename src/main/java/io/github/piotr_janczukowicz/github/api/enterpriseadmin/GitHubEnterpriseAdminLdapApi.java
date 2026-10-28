package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub Enterprise Server LDAP REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminLdapApi extends GitHubResourceApi {

    private static final PathTemplate TEAM_MAPPING = PathTemplate.of("/admin/ldap/teams/{team_id}/mapping");
    private static final PathTemplate USER_MAPPING = PathTemplate.of("/admin/ldap/users/{username}/mapping");
    private static final PathTemplate TEAM_SYNC = PathTemplate.of("/admin/ldap/teams/{team_id}/sync");
    private static final PathTemplate USER_SYNC = PathTemplate.of("/admin/ldap/users/{username}/sync");

    /**
     * Resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminLdapApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Sync the LDAP mapping of a team ({@code POST /admin/ldap/teams/{team_id}/sync}).
     *
     * @param teamId team id
     * @return queue status
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubSyncLdapMappingForTeam syncLdapMappingForTeam(final long teamId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(post(TEAM_SYNC, teamId), GitHubSyncLdapMappingForTeam.class);
    }

    /**
     * Sync the LDAP mapping of a user ({@code POST /admin/ldap/users/{username}/sync}).
     *
     * @param username user login
     * @return queue status
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubSyncLdapMappingForUser syncLdapMappingForUser(final String username) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(post(USER_SYNC, username), GitHubSyncLdapMappingForUser.class);
    }

    /**
     * Update the LDAP mapping of a team ({@code PATCH /admin/ldap/teams/{team_id}/mapping}).
     *
     * @param teamId team id
     * @param payload new LDAP distinguished name
     * @return team with its LDAP mapping
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubLdapMappingTeam updateLdapMappingForTeam(
            final long teamId, final GitHubUpdateLdapMappingForTeamPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(TEAM_MAPPING, teamId), payload), GitHubLdapMappingTeam.class);
    }

    /**
     * Update the LDAP mapping of a user ({@code PATCH /admin/ldap/users/{username}/mapping}).
     *
     * @param username user login
     * @param payload new LDAP distinguished name
     * @return user with its LDAP mapping
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubLdapMappingUser updateLdapMappingForUser(
            final String username, final GitHubUpdateLdapMappingForUserPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(USER_MAPPING, username), payload), GitHubLdapMappingUser.class);
    }
}
