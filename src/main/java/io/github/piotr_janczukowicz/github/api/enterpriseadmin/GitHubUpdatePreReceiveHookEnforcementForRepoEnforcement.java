package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The state of enforcement for the hook on this repository. */
@CatalogSchema(
        "operations/enterprise-admin/update-pre-receive-hook-enforcement-for-repo/requestBody/properties/enforcement")
@Flavors(GitHubFlavor.GHES)
public enum GitHubUpdatePreReceiveHookEnforcementForRepoEnforcement {
    ENABLED("enabled"),
    DISABLED("disabled"),
    TESTING("testing");

    private static final EnumUtils.WireIndex<GitHubUpdatePreReceiveHookEnforcementForRepoEnforcement> WIRE_INDEX =
            EnumUtils.index(
                    GitHubUpdatePreReceiveHookEnforcementForRepoEnforcement.class,
                    GitHubUpdatePreReceiveHookEnforcementForRepoEnforcement::wireValue);

    private final String wireValue;

    GitHubUpdatePreReceiveHookEnforcementForRepoEnforcement(final String wireValue) {
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
     * enforcement state from a wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not known
     */
    @JsonCreator
    public static GitHubUpdatePreReceiveHookEnforcementForRepoEnforcement fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown enforcement state: " + value));
    }
}
