package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Confidence level for an issue suggestion. */
@CatalogSchema({
    "components/schemas/issue-suggestion/properties/confidence",
    "components/schemas/issue-event-intent/properties/confidence"
})
public enum GitHubIssueSuggestionConfidence {
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubIssueSuggestionConfidence> WIRE_INDEX =
            EnumUtils.index(GitHubIssueSuggestionConfidence.class, GitHubIssueSuggestionConfidence::wireValue);

    private final String wireValue;

    GitHubIssueSuggestionConfidence(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Suggestion confidence from a wire value.
     *
     * @param value JSON value
     * @return matching confidence
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubIssueSuggestionConfidence fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
