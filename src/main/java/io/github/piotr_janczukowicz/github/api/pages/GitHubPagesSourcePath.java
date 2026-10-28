package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** GitHub Pages source path. */
@CatalogSchema({
    "operations/repos/create-pages-site/requestBody/properties/source/properties/path",
    "operations/repos/update-information-about-pages-site/requestBody/properties/source/anyOf/1/properties/path"
})
public enum GitHubPagesSourcePath {
    ROOT("/"),
    DOCS("/docs");

    private static final EnumUtils.WireIndex<GitHubPagesSourcePath> WIRE_INDEX =
            EnumUtils.index(GitHubPagesSourcePath.class, GitHubPagesSourcePath::wireValue);

    private final String wireValue;

    GitHubPagesSourcePath(final String wireValue) {
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
     * Source path from a wire value.
     *
     * @param value JSON value
     * @return matching path
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known path
     */
    @JsonCreator
    public static GitHubPagesSourcePath fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub Pages source path: " + value));
    }
}
