package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Input for creating an OIDC custom property inclusion.
 *
 * @param customPropertyName The name of the custom property to include in the OIDC token
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/oidc-custom-property-inclusion-input")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubOidcCustomPropertyInclusionInput(String customPropertyName) {

    public GitHubOidcCustomPropertyInclusionInput {
        requireNonNull(customPropertyName, "customPropertyName");
    }
}
