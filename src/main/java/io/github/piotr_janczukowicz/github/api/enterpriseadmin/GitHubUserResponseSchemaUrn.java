package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The URIs that are used to indicate the namespaces of the SCIM schemas. */
@CatalogSchema("components/schemas/user-response/properties/schemas/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubUserResponseSchemaUrn {
    /** urn:ietf:params:scim:schemas:core:2.0:User. */
    USER("urn:ietf:params:scim:schemas:core:2.0:User");

    private static final EnumUtils.WireIndex<GitHubUserResponseSchemaUrn> WIRE_INDEX =
            EnumUtils.index(GitHubUserResponseSchemaUrn.class, GitHubUserResponseSchemaUrn::wireValue);

    private final String wireValue;

    GitHubUserResponseSchemaUrn(final String wireValue) {
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
     * User response schema urn from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubUserResponseSchemaUrn fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub user response schema urn: " + value));
    }
}
