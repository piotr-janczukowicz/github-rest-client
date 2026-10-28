package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Diff entry status. */
@CatalogSchema("components/schemas/diff-entry/properties/status")
public enum GitHubDiffEntryStatus {
    ADDED("added"),
    REMOVED("removed"),
    MODIFIED("modified"),
    RENAMED("renamed"),
    COPIED("copied"),
    CHANGED("changed"),
    UNCHANGED("unchanged"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubDiffEntryStatus> WIRE_INDEX =
            EnumUtils.index(GitHubDiffEntryStatus.class, GitHubDiffEntryStatus::wireValue);

    private final String wireValue;

    GitHubDiffEntryStatus(final String wireValue) {
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
     * Diff entry status from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubDiffEntryStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
