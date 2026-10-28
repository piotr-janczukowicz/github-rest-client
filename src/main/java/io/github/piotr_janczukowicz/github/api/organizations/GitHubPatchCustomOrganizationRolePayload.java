package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request to update a custom organization role.
 *
 * @param name The name of the custom role
 * @param description A short description of the custom role
 * @param permissions The permissions included in this role
 * @param baseRole The system role from which this role inherits permissions
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/patch-custom-organization-role/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubPatchCustomOrganizationRolePayload(
        @Nullable String name,
        @Nullable String description,
        @Nullable List<String> permissions,
        @Nullable GitHubCustomOrganizationRoleUpdateBaseRole baseRole) {

    public GitHubPatchCustomOrganizationRolePayload {
        if (permissions != null) {
            permissions = List.copyOf(permissions);
        }
    }
}
