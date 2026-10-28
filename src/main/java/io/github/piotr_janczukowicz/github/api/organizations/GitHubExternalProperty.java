package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * External custom property defined for a GitHub App installation.
 *
 * @param propertyName The name of the external property
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/external-property")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubExternalProperty(String propertyName) {

    public GitHubExternalProperty {
        requireNonNull(propertyName, "propertyName");
    }
}
