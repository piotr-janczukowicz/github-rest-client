package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The configured policy. Null means the enterprise has not configured a policy. */
@CatalogSchema("components/schemas/enterprise-copilot-code-review-policy/properties/policy_state")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubEnterpriseCopilotCodeReviewPolicyPolicyState {
    ENABLED_FOR_ALL_ORGANIZATIONS("enabled_for_all_organizations"),
    DISABLED_FOR_ALL_ORGANIZATIONS("disabled_for_all_organizations"),
    CONFIGURED_BY_ORGANIZATION_ADMINS("configured_by_organization_admins"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubEnterpriseCopilotCodeReviewPolicyPolicyState> WIRE_INDEX =
            EnumUtils.index(
                    GitHubEnterpriseCopilotCodeReviewPolicyPolicyState.class,
                    GitHubEnterpriseCopilotCodeReviewPolicyPolicyState::wireValue);

    private final String wireValue;

    GitHubEnterpriseCopilotCodeReviewPolicyPolicyState(final String wireValue) {
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
    public static GitHubEnterpriseCopilotCodeReviewPolicyPolicyState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
