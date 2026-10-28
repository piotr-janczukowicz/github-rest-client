package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** committer_email_pattern type. */
@CatalogSchema("components/schemas/repository-rule-committer-email-pattern/properties/type")
public enum GitHubRepositoryRuleCommitterEmailPatternType {
    COMMITTER_EMAIL_PATTERN("committer_email_pattern");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleCommitterEmailPatternType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubRepositoryRuleCommitterEmailPatternType.class,
                    GitHubRepositoryRuleCommitterEmailPatternType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleCommitterEmailPatternType(final String wireValue) {
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
     * committer_email_pattern type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleCommitterEmailPatternType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub repository rule committer email pattern type: " + value));
    }
}
