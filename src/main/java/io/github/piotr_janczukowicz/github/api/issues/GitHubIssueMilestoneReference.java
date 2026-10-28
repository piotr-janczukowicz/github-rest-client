package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Milestone reference in create or update issue requests ({@code oneOf} string vs integer).
 *
 * @param numericId Milestone number
 * @param stringId Milestone title
 */
@CatalogSchema("operations/issues/create/requestBody/properties/milestone")
public record GitHubIssueMilestoneReference(
        @Nullable Long numericId, @Nullable String stringId) {

    public GitHubIssueMilestoneReference {
        final boolean hasNumeric = numericId != null;
        final boolean hasString = stringId != null;

        if (hasNumeric == hasString) {
            throw new IllegalArgumentException("exactly one of numericId or stringId is required");
        }
    }

    /**
     * Milestone reference by number.
     *
     * @param number milestone number
     * @return milestone reference
     */
    public static GitHubIssueMilestoneReference ofNumber(final long number) {
        return new GitHubIssueMilestoneReference(number, null);
    }

    /**
     * Milestone reference by title.
     *
     * @param title milestone title
     * @return milestone reference
     * @throws NullPointerException if {@code title} is {@code null}
     */
    public static GitHubIssueMilestoneReference ofTitle(final String title) {
        requireNonNull(title, "title");
        return new GitHubIssueMilestoneReference(null, title);
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return numeric or string wire value
     */
    @JsonValue
    public Object jsonValue() {
        if (numericId != null) {
            return numericId;
        }
        return requireNonNull(stringId, "stringId");
    }

    /**
     * Milestone reference from a JSON number.
     *
     * @param value JSON number
     * @return milestone reference
     */
    @JsonCreator
    public static GitHubIssueMilestoneReference fromJson(final Number value) {
        return ofNumber(requireNonNull(value, "value").longValue());
    }

    /**
     * Milestone reference from a JSON string.
     *
     * @param value JSON string
     * @return milestone reference
     */
    @JsonCreator
    public static GitHubIssueMilestoneReference fromJson(final String value) {
        return ofTitle(value);
    }
}
