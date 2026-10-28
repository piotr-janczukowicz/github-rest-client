package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Content directory entry type. */
@CatalogSchema("components/schemas/content-directory/items/properties/type")
public enum GitHubContentDirectoryEntryType {
    DIR("dir"),
    FILE("file"),
    SUBMODULE("submodule"),
    SYMLINK("symlink"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubContentDirectoryEntryType> WIRE_INDEX =
            EnumUtils.index(GitHubContentDirectoryEntryType.class, GitHubContentDirectoryEntryType::wireValue);

    private final String wireValue;

    GitHubContentDirectoryEntryType(final String wireValue) {
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
     * Content directory entry type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubContentDirectoryEntryType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
