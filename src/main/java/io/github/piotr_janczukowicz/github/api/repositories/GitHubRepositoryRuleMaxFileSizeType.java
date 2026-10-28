package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** max_file_size type. */
@CatalogSchema("components/schemas/repository-rule-max-file-size/properties/type")
public enum GitHubRepositoryRuleMaxFileSizeType {
    MAX_FILE_SIZE("max_file_size");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleMaxFileSizeType> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryRuleMaxFileSizeType.class, GitHubRepositoryRuleMaxFileSizeType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleMaxFileSizeType(final String wireValue) {
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
     * max_file_size type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleMaxFileSizeType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub repository rule max file size type: " + value));
    }
}
