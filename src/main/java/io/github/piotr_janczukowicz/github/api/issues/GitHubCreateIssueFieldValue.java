package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Issue field value in a create issue request.
 *
 * @param fieldId issue field id
 * @param value field value
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/issues/create/requestBody/properties/issue_field_values/items",
    "operations/issues/add-issue-field-values/requestBody/properties/issue_field_values/items"
})
public record GitHubCreateIssueFieldValue(long fieldId, GitHubIssueFieldValueWire value) {

    public GitHubCreateIssueFieldValue {
        requireNonNull(value, "value");
    }
}
