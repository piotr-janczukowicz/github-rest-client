package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The type of the object we're tagging. Normally this is a {@code commit} but it can also be a {@code tree} or a
 * {@code blob}.
 */
@CatalogSchema("operations/git/create-tag/requestBody/properties/type")
public enum GitHubCreateTagType {
    COMMIT("commit"),
    TREE("tree"),
    BLOB("blob");

    private static final EnumUtils.WireIndex<GitHubCreateTagType> WIRE_INDEX =
            EnumUtils.index(GitHubCreateTagType.class, GitHubCreateTagType::wireValue);

    private final String wireValue;

    GitHubCreateTagType(final String wireValue) {
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
     * Tag object type from a wire value.
     *
     * @param value JSON value
     * @return matching type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known type
     */
    @JsonCreator
    public static GitHubCreateTagType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub create tag type: " + value));
    }
}
