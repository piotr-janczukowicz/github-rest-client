package io.github.piotr_janczukowicz.github.api.collaborators;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Repository collaborator permission.
 *
 * @param permission Permission
 * @param roleName Role name
 * @param user User
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/repository-collaborator-permission")
@RecordBuilder
public record GitHubRepositoryCollaboratorPermission(
        String permission, String roleName, @Nullable GitHubCollaborator user) {

    public GitHubRepositoryCollaboratorPermission {
        requireNonNull(permission, "permission");
        requireNonNull(roleName, "roleName");
    }
}
