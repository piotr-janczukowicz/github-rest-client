package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** required_deployments type. */
@CatalogSchema("components/schemas/repository-rule-required-deployments/properties/type")
public enum GitHubRepositoryRuleRequiredDeploymentsType {
    REQUIRED_DEPLOYMENTS("required_deployments");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleRequiredDeploymentsType> WIRE_INDEX = EnumUtils.index(
            GitHubRepositoryRuleRequiredDeploymentsType.class, GitHubRepositoryRuleRequiredDeploymentsType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleRequiredDeploymentsType(final String wireValue) {
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
     * required_deployments type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleRequiredDeploymentsType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub repository rule required deployments type: " + value));
    }
}
