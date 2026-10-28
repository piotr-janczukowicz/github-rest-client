package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Organization role.
 *
 * @param id The unique identifier of the role
 * @param name The name of the role
 * @param description A short description about who this role is for or what permissions it grants
 * @param baseRole The system role from which this role inherits permissions
 * @param source Source answers the question, "where did this role come from?"
 * @param permissions A list of permissions included in this role
 * @param organization Organization associated with the role
 * @param createdAt The date and time the role was created
 * @param updatedAt The date and time the role was last updated
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/organization-role")
public record GitHubOrganizationRole(
        long id,
        String name,
        @Nullable String description,
        @Nullable GitHubOrganizationRoleBaseRole baseRole,
        @Nullable GitHubOrganizationRoleSource source,
        List<String> permissions,
        @Nullable GitHubSimpleUser organization,
        Instant createdAt,
        Instant updatedAt) {

    public GitHubOrganizationRole {
        requireNonNull(name, "name");
        requireNonNull(permissions, "permissions");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
