package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** pull_request type. */
@CatalogSchema("components/schemas/repository-rule-pull-request/properties/type")
public enum GitHubRepositoryRulePullRequestType {
    PULL_REQUEST("pull_request");

    private static final EnumUtils.WireIndex<GitHubRepositoryRulePullRequestType> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryRulePullRequestType.class, GitHubRepositoryRulePullRequestType::wireValue);

    private final String wireValue;

    GitHubRepositoryRulePullRequestType(final String wireValue) {
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
     * pull_request type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRulePullRequestType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub repository rule pull request type: " + value));
    }
}
