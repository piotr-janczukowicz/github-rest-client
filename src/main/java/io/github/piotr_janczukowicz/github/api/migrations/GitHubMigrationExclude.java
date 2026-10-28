package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Allowed values that can be passed to the exclude param. */
@CatalogSchema({
    "operations/migrations/list-for-org/parameters/3/items",
    "operations/migrations/get-status-for-org/parameters/2/items",
    "operations/migrations/start-for-org/requestBody/properties/exclude/items",
    "operations/migrations/start-for-authenticated-user/requestBody/properties/exclude/items"
})
public enum GitHubMigrationExclude {
    REPOSITORIES("repositories");

    private static final EnumUtils.WireIndex<GitHubMigrationExclude> WIRE_INDEX =
            EnumUtils.index(GitHubMigrationExclude.class, GitHubMigrationExclude::wireValue);

    private final String wireValue;

    GitHubMigrationExclude(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON and query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Migration exclude item from a wire value.
     *
     * @param value JSON or query value
     * @return matching exclude item
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known exclude item
     */
    @JsonCreator
    public static GitHubMigrationExclude fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub migration exclude item: " + value));
    }
}
