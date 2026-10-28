package io.github.piotr_janczukowicz.github.api.teams;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Upsert team repository permission.
 *
 * @param permission The permission to grant the team on this repository. We accept the following permissions to be set:
 *     `pull`, `triage`, `push`, `maintain`, `admin` and you can also specify a custom repository role name, if the
 *     owning organization has defined any. If no permission is specified, the team's `permission` attribute will be
 *     used to determine what permission to grant the team on this repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/teams/add-or-update-repo-permissions-in-org/requestBody")
public record GitHubUpsertTeamRepositoryPermissionPayload(
        @Nullable String permission) {}
