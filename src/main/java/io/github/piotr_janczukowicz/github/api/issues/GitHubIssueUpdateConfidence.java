package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Confidence level for issue update suggestion metadata. */
@CatalogSchema({
    "operations/issues/update/requestBody/properties/issue_field_values/items/properties/confidence",
    "operations/issues/add-assignees/requestBody/properties/assignees/items/oneOf/1/properties/confidence",
    "operations/issues/add-labels/requestBody/oneOf/0/properties/labels/items/oneOf/1/properties/confidence",
    "operations/issues/add-labels/requestBody/oneOf/2/items/properties/confidence",
    "operations/issues/update/200/properties/suggestions/properties/type/items/properties/confidence",
    "operations/issues/update/200/properties/suggestions/properties/issue_field_values/items/properties/confidence",
    "operations/issues/update/200/properties/suggestions/properties/labels/items/properties/confidence",
    "operations/issues/update/200/properties/suggestions/properties/assignees/items/properties/confidence",
    "operations/issues/update/200/properties/suggestions/properties/state/items/properties/confidence"
})
public enum GitHubIssueUpdateConfidence {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");

    private static final EnumUtils.WireIndex<GitHubIssueUpdateConfidence> WIRE_INDEX =
            EnumUtils.index(GitHubIssueUpdateConfidence.class, GitHubIssueUpdateConfidence::wireValue);

    private final String wireValue;

    GitHubIssueUpdateConfidence(final String wireValue) {
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
     * Confidence from a wire value.
     *
     * @param value JSON value
     * @return matching confidence
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known confidence
     */
    @JsonCreator
    public static GitHubIssueUpdateConfidence fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub issue update confidence: " + value));
    }
}
