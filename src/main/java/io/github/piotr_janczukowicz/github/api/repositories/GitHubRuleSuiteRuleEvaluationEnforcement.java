package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Rule suite rule evaluation enforcement. */
@CatalogSchema("components/schemas/rule-suite/properties/rule_evaluations/items/properties/enforcement")
public enum GitHubRuleSuiteRuleEvaluationEnforcement {
    ACTIVE("active"),
    EVALUATE("evaluate"),
    DELETED_RULESET("deleted ruleset"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubRuleSuiteRuleEvaluationEnforcement> WIRE_INDEX = EnumUtils.index(
            GitHubRuleSuiteRuleEvaluationEnforcement.class, GitHubRuleSuiteRuleEvaluationEnforcement::wireValue);

    private final String wireValue;

    GitHubRuleSuiteRuleEvaluationEnforcement(final String wireValue) {
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
     * Rule suite rule evaluation enforcement from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubRuleSuiteRuleEvaluationEnforcement fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
