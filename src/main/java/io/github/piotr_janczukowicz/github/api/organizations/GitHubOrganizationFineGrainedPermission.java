package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A fine-grained permission that can be assigned to an organization custom role.
 *
 * @param name The name of the permission
 * @param description A short description of the permission
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/organization-fine-grained-permission")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubOrganizationFineGrainedPermission(String name, String description) {

    public GitHubOrganizationFineGrainedPermission {
        requireNonNull(name, "name");
        requireNonNull(description, "description");
    }
}
