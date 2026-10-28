package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** required_signatures type. */
@CatalogSchema("components/schemas/repository-rule-required-signatures/properties/type")
public enum GitHubRepositoryRuleRequiredSignaturesType {
    REQUIRED_SIGNATURES("required_signatures");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleRequiredSignaturesType> WIRE_INDEX = EnumUtils.index(
            GitHubRepositoryRuleRequiredSignaturesType.class, GitHubRepositoryRuleRequiredSignaturesType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleRequiredSignaturesType(final String wireValue) {
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
     * required_signatures type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleRequiredSignaturesType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub repository rule required signatures type: " + value));
    }
}
