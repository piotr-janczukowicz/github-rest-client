package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Organization membership state. */
@CatalogSchema("components/schemas/org-membership/properties/state")
public enum GitHubOrgMembershipState {
    ACTIVE("active"),
    PENDING("pending");

    private static final EnumUtils.WireIndex<GitHubOrgMembershipState> WIRE_INDEX =
            EnumUtils.index(GitHubOrgMembershipState.class, GitHubOrgMembershipState::wireValue);

    private final String wireValue;

    GitHubOrgMembershipState(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON and query parameters.
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
     * @param value JSON or query value
     * @return matching state
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known state
     */
    @JsonCreator
    public static GitHubOrgMembershipState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub organization membership state: " + value));
    }
}
