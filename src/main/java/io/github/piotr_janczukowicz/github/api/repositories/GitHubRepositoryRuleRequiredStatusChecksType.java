package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** required_status_checks type. */
@CatalogSchema("components/schemas/repository-rule-required-status-checks/properties/type")
public enum GitHubRepositoryRuleRequiredStatusChecksType {
    REQUIRED_STATUS_CHECKS("required_status_checks");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleRequiredStatusChecksType> WIRE_INDEX = EnumUtils.index(
            GitHubRepositoryRuleRequiredStatusChecksType.class,
            GitHubRepositoryRuleRequiredStatusChecksType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleRequiredStatusChecksType(final String wireValue) {
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
     * required_status_checks type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleRequiredStatusChecksType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub repository rule required status checks type: " + value));
    }
}
