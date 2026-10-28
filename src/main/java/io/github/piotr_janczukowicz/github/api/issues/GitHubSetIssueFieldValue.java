package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;

/**
 * Issue field value in a set issue field values request.
 *
 * @param fieldId The ID of the issue field to set
 * @param value The value to set for the field. The type depends on the field's data type: For text fields: provide a
 *     string value. For single_select fields: provide the option name as a string (must match an existing option). For
 *     number fields: provide a numeric value. For date fields: provide an ISO 8601 date string
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/issues/set-issue-field-values/requestBody/properties/issue_field_values/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSetIssueFieldValue(
        long fieldId, @CatalogSimpleXor("string|number") String value) {

    public GitHubSetIssueFieldValue {
        requireNonNull(value, "value");
    }
}
