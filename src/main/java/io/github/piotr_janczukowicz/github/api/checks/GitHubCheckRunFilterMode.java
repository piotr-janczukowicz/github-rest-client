package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Filters check runs by their {@code completed_at} timestamp. */
@CatalogSchema({"operations/checks/list-for-suite/parameters/5", "operations/checks/list-for-ref/parameters/5"})
public enum GitHubCheckRunFilterMode {
    LATEST("latest"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubCheckRunFilterMode> WIRE_INDEX =
            EnumUtils.index(GitHubCheckRunFilterMode.class, GitHubCheckRunFilterMode::wireValue);

    private final String wireValue;

    GitHubCheckRunFilterMode(final String wireValue) {
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
     * Resolves check run filter mode from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCheckRunFilterMode
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known check run filter mode
     */
    @JsonCreator
    public static GitHubCheckRunFilterMode fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub check run filter mode: " + value));
    }
}
