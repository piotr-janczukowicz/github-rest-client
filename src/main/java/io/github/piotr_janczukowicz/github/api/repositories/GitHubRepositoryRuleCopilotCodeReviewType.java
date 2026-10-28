package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** copilot_code_review type. */
@CatalogSchema("components/schemas/repository-rule-copilot-code-review/properties/type")
public enum GitHubRepositoryRuleCopilotCodeReviewType {
    COPILOT_CODE_REVIEW("copilot_code_review");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleCopilotCodeReviewType> WIRE_INDEX = EnumUtils.index(
            GitHubRepositoryRuleCopilotCodeReviewType.class, GitHubRepositoryRuleCopilotCodeReviewType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleCopilotCodeReviewType(final String wireValue) {
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
     * copilot_code_review type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleCopilotCodeReviewType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub repository rule copilot code review type: " + value));
    }
}
