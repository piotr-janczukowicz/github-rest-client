package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Identifiers.
 *
 * @param type The type of identifier.
 * @param value The identifier value.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "components/schemas/global-advisory/properties/identifiers/items",
    "components/schemas/repository-advisory/properties/identifiers/items"
})
@RecordBuilder
public record GitHubAdvisoryIdentifier(GitHubAdvisoryIdentifierType type, String value) {

    public GitHubAdvisoryIdentifier {
        requireNonNull(type, "type");
        requireNonNull(value, "value");
    }
}
