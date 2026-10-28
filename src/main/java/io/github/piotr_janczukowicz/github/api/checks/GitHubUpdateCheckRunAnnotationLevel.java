package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The level of the annotation when updating a check run. */
@CatalogSchema(
        "operations/checks/update/requestBody/properties/output/properties/annotations/items/properties/annotation_level")
public enum GitHubUpdateCheckRunAnnotationLevel {
    NOTICE("notice"),
    WARNING("warning"),
    FAILURE("failure");

    private static final EnumUtils.WireIndex<GitHubUpdateCheckRunAnnotationLevel> WIRE_INDEX =
            EnumUtils.index(GitHubUpdateCheckRunAnnotationLevel.class, GitHubUpdateCheckRunAnnotationLevel::wireValue);

    private final String wireValue;

    GitHubUpdateCheckRunAnnotationLevel(final String wireValue) {
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
     * Resolves update check run annotation level from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubUpdateCheckRunAnnotationLevel
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known update check run annotation level
     */
    @JsonCreator
    public static GitHubUpdateCheckRunAnnotationLevel fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub update check run annotation level: " + value));
    }
}
