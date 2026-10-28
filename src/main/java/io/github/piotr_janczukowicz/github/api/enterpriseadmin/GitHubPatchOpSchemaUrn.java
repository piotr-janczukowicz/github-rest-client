package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The URIs that are used to indicate the namespaces of the SCIM schemas. */
@CatalogSchema("components/schemas/patch-schema/properties/schemas/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubPatchOpSchemaUrn {
    /** urn:ietf:params:scim:api:messages:2.0:PatchOp. */
    PATCH_OP("urn:ietf:params:scim:api:messages:2.0:PatchOp");

    private static final EnumUtils.WireIndex<GitHubPatchOpSchemaUrn> WIRE_INDEX =
            EnumUtils.index(GitHubPatchOpSchemaUrn.class, GitHubPatchOpSchemaUrn::wireValue);

    private final String wireValue;

    GitHubPatchOpSchemaUrn(final String wireValue) {
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
     * Patch op schema urn from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubPatchOpSchemaUrn fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub patch op schema urn: " + value));
    }
}
