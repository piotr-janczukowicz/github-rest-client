package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Status of a commit comparison. */
@CatalogSchema("components/schemas/commit-comparison/properties/status")
public enum GitHubCommitComparisonStatus {
    DIVERGED("diverged"),
    AHEAD("ahead"),
    BEHIND("behind"),
    IDENTICAL("identical"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCommitComparisonStatus> WIRE_INDEX =
            EnumUtils.index(GitHubCommitComparisonStatus.class, GitHubCommitComparisonStatus::wireValue);

    private final String wireValue;

    GitHubCommitComparisonStatus(final String wireValue) {
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
     * Commit comparison status from a wire value.
     *
     * @param value JSON value
     * @return matching status
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCommitComparisonStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
