package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Policy state of a model. */
@CatalogSchema("operations/copilot/get-organization-model-policies/200/properties/models/items/properties/policy_state")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubGetOrganizationModelPoliciesResponseModelPolicyState {
    /** Enabled. */
    ENABLED("enabled"),
    /** Disabled. */
    DISABLED("disabled"),
    /** Delegate to default. */
    DELEGATE_TO_DEFAULT("delegate_to_default"),
    /** Unconfigured. */
    UNCONFIGURED("unconfigured"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubGetOrganizationModelPoliciesResponseModelPolicyState> WIRE_INDEX =
            EnumUtils.index(
                    GitHubGetOrganizationModelPoliciesResponseModelPolicyState.class,
                    GitHubGetOrganizationModelPoliciesResponseModelPolicyState::wireValue);

    private final String wireValue;

    GitHubGetOrganizationModelPoliciesResponseModelPolicyState(final String wireValue) {
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
     * Value from a wire string.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubGetOrganizationModelPoliciesResponseModelPolicyState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
