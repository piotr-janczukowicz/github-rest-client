package io.github.piotr_janczukowicz.github.api.collaborators;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update repository invitation.
 *
 * @param permissions The permissions that the associated user will have on the repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/update-invitation/requestBody")
public record GitHubUpdateRepositoryInvitationPayload(
        @Nullable GitHubUpdateRepositoryInvitationPermission permissions) {}
