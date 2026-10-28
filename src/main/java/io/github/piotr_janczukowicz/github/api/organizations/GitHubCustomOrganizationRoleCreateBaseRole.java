package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The system role from which this role inherits permissions. */
@CatalogSchema("components/schemas/organization-custom-organization-role-create-schema/properties/base_role")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubCustomOrganizationRoleCreateBaseRole {
    /** Read. */
    READ("read"),
    /** Triage. */
    TRIAGE("triage"),
    /** Write. */
    WRITE("write"),
    /** Maintain. */
    MAINTAIN("maintain"),
    /** Admin. */
    ADMIN("admin");

    private static final EnumUtils.WireIndex<GitHubCustomOrganizationRoleCreateBaseRole> WIRE_INDEX = EnumUtils.index(
            GitHubCustomOrganizationRoleCreateBaseRole.class, GitHubCustomOrganizationRoleCreateBaseRole::wireValue);

    private final String wireValue;

    GitHubCustomOrganizationRoleCreateBaseRole(final String wireValue) {
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
     * Value from a wire string.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubCustomOrganizationRoleCreateBaseRole fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub custom organization role create base role: " + value));
    }
}
