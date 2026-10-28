package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** restrict_action_events rule type. */
@CatalogSchema("components/schemas/actions-rule-restrict-action-events/properties/type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubActionsRuleRestrictActionEventsType {
    RESTRICT_ACTION_EVENTS("restrict_action_events");

    private static final EnumUtils.WireIndex<GitHubActionsRuleRestrictActionEventsType> WIRE_INDEX = EnumUtils.index(
            GitHubActionsRuleRestrictActionEventsType.class, GitHubActionsRuleRestrictActionEventsType::wireValue);

    private final String wireValue;

    GitHubActionsRuleRestrictActionEventsType(final String wireValue) {
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
     * restrict_action_events type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubActionsRuleRestrictActionEventsType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHubActionsRuleRestrictActionEventsType value: " + value));
    }
}
