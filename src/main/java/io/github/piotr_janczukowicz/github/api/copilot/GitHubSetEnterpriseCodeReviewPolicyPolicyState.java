package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The enterprise's Copilot code review policy. */
@CatalogSchema("operations/copilot/set-enterprise-code-review-policy/requestBody/properties/policy_state")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubSetEnterpriseCodeReviewPolicyPolicyState {
    ENABLED_FOR_ALL_ORGANIZATIONS("enabled_for_all_organizations"),
    DISABLED_FOR_ALL_ORGANIZATIONS("disabled_for_all_organizations"),
    CONFIGURED_BY_ORGANIZATION_ADMINS("configured_by_organization_admins");

    private static final EnumUtils.WireIndex<GitHubSetEnterpriseCodeReviewPolicyPolicyState> WIRE_INDEX =
            EnumUtils.index(
                    GitHubSetEnterpriseCodeReviewPolicyPolicyState.class,
                    GitHubSetEnterpriseCodeReviewPolicyPolicyState::wireValue);

    private final String wireValue;

    GitHubSetEnterpriseCodeReviewPolicyPolicyState(final String wireValue) {
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
    public static GitHubSetEnterpriseCodeReviewPolicyPolicyState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub set enterprise code review policy policy state: " + value));
    }
}
