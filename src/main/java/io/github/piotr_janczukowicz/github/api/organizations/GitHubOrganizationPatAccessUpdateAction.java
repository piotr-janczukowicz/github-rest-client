package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Update action for organization programmatic access grants. */
@CatalogSchema("operations/orgs/update-pat-accesses/requestBody/properties/action")
public enum GitHubOrganizationPatAccessUpdateAction {
    REVOKE("revoke");

    private static final EnumUtils.WireIndex<GitHubOrganizationPatAccessUpdateAction> WIRE_INDEX = EnumUtils.index(
            GitHubOrganizationPatAccessUpdateAction.class, GitHubOrganizationPatAccessUpdateAction::wireValue);

    private final String wireValue;

    GitHubOrganizationPatAccessUpdateAction(final String wireValue) {
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
     * Update action from a wire value.
     *
     * @param value JSON value
     * @return matching update action
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known update action
     */
    @JsonCreator
    public static GitHubOrganizationPatAccessUpdateAction fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub organization PAT access update action: " + value));
    }
}
