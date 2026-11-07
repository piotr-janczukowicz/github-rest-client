package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * When editable, the Copilot Fusion layer's configured value, or {@code enabled} if the Copilot Fusion layer has not
 * configured this model, because Copilot Fusion only restricts models it explicitly disables. When locked, the
 * enterprise's explicit value or its applied default availability for an eligible model. Returns {@code enabled} when
 * locked to an applied default availability that is delegated to organizations. Returns {@code disabled} when locked
 * without an explicit enterprise value and the model is excluded from default availability.
 */
@CatalogSchema(
        "operations/enterprise-admin/update-enterprise-fusion-model-policies/200/properties/models/items/properties/policy_state")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubUpdateEnterpriseFusionModelPoliciesResponseModelPolicyState {
    ENABLED("enabled"),
    DISABLED("disabled"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubUpdateEnterpriseFusionModelPoliciesResponseModelPolicyState>
            WIRE_INDEX = EnumUtils.index(
                    GitHubUpdateEnterpriseFusionModelPoliciesResponseModelPolicyState.class,
                    GitHubUpdateEnterpriseFusionModelPoliciesResponseModelPolicyState::wireValue);

    private final String wireValue;

    GitHubUpdateEnterpriseFusionModelPoliciesResponseModelPolicyState(final String wireValue) {
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
    public static GitHubUpdateEnterpriseFusionModelPoliciesResponseModelPolicyState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
