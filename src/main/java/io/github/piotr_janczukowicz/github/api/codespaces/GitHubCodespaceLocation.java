package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The initially assigned location of a codespace. */
@CatalogSchema({
    "components/schemas/codespace/properties/location",
    "components/schemas/codespace-with-full-repository/properties/location"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodespaceLocation {
    EAST_US("EastUs"),
    SOUTH_EAST_ASIA("SouthEastAsia"),
    WEST_EUROPE("WestEurope"),
    WEST_US2("WestUs2"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodespaceLocation> WIRE_INDEX =
            EnumUtils.index(GitHubCodespaceLocation.class, GitHubCodespaceLocation::wireValue);

    private final String wireValue;

    GitHubCodespaceLocation(final String wireValue) {
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
     * Resolves codespace location from its wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodespaceLocation fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
