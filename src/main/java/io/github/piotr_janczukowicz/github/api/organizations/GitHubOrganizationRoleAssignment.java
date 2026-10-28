package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Direct, indirect, or mixed relationship between an assignee and an organization role. */
@CatalogSchema("components/schemas/team-role-assignment/properties/assignment")
public enum GitHubOrganizationRoleAssignment {
    DIRECT("direct"),
    INDIRECT("indirect"),
    MIXED("mixed"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubOrganizationRoleAssignment> WIRE_INDEX =
            EnumUtils.index(GitHubOrganizationRoleAssignment.class, GitHubOrganizationRoleAssignment::wireValue);

    private final String wireValue;

    GitHubOrganizationRoleAssignment(final String wireValue) {
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
     * Assignment from a wire value.
     *
     * @param value JSON value
     * @return matching assignment
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubOrganizationRoleAssignment fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
