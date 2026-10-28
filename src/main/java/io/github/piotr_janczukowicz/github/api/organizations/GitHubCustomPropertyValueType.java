package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Custom property value type. */
@CatalogSchema("components/schemas/custom-property/properties/value_type")
public enum GitHubCustomPropertyValueType {
    STRING("string"),
    SINGLE_SELECT("single_select"),
    MULTI_SELECT("multi_select"),
    TRUE_FALSE("true_false"),
    URL("url");

    private static final EnumUtils.WireIndex<GitHubCustomPropertyValueType> WIRE_INDEX =
            EnumUtils.index(GitHubCustomPropertyValueType.class, GitHubCustomPropertyValueType::wireValue);

    private final String wireValue;

    GitHubCustomPropertyValueType(final String wireValue) {
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
     * Value type from a wire value.
     *
     * @param value JSON value
     * @return matching value type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value type
     */
    @JsonCreator
    public static GitHubCustomPropertyValueType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub custom property value type: " + value));
    }
}
