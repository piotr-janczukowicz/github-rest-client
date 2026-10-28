package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** file_extension_restriction type. */
@CatalogSchema("components/schemas/repository-rule-file-extension-restriction/properties/type")
public enum GitHubRepositoryRuleFileExtensionRestrictionType {
    FILE_EXTENSION_RESTRICTION("file_extension_restriction");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleFileExtensionRestrictionType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubRepositoryRuleFileExtensionRestrictionType.class,
                    GitHubRepositoryRuleFileExtensionRestrictionType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleFileExtensionRestrictionType(final String wireValue) {
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
     * file_extension_restriction type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleFileExtensionRestrictionType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub repository rule file extension restriction type: " + value));
    }
}
