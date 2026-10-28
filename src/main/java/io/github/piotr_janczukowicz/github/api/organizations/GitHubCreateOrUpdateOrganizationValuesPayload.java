package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCustomPropertyValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Create or update organization repository custom property values request body.
 *
 * @param repositoryNames Repository names
 * @param properties Custom property values
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/custom-properties-for-repos-create-or-update-organization-values/requestBody")
public record GitHubCreateOrUpdateOrganizationValuesPayload(
        List<String> repositoryNames, List<GitHubCustomPropertyValue> properties) {

    private static final String PARAM_PROPERTIES = "properties";
    private static final String PARAM_REPOSITORY_NAMES = "repositoryNames";

    public GitHubCreateOrUpdateOrganizationValuesPayload {
        repositoryNames = List.copyOf(requireSizeBetween(repositoryNames, 1, 30, PARAM_REPOSITORY_NAMES));
        properties = List.copyOf(requireNonNull(properties, PARAM_PROPERTIES));
    }
}
