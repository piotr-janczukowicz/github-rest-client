package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Determines if the user has a direct, indirect, or mixed relationship to a role. */
@CatalogSchema("components/schemas/enterprise-user-role-assignment/allOf/1/properties/assignment")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubEnterpriseRoleAssignment {
    /** Direct. */
    DIRECT("direct"),
    /** Indirect. */
    INDIRECT("indirect"),
    /** Mixed. */
    MIXED("mixed"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubEnterpriseRoleAssignment> WIRE_INDEX =
            EnumUtils.index(GitHubEnterpriseRoleAssignment.class, GitHubEnterpriseRoleAssignment::wireValue);

    private final String wireValue;

    GitHubEnterpriseRoleAssignment(final String wireValue) {
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
     * Enterprise role assignment from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubEnterpriseRoleAssignment fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
