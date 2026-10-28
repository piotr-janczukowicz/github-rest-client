package io.github.piotr_janczukowicz.github.api.metrics;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The time frame to display results for. */
@CatalogSchema("components/parameters/per/schema")
public enum GitHubTrafficPer {
    DAY("day"),
    WEEK("week");

    private static final EnumUtils.WireIndex<GitHubTrafficPer> WIRE_INDEX =
            EnumUtils.index(GitHubTrafficPer.class, GitHubTrafficPer::wireValue);

    private final String wireValue;

    GitHubTrafficPer(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Time frame from a wire value.
     *
     * @param value wire value
     * @return matching time frame
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known time frame
     */
    @JsonCreator
    public static GitHubTrafficPer fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub traffic per value: " + value));
    }
}
