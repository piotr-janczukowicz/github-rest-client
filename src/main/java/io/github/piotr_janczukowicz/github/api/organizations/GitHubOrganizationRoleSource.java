package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Source of an organization role. */
@CatalogSchema("components/schemas/organization-role/properties/source")
public enum GitHubOrganizationRoleSource {
    ORGANIZATION("Organization"),
    ENTERPRISE("Enterprise"),
    PREDEFINED("Predefined"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubOrganizationRoleSource> WIRE_INDEX =
            EnumUtils.index(GitHubOrganizationRoleSource.class, GitHubOrganizationRoleSource::wireValue);

    private final String wireValue;

    GitHubOrganizationRoleSource(final String wireValue) {
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
     * Source from a wire value.
     *
     * @param value JSON value
     * @return matching source
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubOrganizationRoleSource fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
