package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** System role from which an organization role inherits permissions. */
@CatalogSchema("components/schemas/organization-role/properties/base_role")
public enum GitHubOrganizationRoleBaseRole {
    READ("read"),
    TRIAGE("triage"),
    WRITE("write"),
    MAINTAIN("maintain"),
    ADMIN("admin"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubOrganizationRoleBaseRole> WIRE_INDEX =
            EnumUtils.index(GitHubOrganizationRoleBaseRole.class, GitHubOrganizationRoleBaseRole::wireValue);

    private final String wireValue;

    GitHubOrganizationRoleBaseRole(final String wireValue) {
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
     * Base role from a wire value.
     *
     * @param value JSON value
     * @return matching base role
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubOrganizationRoleBaseRole fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
