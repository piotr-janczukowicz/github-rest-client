package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The separately read enterprise-layer policy for this model, used to determine whether the Copilot Fusion layer can
 * edit its policy.
 */
@CatalogSchema(
        "operations/enterprise-admin/get-enterprise-fusion-model-policies/200/properties/models/items/properties/enterprise_policy_state")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubGetEnterpriseFusionModelPoliciesResponseModelEnterprisePolicyState {
    ENABLED("enabled"),
    DISABLED("disabled"),
    DELEGATE_TO_TEAMS_AND_APPS("delegate_to_teams_and_apps"),
    DELEGATE_TO_ORGANIZATIONS("delegate_to_organizations"),
    DELEGATE_TO_DEFAULT("delegate_to_default"),
    UNCONFIGURED("unconfigured"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubGetEnterpriseFusionModelPoliciesResponseModelEnterprisePolicyState>
            WIRE_INDEX = EnumUtils.index(
                    GitHubGetEnterpriseFusionModelPoliciesResponseModelEnterprisePolicyState.class,
                    GitHubGetEnterpriseFusionModelPoliciesResponseModelEnterprisePolicyState::wireValue);

    private final String wireValue;

    GitHubGetEnterpriseFusionModelPoliciesResponseModelEnterprisePolicyState(final String wireValue) {
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
    public static GitHubGetEnterpriseFusionModelPoliciesResponseModelEnterprisePolicyState fromWire(
            final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
