package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** branch_name_pattern type. */
@CatalogSchema("components/schemas/repository-rule-branch-name-pattern/properties/type")
public enum GitHubRepositoryRuleBranchNamePatternType {
    BRANCH_NAME_PATTERN("branch_name_pattern");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleBranchNamePatternType> WIRE_INDEX = EnumUtils.index(
            GitHubRepositoryRuleBranchNamePatternType.class, GitHubRepositoryRuleBranchNamePatternType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleBranchNamePatternType(final String wireValue) {
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
     * branch_name_pattern type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleBranchNamePatternType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub repository rule branch name pattern type: " + value));
    }
}
