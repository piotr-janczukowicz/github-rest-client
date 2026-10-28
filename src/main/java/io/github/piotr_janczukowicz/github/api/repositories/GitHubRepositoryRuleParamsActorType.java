package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Actor type for repository rule dismissal restriction parameters. */
@CatalogSchema("components/schemas/repository-rule-params-actor/properties/type")
public enum GitHubRepositoryRuleParamsActorType {
    USER("User"),
    TEAM("Team"),
    INTEGRATION_INSTALLATION("IntegrationInstallation"),
    REPOSITORY_ROLE("RepositoryRole");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleParamsActorType> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryRuleParamsActorType.class, GitHubRepositoryRuleParamsActorType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleParamsActorType(final String wireValue) {
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
     * Actor type for repository rule dismissal restriction parameters from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleParamsActorType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub repository rule params actor type: " + value));
    }
}
