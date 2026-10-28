package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Default repository permission for organization members. */
@CatalogSchema("operations/orgs/update/requestBody/properties/default_repository_permission")
public enum GitHubOrganizationDefaultRepositoryPermission {
    READ("read"),
    WRITE("write"),
    ADMIN("admin"),
    NONE("none");

    private static final EnumUtils.WireIndex<GitHubOrganizationDefaultRepositoryPermission> WIRE_INDEX =
            EnumUtils.index(
                    GitHubOrganizationDefaultRepositoryPermission.class,
                    GitHubOrganizationDefaultRepositoryPermission::wireValue);

    private final String wireValue;

    GitHubOrganizationDefaultRepositoryPermission(final String wireValue) {
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
     * Default repository permission from a wire value.
     *
     * @param value JSON value
     * @return matching permission
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known permission
     */
    @JsonCreator
    public static GitHubOrganizationDefaultRepositoryPermission fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub organization default repository permission: " + value));
    }
}
