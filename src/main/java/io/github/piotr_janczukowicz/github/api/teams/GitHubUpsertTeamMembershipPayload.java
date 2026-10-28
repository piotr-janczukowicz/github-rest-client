package io.github.piotr_janczukowicz.github.api.teams;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Upsert team membership.
 *
 * @param role The role that this user should have in the team.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/teams/add-or-update-membership-for-user-in-org/requestBody",
    "operations/teams/add-or-update-membership-for-user-legacy/requestBody"
})
public record GitHubUpsertTeamMembershipPayload(@Nullable GitHubTeamRole role) {}
