package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Label in a set-labels request ({@code oneOf} string vs object). */
@CatalogSchema({
    "operations/issues/set-labels/requestBody/oneOf/2/properties/labels/items",
    "operations/issues/set-labels/requestBody/oneOf/0/properties/labels/items"
})
public record GitHubSetLabelsLabel(
        @Nullable String labelName, @Nullable Details labelDetails) {

    public GitHubSetLabelsLabel {
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
    public static GitHubSetLabelsLabel ofName(final String name) {
        requireNonNull(name, "name");
        return new GitHubSetLabelsLabel(name, null);
    }

    /**
     * Label object with a name.
     *
     * @param details label details
     * @return label reference
     */
    public static GitHubSetLabelsLabel ofDetails(final Details details) {
        return new GitHubSetLabelsLabel(null, requireNonNull(details, "details"));
    }

    /**
     * Label object with required name.
     *
     * @param name Name
     */
    @RecordBuilder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @CatalogSchema("operations/issues/set-labels/requestBody/oneOf/3/items")
    public record Details(String name) {

        public Details {
            requireNonNull(name, "name");
        }
    }
}
