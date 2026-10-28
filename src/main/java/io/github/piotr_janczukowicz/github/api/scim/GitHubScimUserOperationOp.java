package io.github.piotr_janczukowicz.github.api.scim;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Operation recorded on a SCIM user. */
@CatalogSchema("components/schemas/scim-user/properties/operations/items/properties/op")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubScimUserOperationOp {
    /** Add the attribute. */
    ADD("add"),
    /** Remove the attribute. */
    REMOVE("remove"),
    /** Replace the attribute. */
    REPLACE("replace"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubScimUserOperationOp> WIRE_INDEX =
            EnumUtils.index(GitHubScimUserOperationOp.class, GitHubScimUserOperationOp::wireValue);

    private final String wireValue;

    GitHubScimUserOperationOp(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * JSON {@code wireValue} of this operation.
     *
     * @return {@code add}, {@code remove} or {@code replace}
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Parses the {@code op} value of a recorded SCIM user operation.
     *
     * @param value wire value
     * @return operation
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubScimUserOperationOp fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
