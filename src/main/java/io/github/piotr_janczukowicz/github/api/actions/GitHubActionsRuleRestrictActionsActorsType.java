package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** restrict_actions_actors rule type. */
@CatalogSchema("components/schemas/actions-rule-restrict-actions-actors/properties/type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubActionsRuleRestrictActionsActorsType {
    RESTRICT_ACTIONS_ACTORS("restrict_actions_actors");

    private static final EnumUtils.WireIndex<GitHubActionsRuleRestrictActionsActorsType> WIRE_INDEX = EnumUtils.index(
            GitHubActionsRuleRestrictActionsActorsType.class, GitHubActionsRuleRestrictActionsActorsType::wireValue);

    private final String wireValue;

    GitHubActionsRuleRestrictActionsActorsType(final String wireValue) {
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
     * restrict_actions_actors type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubActionsRuleRestrictActionsActorsType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHubActionsRuleRestrictActionsActorsType value: " + value));
    }
}
