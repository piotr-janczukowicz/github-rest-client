package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Explicit policy state to set for a model. */
@CatalogSchema(
        "operations/copilot/update-organization-model-policies/requestBody/properties/models/items/properties/policy_state")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubUpdateOrganizationModelPoliciesModelPolicyState {
    /** Enabled. */
    ENABLED("enabled"),
    /** Disabled. */
    DISABLED("disabled");

    private static final EnumUtils.WireIndex<GitHubUpdateOrganizationModelPoliciesModelPolicyState> WIRE_INDEX =
            EnumUtils.index(
                    GitHubUpdateOrganizationModelPoliciesModelPolicyState.class,
                    GitHubUpdateOrganizationModelPoliciesModelPolicyState::wireValue);

    private final String wireValue;

    GitHubUpdateOrganizationModelPoliciesModelPolicyState(final String wireValue) {
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
    public static GitHubUpdateOrganizationModelPoliciesModelPolicyState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub update organization model policies model policy state: " + value));
    }
}
