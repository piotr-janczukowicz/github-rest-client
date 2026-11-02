package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** An action that Agent Merge is explicitly allowed to perform. */
@CatalogSchema("operations/agent-merge/put-agent-merge-enrollment/requestBody/properties/allowed_actions/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubPutAgentMergeEnrollmentAllowedAction {
    FIX_CI("fix_ci"),
    RESOLVE_MERGE_CONFLICTS("resolve_merge_conflicts");

    private static final EnumUtils.WireIndex<GitHubPutAgentMergeEnrollmentAllowedAction> WIRE_INDEX = EnumUtils.index(
            GitHubPutAgentMergeEnrollmentAllowedAction.class, GitHubPutAgentMergeEnrollmentAllowedAction::wireValue);

    private final String wireValue;

    GitHubPutAgentMergeEnrollmentAllowedAction(final String wireValue) {
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
    public static GitHubPutAgentMergeEnrollmentAllowedAction fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub put agent merge enrollment allowed action: " + value));
    }
}
