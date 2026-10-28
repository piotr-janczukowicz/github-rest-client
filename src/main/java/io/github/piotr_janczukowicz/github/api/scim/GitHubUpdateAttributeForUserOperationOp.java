package io.github.piotr_janczukowicz.github.api.scim;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Operation to perform on a SCIM user attribute. */
@CatalogSchema("operations/scim/update-attribute-for-user/requestBody/properties/Operations/items/properties/op")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubUpdateAttributeForUserOperationOp {
    /** Add the attribute. */
    ADD("add"),
    /** Remove the attribute. */
    REMOVE("remove"),
    /** Replace the attribute. */
    REPLACE("replace");

    private static final EnumUtils.WireIndex<GitHubUpdateAttributeForUserOperationOp> WIRE_INDEX = EnumUtils.index(
            GitHubUpdateAttributeForUserOperationOp.class, GitHubUpdateAttributeForUserOperationOp::wireValue);

    private final String wireValue;

    GitHubUpdateAttributeForUserOperationOp(final String wireValue) {
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
     * Parses the {@code op} value of an update-attribute operation.
     *
     * @param value wire value
     * @return operation
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known operation
     */
    @JsonCreator
    public static GitHubUpdateAttributeForUserOperationOp fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub update attribute for user operation op: " + value));
    }
}
