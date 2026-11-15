package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Color for the option. */
@CatalogSchema("components/schemas/user-update-issue-field/properties/options/items/properties/color")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubUpdateUserIssueFieldOptionColor {
    GRAY("gray"),
    BLUE("blue"),
    GREEN("green"),
    YELLOW("yellow"),
    ORANGE("orange"),
    RED("red"),
    PINK("pink"),
    PURPLE("purple");

    private static final EnumUtils.WireIndex<GitHubUpdateUserIssueFieldOptionColor> WIRE_INDEX = EnumUtils.index(
            GitHubUpdateUserIssueFieldOptionColor.class, GitHubUpdateUserIssueFieldOptionColor::wireValue);

    private final String wireValue;

    GitHubUpdateUserIssueFieldOptionColor(final String wireValue) {
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
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubUpdateUserIssueFieldOptionColor fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub update user issue field option color: " + value));
    }
}
