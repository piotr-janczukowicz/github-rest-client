package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** An action that Agent Merge is explicitly allowed to perform. */
@CatalogSchema("operations/agent-merge/get-agent-merge-enrollment/200/properties/allowed_actions/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubGetAgentMergeEnrollmentResponseAllowedAction {
    FIX_CI("fix_ci"),
    RESOLVE_MERGE_CONFLICTS("resolve_merge_conflicts"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubGetAgentMergeEnrollmentResponseAllowedAction> WIRE_INDEX =
            EnumUtils.index(
                    GitHubGetAgentMergeEnrollmentResponseAllowedAction.class,
                    GitHubGetAgentMergeEnrollmentResponseAllowedAction::wireValue);

    private final String wireValue;

    GitHubGetAgentMergeEnrollmentResponseAllowedAction(final String wireValue) {
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
    public static GitHubGetAgentMergeEnrollmentResponseAllowedAction fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
