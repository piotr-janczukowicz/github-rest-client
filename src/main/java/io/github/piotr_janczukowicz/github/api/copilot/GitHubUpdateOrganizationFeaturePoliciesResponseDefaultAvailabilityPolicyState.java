package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The effective default availability. The enterprise default is used when the enterprise has set one. Otherwise, the
 * organization's own default is used.
 */
@CatalogSchema(
        "operations/copilot/update-organization-feature-policies/200/properties/default_availability/properties/policy_state")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubUpdateOrganizationFeaturePoliciesResponseDefaultAvailabilityPolicyState {
    ENABLED("enabled"),
    DISABLED("disabled"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<
                    GitHubUpdateOrganizationFeaturePoliciesResponseDefaultAvailabilityPolicyState>
            WIRE_INDEX = EnumUtils.index(
                    GitHubUpdateOrganizationFeaturePoliciesResponseDefaultAvailabilityPolicyState.class,
                    GitHubUpdateOrganizationFeaturePoliciesResponseDefaultAvailabilityPolicyState::wireValue);

    private final String wireValue;

    GitHubUpdateOrganizationFeaturePoliciesResponseDefaultAvailabilityPolicyState(final String wireValue) {
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
     * @return matching constant, or {@link #UNRECOGNIZED} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubUpdateOrganizationFeaturePoliciesResponseDefaultAvailabilityPolicyState fromWire(
            final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
