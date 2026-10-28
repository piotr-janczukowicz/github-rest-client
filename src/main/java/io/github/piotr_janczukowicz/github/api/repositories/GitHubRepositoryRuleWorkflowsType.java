package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** workflows type. */
@CatalogSchema("components/schemas/repository-rule-workflows/properties/type")
public enum GitHubRepositoryRuleWorkflowsType {
    WORKFLOWS("workflows");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleWorkflowsType> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryRuleWorkflowsType.class, GitHubRepositoryRuleWorkflowsType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleWorkflowsType(final String wireValue) {
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
     * workflows type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleWorkflowsType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub repository rule workflows type: " + value));
    }
}
