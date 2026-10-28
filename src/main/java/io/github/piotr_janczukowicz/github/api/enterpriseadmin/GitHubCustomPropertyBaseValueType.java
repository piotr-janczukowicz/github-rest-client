package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Type of value a custom property holds. */
@CatalogSchema("components/schemas/custom-property-base/properties/value_type")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubCustomPropertyBaseValueType {
    /** String. */
    STRING("string"),
    /** Single select. */
    SINGLE_SELECT("single_select"),
    /** Multi select. */
    MULTI_SELECT("multi_select"),
    /** True false. */
    TRUE_FALSE("true_false"),
    /** Url. */
    URL("url");

    private static final EnumUtils.WireIndex<GitHubCustomPropertyBaseValueType> WIRE_INDEX =
            EnumUtils.index(GitHubCustomPropertyBaseValueType.class, GitHubCustomPropertyBaseValueType::wireValue);

    private final String wireValue;

    GitHubCustomPropertyBaseValueType(final String wireValue) {
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
    public static GitHubCustomPropertyBaseValueType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub custom property base value type: " + value));
    }
}
