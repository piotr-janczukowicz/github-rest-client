package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Repository activity time period filter. */
public enum GitHubRepositoryActivityTimePeriod {
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    QUARTER("quarter"),
    YEAR("year");

    private static final EnumUtils.WireIndex<GitHubRepositoryActivityTimePeriod> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryActivityTimePeriod.class, GitHubRepositoryActivityTimePeriod::wireValue);

    private final String wireValue;

    GitHubRepositoryActivityTimePeriod(final String wireValue) {
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
     * Repository activity time period filter from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryActivityTimePeriod fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub repository activity time period: " + value));
    }
}
