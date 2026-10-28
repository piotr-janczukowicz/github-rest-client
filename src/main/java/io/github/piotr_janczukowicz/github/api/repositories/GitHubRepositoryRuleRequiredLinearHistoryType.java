package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** required_linear_history type. */
@CatalogSchema("components/schemas/repository-rule-required-linear-history/properties/type")
public enum GitHubRepositoryRuleRequiredLinearHistoryType {
    REQUIRED_LINEAR_HISTORY("required_linear_history");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleRequiredLinearHistoryType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubRepositoryRuleRequiredLinearHistoryType.class,
                    GitHubRepositoryRuleRequiredLinearHistoryType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleRequiredLinearHistoryType(final String wireValue) {
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
     * required_linear_history type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleRequiredLinearHistoryType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub repository rule required linear history type: " + value));
    }
}
