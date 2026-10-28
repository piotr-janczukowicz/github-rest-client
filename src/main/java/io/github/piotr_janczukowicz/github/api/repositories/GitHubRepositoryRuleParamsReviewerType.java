package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Team type. */
@CatalogSchema("components/schemas/repository-rule-params-reviewer/properties/type")
public enum GitHubRepositoryRuleParamsReviewerType {
    TEAM("Team");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleParamsReviewerType> WIRE_INDEX = EnumUtils.index(
            GitHubRepositoryRuleParamsReviewerType.class, GitHubRepositoryRuleParamsReviewerType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleParamsReviewerType(final String wireValue) {
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
     * Team type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleParamsReviewerType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub repository rule params reviewer type: " + value));
    }
}
