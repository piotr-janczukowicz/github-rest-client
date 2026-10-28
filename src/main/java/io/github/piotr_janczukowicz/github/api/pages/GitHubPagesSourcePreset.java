package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** GitHub Pages source preset for update site requests. */
@CatalogSchema("operations/repos/update-information-about-pages-site/requestBody/properties/source/anyOf/0")
public enum GitHubPagesSourcePreset {
    GH_PAGES("gh-pages"),
    MASTER("master"),
    MASTER_DOCS("master /docs");

    private static final EnumUtils.WireIndex<GitHubPagesSourcePreset> WIRE_INDEX =
            EnumUtils.index(GitHubPagesSourcePreset.class, GitHubPagesSourcePreset::wireValue);

    private final String wireValue;

    GitHubPagesSourcePreset(final String wireValue) {
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
     * Source preset from a wire value.
     *
     * @param value JSON value
     * @return matching preset
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known preset
     */
    @JsonCreator
    public static GitHubPagesSourcePreset fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub Pages source preset: " + value));
    }
}
