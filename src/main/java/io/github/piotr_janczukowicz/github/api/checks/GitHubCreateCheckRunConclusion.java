package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The final conclusion of the check run when creating it. */
@CatalogSchema("operations/checks/create/requestBody/properties/conclusion")
public enum GitHubCreateCheckRunConclusion {
    ACTION_REQUIRED("action_required"),
    CANCELLED("cancelled"),
    FAILURE("failure"),
    NEUTRAL("neutral"),
    SUCCESS("success"),
    SKIPPED("skipped"),
    STALE("stale"),
    TIMED_OUT("timed_out");

    private static final EnumUtils.WireIndex<GitHubCreateCheckRunConclusion> WIRE_INDEX =
            EnumUtils.index(GitHubCreateCheckRunConclusion.class, GitHubCreateCheckRunConclusion::wireValue);

    private final String wireValue;

    GitHubCreateCheckRunConclusion(final String wireValue) {
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
     * Resolves create check run conclusion from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCreateCheckRunConclusion
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known create check run conclusion
     */
    @JsonCreator
    public static GitHubCreateCheckRunConclusion fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub create check run conclusion: " + value));
    }
}
