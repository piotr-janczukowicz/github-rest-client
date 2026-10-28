package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** SCIM patch operation name. */
@CatalogSchema("components/schemas/patch-schema/properties/Operations/items/properties/op")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubPatchSchemaOperationOp {
    /** Add. */
    ADD("add"),
    /** Replace. */
    REPLACE("replace"),
    /** Remove. */
    REMOVE("remove");

    private static final EnumUtils.WireIndex<GitHubPatchSchemaOperationOp> WIRE_INDEX =
            EnumUtils.index(GitHubPatchSchemaOperationOp.class, GitHubPatchSchemaOperationOp::wireValue);

    private final String wireValue;

    GitHubPatchSchemaOperationOp(final String wireValue) {
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
     * Patch schema operation op from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubPatchSchemaOperationOp fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub patch schema operation op: " + value));
    }
}
