package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Color of an issue type. */
@CatalogSchema({
    "components/schemas/issue-type/properties/color",
    "components/schemas/issue-type-webhook/properties/color"
})
public enum GitHubIssueTypeColor {
    GRAY("gray"),
    BLUE("blue"),
    GREEN("green"),
    YELLOW("yellow"),
    ORANGE("orange"),
    RED("red"),
    PINK("pink"),
    PURPLE("purple");

    private static final EnumUtils.WireIndex<GitHubIssueTypeColor> WIRE_INDEX =
            EnumUtils.index(GitHubIssueTypeColor.class, GitHubIssueTypeColor::wireValue);

    private final String wireValue;

    GitHubIssueTypeColor(final String wireValue) {
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
     * Issue type color from a wire value.
     *
     * @param value JSON value
     * @return matching color
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known color
     */
    @JsonCreator
    public static GitHubIssueTypeColor fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub issue type color: " + value));
    }
}
