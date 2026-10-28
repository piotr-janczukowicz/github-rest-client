package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The level of the annotation when creating a check run. */
@CatalogSchema(
        "operations/checks/create/requestBody/properties/output/properties/annotations/items/properties/annotation_level")
public enum GitHubCreateCheckRunAnnotationLevel {
    NOTICE("notice"),
    WARNING("warning"),
    FAILURE("failure");

    private static final EnumUtils.WireIndex<GitHubCreateCheckRunAnnotationLevel> WIRE_INDEX =
            EnumUtils.index(GitHubCreateCheckRunAnnotationLevel.class, GitHubCreateCheckRunAnnotationLevel::wireValue);

    private final String wireValue;

    GitHubCreateCheckRunAnnotationLevel(final String wireValue) {
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
     * Resolves create check run annotation level from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCreateCheckRunAnnotationLevel
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known create check run annotation level
     */
    @JsonCreator
    public static GitHubCreateCheckRunAnnotationLevel fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub create check run annotation level: " + value));
    }
}
