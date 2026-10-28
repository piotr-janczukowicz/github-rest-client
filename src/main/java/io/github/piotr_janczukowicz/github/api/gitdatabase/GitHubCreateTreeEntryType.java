package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Either {@code blob}, {@code tree}, or {@code commit}. */
@CatalogSchema("operations/git/create-tree/requestBody/properties/tree/items/properties/type")
public enum GitHubCreateTreeEntryType {
    BLOB("blob"),
    TREE("tree"),
    COMMIT("commit");

    private static final EnumUtils.WireIndex<GitHubCreateTreeEntryType> WIRE_INDEX =
            EnumUtils.index(GitHubCreateTreeEntryType.class, GitHubCreateTreeEntryType::wireValue);

    private final String wireValue;

    GitHubCreateTreeEntryType(final String wireValue) {
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
     * Tree entry type from a wire value.
     *
     * @param value JSON value
     * @return matching type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known type
     */
    @JsonCreator
    public static GitHubCreateTreeEntryType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub create tree entry type: " + value));
    }
}
