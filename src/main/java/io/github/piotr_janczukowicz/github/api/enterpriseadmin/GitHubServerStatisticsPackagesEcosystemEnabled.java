package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Enablement state of a package ecosystem. */
@CatalogSchema("components/schemas/server-statistics-packages/properties/ecosystems/items/properties/enabled")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubServerStatisticsPackagesEcosystemEnabled {
    /** TRUE. */
    TRUE("TRUE"),
    /** FALSE. */
    FALSE("FALSE"),
    /** READONLY. */
    READONLY("READONLY"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubServerStatisticsPackagesEcosystemEnabled> WIRE_INDEX =
            EnumUtils.index(
                    GitHubServerStatisticsPackagesEcosystemEnabled.class,
                    GitHubServerStatisticsPackagesEcosystemEnabled::wireValue);

    private final String wireValue;

    GitHubServerStatisticsPackagesEcosystemEnabled(final String wireValue) {
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
     * Value from a wire string.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubServerStatisticsPackagesEcosystemEnabled fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
