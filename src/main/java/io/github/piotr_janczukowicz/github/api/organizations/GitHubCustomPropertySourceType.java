package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Custom property source type. */
@CatalogSchema("components/schemas/custom-property/properties/source_type")
public enum GitHubCustomPropertySourceType {
    ORGANIZATION("organization"),
    ENTERPRISE("enterprise");

    private static final EnumUtils.WireIndex<GitHubCustomPropertySourceType> WIRE_INDEX =
            EnumUtils.index(GitHubCustomPropertySourceType.class, GitHubCustomPropertySourceType::wireValue);

    private final String wireValue;

    GitHubCustomPropertySourceType(final String wireValue) {
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
     * Source type from a wire value.
     *
     * @param value JSON value
     * @return matching source type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known source type
     */
    @JsonCreator
    public static GitHubCustomPropertySourceType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub custom property source type: " + value));
    }
}
