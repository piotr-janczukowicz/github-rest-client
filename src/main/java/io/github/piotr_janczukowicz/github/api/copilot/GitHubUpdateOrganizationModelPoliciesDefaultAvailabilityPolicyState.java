package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Default availability policy state to set. */
@CatalogSchema(
        "operations/copilot/update-organization-model-policies/requestBody/properties/default_availability/properties/policy_state")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityPolicyState {
    /** Enabled. */
    ENABLED("enabled"),
    /** Disabled. */
    DISABLED("disabled");

    private static final EnumUtils.WireIndex<GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityPolicyState>
            WIRE_INDEX = EnumUtils.index(
                    GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityPolicyState.class,
                    GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityPolicyState::wireValue);

    private final String wireValue;

    GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityPolicyState(final String wireValue) {
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
    public static GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityPolicyState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub update organization model policies default availability policy state: "
                                + value));
    }
}
