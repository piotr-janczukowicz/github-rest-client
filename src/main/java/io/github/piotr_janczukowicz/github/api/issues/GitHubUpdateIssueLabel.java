package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Label in an update issue request ({@code oneOf} string vs object). */
@CatalogSchema("operations/issues/update/requestBody/properties/labels/items")
public record GitHubUpdateIssueLabel(
        @Nullable String labelName, @Nullable Details labelDetails) {

    public GitHubUpdateIssueLabel {
        final boolean hasName = labelName != null;
        final boolean hasDetails = labelDetails != null;

        if (hasName == hasDetails) {
            throw new IllegalArgumentException("exactly one of labelName or labelDetails is required");
        }
    }

    /**
     * Label by name.
     *
     * @param name label name
     * @return label reference
     */
    public static GitHubUpdateIssueLabel ofName(final String name) {
        requireNonNull(name, "name");
        return new GitHubUpdateIssueLabel(name, null);
    }

    /**
     * Label object with optional metadata.
     *
     * @param details label details
     * @return label reference
     */
    public static GitHubUpdateIssueLabel ofDetails(final Details details) {
        return new GitHubUpdateIssueLabel(null, requireNonNull(details, "details"));
    }

    /**
     * Label object with optional metadata.
     *
     * @param id label id
     * @param name label name
     * @param description label description
     * @param color label color
     * @param rationale optional reasoning for selecting this label
     * @param suggest store as pending suggestion when {@code true}
     * @param confidence confidence level for this label choice
     */
    @RecordBuilder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @CatalogSchema("operations/issues/update/requestBody/properties/labels/items/oneOf/1")
    public record Details(
            @Nullable Long id,
            @Nullable String name,
            @Nullable String description,
            @Nullable String color,
            @Nullable String rationale,
            @Nullable Boolean suggest,
            @Nullable GitHubIssueUpdateConfidence confidence) {}
}
