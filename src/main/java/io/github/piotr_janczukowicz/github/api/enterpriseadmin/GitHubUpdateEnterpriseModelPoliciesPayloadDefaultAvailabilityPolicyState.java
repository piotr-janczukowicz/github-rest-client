package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Policy state of the default availability. */
@CatalogSchema(
        "operations/enterprise-admin/update-enterprise-model-policies/requestBody/properties/default_availability/properties/policy_state")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityPolicyState {
    /** Enabled. */
    ENABLED("enabled"),
    /** Disabled. */
    DISABLED("disabled"),
    /** Delegate to organizations. */
    DELEGATE_TO_ORGANIZATIONS("delegate_to_organizations");

    private static final EnumUtils.WireIndex<GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityPolicyState>
            WIRE_INDEX = EnumUtils.index(
                    GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityPolicyState.class,
                    GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityPolicyState::wireValue);

    private final String wireValue;

    GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityPolicyState(final String wireValue) {
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
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityPolicyState fromWire(
            final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub update enterprise model policies payload default availability policy state: "
                                + value));
    }
}
