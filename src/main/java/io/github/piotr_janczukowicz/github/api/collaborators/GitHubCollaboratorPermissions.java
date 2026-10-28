package io.github.piotr_janczukowicz.github.api.collaborators;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Collaborator permissions.
 *
 * @param pull Pull
 * @param triage Triage
 * @param push Push
 * @param maintain Maintain
 * @param admin Admin
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/collaborator/properties/permissions")
@RecordBuilder
public record GitHubCollaboratorPermissions(
        boolean pull,
        @Nullable Boolean triage,
        boolean push,
        @Nullable Boolean maintain,
        boolean admin) {}
