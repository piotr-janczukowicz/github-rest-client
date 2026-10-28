package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Source of a custom property. */
@CatalogSchema("components/schemas/custom-property-base/properties/source_type")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubCustomPropertyBaseSourceType {
    /** Organization. */
    ORGANIZATION("organization"),
    /** Enterprise. */
    ENTERPRISE("enterprise");

    private static final EnumUtils.WireIndex<GitHubCustomPropertyBaseSourceType> WIRE_INDEX =
            EnumUtils.index(GitHubCustomPropertyBaseSourceType.class, GitHubCustomPropertyBaseSourceType::wireValue);

    private final String wireValue;

    GitHubCustomPropertyBaseSourceType(final String wireValue) {
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
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubCustomPropertyBaseSourceType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub custom property base source type: " + value));
    }
}
