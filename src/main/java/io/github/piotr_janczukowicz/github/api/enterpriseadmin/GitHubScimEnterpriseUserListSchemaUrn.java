package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The URIs that are used to indicate the namespaces of the list SCIM schemas. */
@CatalogSchema("components/schemas/scim-enterprise-user-list/properties/schemas/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubScimEnterpriseUserListSchemaUrn {
    /** urn:ietf:params:scim:api:messages:2.0:ListResponse. */
    LIST_RESPONSE("urn:ietf:params:scim:api:messages:2.0:ListResponse");

    private static final EnumUtils.WireIndex<GitHubScimEnterpriseUserListSchemaUrn> WIRE_INDEX = EnumUtils.index(
            GitHubScimEnterpriseUserListSchemaUrn.class, GitHubScimEnterpriseUserListSchemaUrn::wireValue);

    private final String wireValue;

    GitHubScimEnterpriseUserListSchemaUrn(final String wireValue) {
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
     * Scim enterprise user list schema urn from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubScimEnterpriseUserListSchemaUrn fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub scim enterprise user list schema urn: " + value));
    }
}
