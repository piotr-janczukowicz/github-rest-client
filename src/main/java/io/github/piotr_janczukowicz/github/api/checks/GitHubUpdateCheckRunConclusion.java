package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The final conclusion of the check run when updating it. */
@CatalogSchema("operations/checks/update/requestBody/properties/conclusion")
public enum GitHubUpdateCheckRunConclusion {
    ACTION_REQUIRED("action_required"),
    CANCELLED("cancelled"),
    FAILURE("failure"),
    NEUTRAL("neutral"),
    SUCCESS("success"),
    SKIPPED("skipped"),
    STALE("stale"),
    TIMED_OUT("timed_out");

    private static final EnumUtils.WireIndex<GitHubUpdateCheckRunConclusion> WIRE_INDEX =
            EnumUtils.index(GitHubUpdateCheckRunConclusion.class, GitHubUpdateCheckRunConclusion::wireValue);

    private final String wireValue;

    GitHubUpdateCheckRunConclusion(final String wireValue) {
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
     * Resolves update check run conclusion from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubUpdateCheckRunConclusion
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known update check run conclusion
     */
    @JsonCreator
    public static GitHubUpdateCheckRunConclusion fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub update check run conclusion: " + value));
    }
}
