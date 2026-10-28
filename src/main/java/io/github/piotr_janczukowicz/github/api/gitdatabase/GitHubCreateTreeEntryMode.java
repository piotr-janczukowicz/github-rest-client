package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The file mode; one of {@code 100644} for file (blob), {@code 100755} for executable (blob), {@code 040000} for
 * subdirectory (tree), {@code 160000} for submodule (commit), or {@code 120000} for a blob that specifies the path of a
 * symlink.
 */
@CatalogSchema("operations/git/create-tree/requestBody/properties/tree/items/properties/mode")
public enum GitHubCreateTreeEntryMode {
    FILE("100644"),
    EXECUTABLE("100755"),
    SUBDIRECTORY("040000"),
    SUBMODULE("160000"),
    SYMLINK("120000");

    private static final EnumUtils.WireIndex<GitHubCreateTreeEntryMode> WIRE_INDEX =
            EnumUtils.index(GitHubCreateTreeEntryMode.class, GitHubCreateTreeEntryMode::wireValue);

    private final String wireValue;

    GitHubCreateTreeEntryMode(final String wireValue) {
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
     * Tree entry mode from a wire value.
     *
     * @param value JSON value
     * @return matching mode
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known mode
     */
    @JsonCreator
    public static GitHubCreateTreeEntryMode fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub create tree entry mode: " + value));
    }
}
