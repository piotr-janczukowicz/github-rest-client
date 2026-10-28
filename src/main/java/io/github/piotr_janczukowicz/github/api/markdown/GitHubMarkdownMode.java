package io.github.piotr_janczukowicz.github.api.markdown;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The rendering mode. */
@CatalogSchema("operations/markdown/render/requestBody/properties/mode")
public enum GitHubMarkdownMode {
    MARKDOWN("markdown"),
    GFM("gfm");

    private static final EnumUtils.WireIndex<GitHubMarkdownMode> WIRE_INDEX =
            EnumUtils.index(GitHubMarkdownMode.class, GitHubMarkdownMode::wireValue);

    private final String wireValue;

    GitHubMarkdownMode(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in the request body.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Markdown mode from a wire value.
     *
     * @param value request body value
     * @return matching markdown mode
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known markdown mode
     */
    @JsonCreator
    public static GitHubMarkdownMode fromWire(final String value) {
        requireNonNull(value, "value");
        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub markdown mode: " + value));
    }
}
