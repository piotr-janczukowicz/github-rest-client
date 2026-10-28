package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Policy state of the model. */
@CatalogSchema(
        "operations/enterprise-admin/update-enterprise-team-model-policies/200/properties/models/items/properties/policy_state")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState {
    /** Enabled. */
    ENABLED("enabled"),
    /** Disabled. */
    DISABLED("disabled"),
    /** Optional. */
    OPTIONAL("optional"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState> WIRE_INDEX =
            EnumUtils.index(
                    GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState.class,
                    GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState::wireValue);

    private final String wireValue;

    GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState(final String wireValue) {
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
    public static GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
