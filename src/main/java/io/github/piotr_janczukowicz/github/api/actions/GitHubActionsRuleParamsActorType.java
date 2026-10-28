package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The type of the actor. */
@CatalogSchema("components/schemas/actions-rule-params-actor/properties/type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubActionsRuleParamsActorType {
    USER("User"),
    BOT("Bot"),
    TEAM("Team"),
    BUSINESS_TEAM("BusinessTeam"),
    ENTERPRISE_TEAM("EnterpriseTeam"),
    INTEGRATION_INSTALLATION("IntegrationInstallation"),
    APP("App"),
    REPOSITORY_ROLE("RepositoryRole");

    private static final EnumUtils.WireIndex<GitHubActionsRuleParamsActorType> WIRE_INDEX =
            EnumUtils.index(GitHubActionsRuleParamsActorType.class, GitHubActionsRuleParamsActorType::wireValue);

    private final String wireValue;

    GitHubActionsRuleParamsActorType(final String wireValue) {
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
     * Actor type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubActionsRuleParamsActorType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHubActionsRuleParamsActorType value: " + value));
    }
}
