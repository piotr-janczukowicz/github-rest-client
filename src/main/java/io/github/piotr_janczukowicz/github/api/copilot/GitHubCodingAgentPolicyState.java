package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Coding agent policy state for an enterprise. */
@CatalogSchema("operations/copilot/set-enterprise-coding-agent-policy/requestBody/properties/policy_state")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodingAgentPolicyState {
    ENABLED_FOR_ALL_ORGS("enabled_for_all_orgs"),
    DISABLED_FOR_ALL_ORGS("disabled_for_all_orgs"),
    ENABLED_FOR_SELECTED_ORGS("enabled_for_selected_orgs"),
    CONFIGURED_BY_ORG_ADMINS("configured_by_org_admins");

    private static final EnumUtils.WireIndex<GitHubCodingAgentPolicyState> WIRE_INDEX =
            EnumUtils.index(GitHubCodingAgentPolicyState.class, GitHubCodingAgentPolicyState::wireValue);

    private final String wireValue;

    GitHubCodingAgentPolicyState(final String wireValue) {
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
     * Policy state from a wire value.
     *
     * @param value JSON value
     * @return matching policy state
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known policy state
     */
    @JsonCreator
    public static GitHubCodingAgentPolicyState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown coding agent policy state: " + value));
    }
}
