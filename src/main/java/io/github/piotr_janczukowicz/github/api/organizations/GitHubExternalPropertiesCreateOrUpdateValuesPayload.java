package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCustomPropertyValue;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Create or update external custom property values for organization repositories request body.
 *
 * @param repositoryNames The names of repositories that the external custom property values will be applied to
 * @param properties List of external custom property names and associated values to apply to the repositories
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/external-properties-for-repos-create-or-update-organization-values/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubExternalPropertiesCreateOrUpdateValuesPayload(
        List<String> repositoryNames, List<GitHubCustomPropertyValue> properties) {

    private static final String PARAM_PROPERTIES = "properties";
    private static final String PARAM_REPOSITORY_NAMES = "repositoryNames";

    public GitHubExternalPropertiesCreateOrUpdateValuesPayload {
        repositoryNames = List.copyOf(requireSizeBetween(repositoryNames, 1, 30, PARAM_REPOSITORY_NAMES));
        properties = List.copyOf(requireNonNull(properties, PARAM_PROPERTIES));
    }
}
