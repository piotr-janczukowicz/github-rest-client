package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Team Membership.
 *
 * @param url Url
 * @param role The role of the user in the team.
 * @param state The state of the user's membership in the team.
 */
@CatalogSchema("components/schemas/team-membership")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubTeamMembership(String url, GitHubTeamRole role, GitHubTeamMembershipState state) {

    public GitHubTeamMembership {
        requireNonNull(url, "url");
        requireNonNull(role, "role");
        requireNonNull(state, "state");
    }
}
