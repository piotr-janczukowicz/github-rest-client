package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Issue type in an update issue request ({@code oneOf} string vs object). */
@CatalogSchema("operations/issues/update/requestBody/properties/type")
public record GitHubUpdateIssueType(
        @Nullable String typeName, @Nullable Details typeDetails) {

    public GitHubUpdateIssueType {
        final boolean hasName = typeName != null;
        final boolean hasDetails = typeDetails != null;

        if (hasName == hasDetails) {
            throw new IllegalArgumentException("exactly one of typeName or typeDetails is required");
        }
    }

    /**
     * Issue type by name.
     *
     * @param name issue type name
     * @return type reference
     */
    public static GitHubUpdateIssueType ofName(final String name) {
        requireNonNull(name, "name");
        return new GitHubUpdateIssueType(name, null);
    }

    /**
     * Issue type object with optional metadata.
     *
     * @param details type details
     * @return type reference
     */
    public static GitHubUpdateIssueType ofDetails(final Details details) {
        return new GitHubUpdateIssueType(null, requireNonNull(details, "details"));
    }

    /**
     * Issue type object with optional metadata.
     *
     * @param value issue type name, or JSON null to remove the current type
     * @param rationale optional reasoning for selecting this type
     * @param suggest store as pending suggestion when {@code true}
     * @param confidence confidence level for this type choice
     */
    @RecordBuilder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @CatalogSchema("operations/issues/update/requestBody/properties/type/oneOf/1")
    public record Details(
            @Nullable OptionalNull<String> value,
            @Nullable String rationale,
            @Nullable Boolean suggest,
            @Nullable GitHubIssueUpdateConfidence confidence) {}
}
