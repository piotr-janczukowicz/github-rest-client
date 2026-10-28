package io.github.piotr_janczukowicz.github.api;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sort direction query parameter. */
@CatalogSchema("components/parameters/direction/schema")
public enum GitHubDirection {
    /** Ascending sort. */
    ASC("asc"),
    /** Descending sort. */
    DESC("desc");

    private static final EnumUtils.WireIndex<GitHubDirection> WIRE_INDEX =
            EnumUtils.index(GitHubDirection.class, GitHubDirection::wireValue);

    private final String wireValue;

    GitHubDirection(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * JSON {@code wireValue} of this direction.
     *
     * @return {@code asc} or {@code desc}
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Parses GitHub's {@code direction} query value.
     *
     * @param value wire value
     * @return direction
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known direction
     */
    @JsonCreator
    public static GitHubDirection fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub direction: " + value));
    }
}
