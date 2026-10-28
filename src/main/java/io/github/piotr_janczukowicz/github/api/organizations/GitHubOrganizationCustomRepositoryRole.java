package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A custom repository role of an organization.
 *
 * @param id Unique identifier of the role
 * @param name Name of the role
 * @param description A short description about the role
 * @param baseRole The system role from which this role inherits permissions
 * @param permissions The permissions included in this role
 * @param organization The organization the custom role belongs to
 * @param createdAt Date and time the role was created
 * @param updatedAt Date and time the role was last updated
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/organization-custom-repository-role")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubOrganizationCustomRepositoryRole(
        long id,
        String name,
        @Nullable String description,
        GitHubOrganizationCustomRepositoryRoleBaseRole baseRole,
        List<String> permissions,
        GitHubSimpleUser organization,
        Instant createdAt,
        Instant updatedAt) {

    public GitHubOrganizationCustomRepositoryRole {
        requireNonNull(name, "name");
        requireNonNull(baseRole, "baseRole");
        requireNonNull(permissions, "permissions");
        requireNonNull(organization, "organization");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
