package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Color of an organization issue field option. */
@CatalogSchema("components/schemas/issue-field/properties/options/items/properties/color")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubIssueFieldOptionColor {
    GRAY("gray"),
    BLUE("blue"),
    GREEN("green"),
    YELLOW("yellow"),
    ORANGE("orange"),
    RED("red"),
    PINK("pink"),
    PURPLE("purple"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubIssueFieldOptionColor> WIRE_INDEX =
            EnumUtils.index(GitHubIssueFieldOptionColor.class, GitHubIssueFieldOptionColor::wireValue);

    private final String wireValue;

    GitHubIssueFieldOptionColor(final String wireValue) {
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
     * Issue field option color from a wire value.
     *
     * @param value JSON value
     * @return matching color
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubIssueFieldOptionColor fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
