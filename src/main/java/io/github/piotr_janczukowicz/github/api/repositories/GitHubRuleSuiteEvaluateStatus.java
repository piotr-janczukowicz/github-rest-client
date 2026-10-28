package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Rule suite list evaluate status filter. */
@CatalogSchema("components/parameters/evaluate-status/schema")
public enum GitHubRuleSuiteEvaluateStatus {
    ALL("all"),
    ACTIVE("active"),
    EVALUATE("evaluate");

    private static final EnumUtils.WireIndex<GitHubRuleSuiteEvaluateStatus> WIRE_INDEX =
            EnumUtils.index(GitHubRuleSuiteEvaluateStatus.class, GitHubRuleSuiteEvaluateStatus::wireValue);

    private final String wireValue;

    GitHubRuleSuiteEvaluateStatus(final String wireValue) {
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
     * Rule suite list evaluate status filter from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRuleSuiteEvaluateStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub rule suite evaluate status: " + value));
    }
}
