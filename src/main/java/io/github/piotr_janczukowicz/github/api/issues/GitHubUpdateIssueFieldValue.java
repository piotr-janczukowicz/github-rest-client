package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Issue field value in an update issue request.
 *
 * @param fieldId issue field id
 * @param value field value
 * @param rationale optional reasoning for setting this field value
 * @param suggest store as pending suggestion when {@code true}
 * @param confidence confidence level for this field value choice
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/issues/update/requestBody/properties/issue_field_values/items")
public record GitHubUpdateIssueFieldValue(
        long fieldId,
        GitHubIssueFieldValueWire value,
        @Nullable String rationale,
        @Nullable Boolean suggest,
        @Nullable GitHubIssueUpdateConfidence confidence) {

    public GitHubUpdateIssueFieldValue {
        requireNonNull(value, "value");
    }
}
