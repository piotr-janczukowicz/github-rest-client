package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** max_file_path_length type. */
@CatalogSchema("components/schemas/repository-rule-max-file-path-length/properties/type")
public enum GitHubRepositoryRuleMaxFilePathLengthType {
    MAX_FILE_PATH_LENGTH("max_file_path_length");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleMaxFilePathLengthType> WIRE_INDEX = EnumUtils.index(
            GitHubRepositoryRuleMaxFilePathLengthType.class, GitHubRepositoryRuleMaxFilePathLengthType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleMaxFilePathLengthType(final String wireValue) {
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
     * max_file_path_length type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleMaxFilePathLengthType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub repository rule max file path length type: " + value));
    }
}
