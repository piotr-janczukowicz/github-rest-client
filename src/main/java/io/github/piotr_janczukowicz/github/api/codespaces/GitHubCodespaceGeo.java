package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The geographic area for a codespace. */
@CatalogSchema({
    "operations/codespaces/create-with-repo-for-authenticated-user/requestBody/properties/geo",
    "operations/codespaces/create-with-pr-for-authenticated-user/requestBody/properties/geo",
    "operations/codespaces/create-for-authenticated-user/requestBody/oneOf/0/properties/geo",
    "operations/codespaces/create-for-authenticated-user/requestBody/oneOf/1/properties/geo"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodespaceGeo {
    EUROPE_WEST("EuropeWest"),
    SOUTHEAST_ASIA("SoutheastAsia"),
    US_EAST("UsEast"),
    US_WEST("UsWest");

    private static final EnumUtils.WireIndex<GitHubCodespaceGeo> WIRE_INDEX =
            EnumUtils.index(GitHubCodespaceGeo.class, GitHubCodespaceGeo::wireValue);

    private final String wireValue;

    GitHubCodespaceGeo(final String wireValue) {
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
     * Resolves codespace geo from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodespaceGeo
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known codespace geo
     */
    @JsonCreator
    public static GitHubCodespaceGeo fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub codespace geo: " + value));
    }
}
