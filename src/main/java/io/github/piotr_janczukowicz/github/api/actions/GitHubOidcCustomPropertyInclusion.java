package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * An OIDC custom property inclusion for repository properties.
 *
 * @param customPropertyName The name of the custom property that is included in the OIDC token
 * @param inclusionSource Whether the inclusion was defined at the organization or enterprise level
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/oidc-custom-property-inclusion")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubOidcCustomPropertyInclusion(
        String customPropertyName, GitHubOidcCustomPropertyInclusionSource inclusionSource) {

    public GitHubOidcCustomPropertyInclusion {
        requireNonNull(customPropertyName, "customPropertyName");
        requireNonNull(inclusionSource, "inclusionSource");
    }
}
