package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Cwes.
 *
 * @param cweId The Common Weakness Enumeration (CWE) identifier.
 * @param name The name of the CWE.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "components/schemas/global-advisory/properties/cwes/items",
    "components/schemas/repository-advisory/properties/cwes/items"
})
@RecordBuilder
public record GitHubAdvisoryCwe(String cweId, String name) {

    public GitHubAdvisoryCwe {
        requireNonNull(cweId, "cweId");
        requireNonNull(name, "name");
    }
}
