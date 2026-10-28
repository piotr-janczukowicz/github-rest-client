package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The system role from which this role inherits permissions. */
@CatalogSchema("components/schemas/organization-custom-repository-role/properties/base_role")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubOrganizationCustomRepositoryRoleBaseRole {
    /** Read. */
    READ("read"),
    /** Triage. */
    TRIAGE("triage"),
    /** Write. */
    WRITE("write"),
    /** Maintain. */
    MAINTAIN("maintain"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubOrganizationCustomRepositoryRoleBaseRole> WIRE_INDEX =
            EnumUtils.index(
                    GitHubOrganizationCustomRepositoryRoleBaseRole.class,
                    GitHubOrganizationCustomRepositoryRoleBaseRole::wireValue);

    private final String wireValue;

    GitHubOrganizationCustomRepositoryRoleBaseRole(final String wireValue) {
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
     */
    @JsonCreator
    public static GitHubOrganizationCustomRepositoryRoleBaseRole fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
