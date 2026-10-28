package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The enforcement level of the ruleset. `evaluate` allows admins to test rules before enforcing them. Admins can view
 * insights on the Rule Insights page (`evaluate` is only available with GitHub Enterprise).
 */
@CatalogSchema({
    "components/schemas/repository-rule-enforcement",
    "components/schemas/repository-ruleset/properties/enforcement"
})
public enum GitHubRepositoryRuleEnforcement {
    DISABLED("disabled"),
    ACTIVE("active"),
    EVALUATE("evaluate");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleEnforcement> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryRuleEnforcement.class, GitHubRepositoryRuleEnforcement::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleEnforcement(final String wireValue) {
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
     * Ruleset enforcement level from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleEnforcement fromWire(final String value) {
        requireNonNull(value, "value");
        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown repository-rule-enforcement: " + value));
    }
}
