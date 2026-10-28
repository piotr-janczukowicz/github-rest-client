package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Custom property filter that matches organizations by property name and values.
 *
 * @param propertyName The name of the custom property.
 * @param values The values to match for the custom property.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "operations/copilot/add-organizations-to-enterprise-coding-agent-policy/requestBody/properties/custom_properties/items",
    "operations/copilot/remove-organizations-from-enterprise-coding-agent-policy/requestBody/properties/custom_properties/items"
})
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodingAgentCustomProperty(String propertyName, List<String> values) {

    public GitHubCodingAgentCustomProperty {
        requireNonNull(propertyName, "propertyName");
        values = List.copyOf(requireNonNull(values, "values"));
    }
}
