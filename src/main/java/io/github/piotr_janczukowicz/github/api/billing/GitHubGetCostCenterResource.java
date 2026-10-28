package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Resource assigned to a cost center.
 *
 * @param type Type of the resource.
 * @param name Name of the resource.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get-cost-center/properties/resources/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetCostCenterResource(String type, String name) {
    public GitHubGetCostCenterResource {
        requireNonNull(type, "type");
        requireNonNull(name, "name");
    }
}
