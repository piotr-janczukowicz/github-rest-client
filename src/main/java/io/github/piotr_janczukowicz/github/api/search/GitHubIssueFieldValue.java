package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.CatalogSimpleXor;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Issue Field Value. A value assigned to an issue field.
 *
 * @param issueFieldId Unique identifier for the issue field.
 * @param issueFieldName The human-readable name of the issue field.
 * @param nodeId Node id
 * @param dataType The data type of the issue field
 * @param value The value of the issue field
 * @param singleSelectOption Details about the selected option (only present for single_select fields)
 * @param multiSelectOptions Details about the selected options
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/issue-field-value")
public record GitHubIssueFieldValue(
        long issueFieldId,
        @Nullable String issueFieldName,
        String nodeId,
        GitHubIssueFieldDataType dataType,

        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable @CatalogSimpleXor("string|number|integer")
        String value,

        @Nullable SelectOption singleSelectOption,
        @Nullable List<SelectOption> multiSelectOptions) {

    public GitHubIssueFieldValue {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(dataType, "dataType");
    }

    /**
     * A select option on an issue field value.
     *
     * @param id Unique identifier for the option.
     * @param name The name of the option
     * @param color The color of the option
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    @CatalogSchema("components/schemas/issue-field-value/properties/single_select_option")
    public record SelectOption(long id, String name, String color) {
        public SelectOption {
            requireNonNull(name, "name");
            requireNonNull(color, "color");
        }
    }
}
