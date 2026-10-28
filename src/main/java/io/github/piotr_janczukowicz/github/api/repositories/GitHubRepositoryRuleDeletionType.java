package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** deletion type. */
@CatalogSchema("components/schemas/repository-rule-deletion/properties/type")
public enum GitHubRepositoryRuleDeletionType {
    DELETION("deletion");

    private static final EnumUtils.WireIndex<GitHubRepositoryRuleDeletionType> WIRE_INDEX =
            EnumUtils.index(GitHubRepositoryRuleDeletionType.class, GitHubRepositoryRuleDeletionType::wireValue);

    private final String wireValue;

    GitHubRepositoryRuleDeletionType(final String wireValue) {
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
     * deletion type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubRepositoryRuleDeletionType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub repository rule deletion type: " + value));
    }
}
