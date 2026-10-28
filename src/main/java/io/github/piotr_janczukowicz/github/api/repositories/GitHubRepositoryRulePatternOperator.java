package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pattern-matching operator for repository rule parameters. */
@CatalogSchema({
    "components/schemas/repository-rule-branch-name-pattern/properties/parameters/properties/operator",
    "components/schemas/repository-rule-commit-author-email-pattern/properties/parameters/properties/operator",
    "components/schemas/repository-rule-commit-message-pattern/properties/parameters/properties/operator",
    "components/schemas/repository-rule-committer-email-pattern/properties/parameters/properties/operator",
    "components/schemas/repository-rule-tag-name-pattern/properties/parameters/properties/operator"
})
public enum GitHubRepositoryRulePatternOperator {
    STARTS_WITH("starts_with"),
    ENDS_WITH("ends_with"),
    CONTAINS("contains"),
    REGEX("regex");

    private static final EnumUtils.WireIndex<GitHubRepositoryRulePatternOperator> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryRulePatternOperator.class, GitHubRepositoryRulePatternOperator::wireValue);

    private final String wireValue;

    GitHubRepositoryRulePatternOperator(final String wireValue) {
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
     * Pattern-matching operator for repository rule parameters from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRulePatternOperator fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub repository rule pattern operator: " + value));
    }
}
