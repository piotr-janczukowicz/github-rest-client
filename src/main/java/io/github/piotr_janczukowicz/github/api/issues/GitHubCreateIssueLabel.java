package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Label in a create issue request ({@code oneOf} string vs object). */
@CatalogSchema("operations/issues/create/requestBody/properties/labels/items")
public record GitHubCreateIssueLabel(
        @Nullable String labelName, @Nullable Details labelDetails) {

    public GitHubCreateIssueLabel {
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
    public static GitHubCreateIssueLabel ofName(final String name) {
        requireNonNull(name, "name");
        return new GitHubCreateIssueLabel(name, null);
    }

    /**
     * Label object with optional metadata.
     *
     * @param details label details
     * @return label reference
     */
    public static GitHubCreateIssueLabel ofDetails(final Details details) {
        return new GitHubCreateIssueLabel(null, requireNonNull(details, "details"));
    }

    /**
     * Label object with optional metadata.
     *
     * @param id label id
     * @param name label name
     * @param description label description
     * @param color label color
     */
    @RecordBuilder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @CatalogSchema("operations/issues/create/requestBody/properties/labels/items/oneOf/1")
    public record Details(
            @Nullable Long id,
            @Nullable String name,
            @Nullable String description,
            @Nullable String color) {}
}
