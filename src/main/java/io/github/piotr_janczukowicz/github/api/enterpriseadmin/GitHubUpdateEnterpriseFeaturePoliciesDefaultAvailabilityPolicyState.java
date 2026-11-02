package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The enterprise default policy applied to newly released Copilot features and clients.
 * {@code delegate_to_organizations} is only accepted for enterprises that manage Copilot through organizations.
 */
@CatalogSchema(
        "operations/enterprise-admin/update-enterprise-feature-policies/requestBody/properties/default_availability/properties/policy_state")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubUpdateEnterpriseFeaturePoliciesDefaultAvailabilityPolicyState {
    ENABLED("enabled"),
    DISABLED("disabled"),
    DELEGATE_TO_ORGANIZATIONS("delegate_to_organizations");

    private static final EnumUtils.WireIndex<GitHubUpdateEnterpriseFeaturePoliciesDefaultAvailabilityPolicyState>
            WIRE_INDEX = EnumUtils.index(
                    GitHubUpdateEnterpriseFeaturePoliciesDefaultAvailabilityPolicyState.class,
                    GitHubUpdateEnterpriseFeaturePoliciesDefaultAvailabilityPolicyState::wireValue);

    private final String wireValue;

    GitHubUpdateEnterpriseFeaturePoliciesDefaultAvailabilityPolicyState(final String wireValue) {
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
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubUpdateEnterpriseFeaturePoliciesDefaultAvailabilityPolicyState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub update enterprise feature policies default availability policy state: "
                                + value));
    }
}
