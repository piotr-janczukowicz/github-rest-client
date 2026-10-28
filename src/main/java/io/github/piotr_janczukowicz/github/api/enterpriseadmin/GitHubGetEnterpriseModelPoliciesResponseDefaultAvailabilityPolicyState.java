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
        "operations/enterprise-admin/get-enterprise-model-policies/200/properties/default_availability/properties/policy_state")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState {
    /** Enabled. */
    ENABLED("enabled"),
    /** Disabled. */
    DISABLED("disabled"),
    /** Delegate to organizations. */
    DELEGATE_TO_ORGANIZATIONS("delegate_to_organizations"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState>
            WIRE_INDEX = EnumUtils.index(
                    GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.class,
                    GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState::wireValue);

    private final String wireValue;

    GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState(final String wireValue) {
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
    public static GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
