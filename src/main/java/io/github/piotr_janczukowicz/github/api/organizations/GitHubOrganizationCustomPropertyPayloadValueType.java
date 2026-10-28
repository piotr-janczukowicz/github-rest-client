package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Type of value a custom property holds. */
@CatalogSchema("components/schemas/organization-custom-property-payload/properties/value_type")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubOrganizationCustomPropertyPayloadValueType {
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

    private static final EnumUtils.WireIndex<GitHubOrganizationCustomPropertyPayloadValueType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubOrganizationCustomPropertyPayloadValueType.class,
                    GitHubOrganizationCustomPropertyPayloadValueType::wireValue);

    private final String wireValue;

    GitHubOrganizationCustomPropertyPayloadValueType(final String wireValue) {
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
    public static GitHubOrganizationCustomPropertyPayloadValueType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub organization custom property payload value type: " + value));
    }
}
