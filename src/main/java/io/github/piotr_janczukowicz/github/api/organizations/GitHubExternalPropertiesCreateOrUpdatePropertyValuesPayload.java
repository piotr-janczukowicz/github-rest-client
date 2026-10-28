package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Create or update external custom property values for a property across organization repositories request body.
 *
 * @param repositoryValues The names of repositories and the values that the named external custom property will be set
 *     to
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/orgs/external-properties-for-repos-create-or-update-values-for-organization-property/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubExternalPropertiesCreateOrUpdatePropertyValuesPayload(
        List<GitHubRepositoryExternalPropertyPayload> repositoryValues) {

    public GitHubExternalPropertiesCreateOrUpdatePropertyValuesPayload {
        repositoryValues = List.copyOf(requireSizeBetween(repositoryValues, 1, 100, "repositoryValues"));
    }
}
