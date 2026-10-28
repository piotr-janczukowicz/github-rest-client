package io.github.piotr_janczukowicz.github.api.teams;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Upsert team repository permission.
 *
 * @param permission The permission to grant the team on this repository. We accept the following permissions to be set:
 *     `pull`, `push`, and `admin`. If no permission is specified, the team's `permission` attribute will be used to
 *     determine what permission to grant the team on this repository.
 * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams API.
 *     Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
 */
@Deprecated(since = "2020-01-21")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/teams/add-or-update-repo-permissions-legacy/requestBody")
public record GitHubUpsertTeamRepositoryPermissionLegacyPayload(
        @Nullable GitHubTeamLegacyPermission permission) {}
