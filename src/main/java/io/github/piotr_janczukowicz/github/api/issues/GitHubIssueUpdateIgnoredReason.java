package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Why a pending issue update suggestion was ignored. */
@CatalogSchema({
    "operations/issues/update/200/properties/suggestions/properties/type/items/properties/ignored_reason",
    "operations/issues/update/200/properties/suggestions/properties/issue_field_values/items/properties/ignored_reason",
    "operations/issues/update/200/properties/suggestions/properties/labels/items/properties/ignored_reason",
    "operations/issues/update/200/properties/suggestions/properties/assignees/items/properties/ignored_reason",
    "operations/issues/update/200/properties/suggestions/properties/state/items/properties/ignored_reason"
})
public enum GitHubIssueUpdateIgnoredReason {
    ALREADY_APPLIED("already_applied"),
    ISSUE_ALREADY_CLOSED("issue_already_closed"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubIssueUpdateIgnoredReason> WIRE_INDEX =
            EnumUtils.index(GitHubIssueUpdateIgnoredReason.class, GitHubIssueUpdateIgnoredReason::wireValue);

    private final String wireValue;

    GitHubIssueUpdateIgnoredReason(final String wireValue) {
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
     * Ignored reason from a wire value.
     *
     * @param value JSON value
     * @return matching reason
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubIssueUpdateIgnoredReason fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
