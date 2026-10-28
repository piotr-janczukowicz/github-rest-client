package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** State for updating organization membership for the authenticated user. */
@CatalogSchema("operations/orgs/update-membership-for-authenticated-user/requestBody/properties/state")
public enum GitHubOrganizationAuthenticatedMembershipState {
    ACTIVE("active");

    private static final EnumUtils.WireIndex<GitHubOrganizationAuthenticatedMembershipState> WIRE_INDEX =
            EnumUtils.index(
                    GitHubOrganizationAuthenticatedMembershipState.class,
                    GitHubOrganizationAuthenticatedMembershipState::wireValue);

    private final String wireValue;

    GitHubOrganizationAuthenticatedMembershipState(final String wireValue) {
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
     * State from a wire value.
     *
     * @param value JSON value
     * @return matching state
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known state
     */
    @JsonCreator
    public static GitHubOrganizationAuthenticatedMembershipState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub organization authenticated membership state: " + value));
    }
}
