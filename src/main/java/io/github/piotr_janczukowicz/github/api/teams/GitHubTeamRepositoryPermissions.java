package io.github.piotr_janczukowicz.github.api.teams;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Permissions.
 *
 * @param admin Admin
 * @param pull Pull
 * @param triage Triage
 * @param push Push
 * @param maintain Maintain
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/team-repository/properties/permissions")
@RecordBuilder
public record GitHubTeamRepositoryPermissions(
        boolean admin,
        boolean pull,
        @Nullable Boolean triage,
        boolean push,
        @Nullable Boolean maintain) {}
