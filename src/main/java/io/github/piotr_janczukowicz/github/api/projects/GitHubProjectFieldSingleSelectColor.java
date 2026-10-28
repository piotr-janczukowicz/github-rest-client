package io.github.piotr_janczukowicz.github.api.projects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;
import org.jspecify.annotations.Nullable;

/** Color for a single-select field option in add-field requests. */
@CatalogSchema("components/schemas/projects-v2-field-single-select-option/properties/color")
public enum GitHubProjectFieldSingleSelectColor {
    BLUE,
    GRAY,
    GREEN,
    ORANGE,
    PINK,
    PURPLE,
    RED,
    YELLOW;

    private static final EnumUtils.WireIndex<GitHubProjectFieldSingleSelectColor> WIRE_INDEX =
            EnumUtils.index(GitHubProjectFieldSingleSelectColor.class, GitHubProjectFieldSingleSelectColor::wireValue);

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return name();
    }

    /**
     * Option color from a wire value.
     *
     * @param value JSON value
     * @return matching color, or {@code null} when {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known color
     */
    @JsonCreator
    public static @Nullable GitHubProjectFieldSingleSelectColor fromWire(final @Nullable String value) {
        if (value == null) {
            return null;
        }

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub project field option color: " + value));
    }
}
