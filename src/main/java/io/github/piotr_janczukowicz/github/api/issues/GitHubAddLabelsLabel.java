package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireExactlyOne;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Label in an add-labels request ({@code oneOf} string vs object). */
@CatalogSchema("operations/issues/add-labels/requestBody/oneOf/0/properties/labels/items")
public record GitHubAddLabelsLabel(
        @Nullable String labelName, @Nullable Details labelDetails) {

    public GitHubAddLabelsLabel {
        requireExactlyOne(labelName, labelDetails, "labelName or labelDetails");
    }

    /**
     * Label by name.
     *
     * @param name label name
     * @return label reference
     */
    public static GitHubAddLabelsLabel ofName(final String name) {
        requireNonNull(name, "name");
        return new GitHubAddLabelsLabel(name, null);
    }

    /**
     * Label object with optional metadata.
     *
     * @param details label details
     * @return label reference
     */
    public static GitHubAddLabelsLabel ofDetails(final Details details) {
        return new GitHubAddLabelsLabel(null, requireNonNull(details, "details"));
    }

    /**
     * Label object with required name.
     *
     * @param name The name of the label to add.
     * @param rationale Optional reasoning for adding this label.
     * @param suggest If {@code true}, the label is stored as a pending suggestion for human review rather than applied
     *     directly.
     * @param confidence The confidence level for this label choice.
     */
    @RecordBuilder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @CatalogSchema({
        "operations/issues/add-labels/requestBody/oneOf/0/properties/labels/items/oneOf/1",
        "operations/issues/add-labels/requestBody/oneOf/2/items"
    })
    public record Details(
            String name,
            @Nullable String rationale,
            @Nullable Boolean suggest,
            @Nullable GitHubIssueUpdateConfidence confidence) {

        public Details {
            requireNonNull(name, "name");
        }
    }
}
