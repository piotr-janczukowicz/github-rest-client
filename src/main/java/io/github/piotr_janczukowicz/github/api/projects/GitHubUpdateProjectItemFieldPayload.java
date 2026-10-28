package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;

/**
 * Field update in an update-project-item request.
 *
 * @param id Field id
 * @param value New value or JSON null to clear
 */
@RecordBuilder
@CatalogSchema({
    "operations/projects/update-item-for-org/requestBody/properties/fields/items",
    "operations/projects/update-item-for-user/requestBody/properties/fields/items"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubUpdateProjectItemFieldPayload(
        long id, @CatalogSimpleXor("string|number") OptionalNull<String> value) {

    public GitHubUpdateProjectItemFieldPayload {
        requireNonNull(value, "value");
    }
}
