package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The URIs that are used to indicate the namespaces of the SCIM schemas. */
@CatalogSchema("components/schemas/group-response/properties/schemas/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubGroupResponseSchemaUrn {
    /** urn:ietf:params:scim:schemas:core:2.0:Group. */
    GROUP("urn:ietf:params:scim:schemas:core:2.0:Group"),
    /** urn:ietf:params:scim:api:messages:2.0:ListResponse. */
    LIST_RESPONSE("urn:ietf:params:scim:api:messages:2.0:ListResponse"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubGroupResponseSchemaUrn> WIRE_INDEX =
            EnumUtils.index(GitHubGroupResponseSchemaUrn.class, GitHubGroupResponseSchemaUrn::wireValue);

    private final String wireValue;

    GitHubGroupResponseSchemaUrn(final String wireValue) {
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
     * Group response schema urn from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubGroupResponseSchemaUrn fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
