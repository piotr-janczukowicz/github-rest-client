package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The Datadog site. */
@CatalogSchema("components/schemas/datadog-config/properties/site")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubDatadogConfigSite {
    /** US. */
    US("US"),
    /** US3. */
    US3("US3"),
    /** US5. */
    US5("US5"),
    /** EU1. */
    EU1("EU1"),
    /** US1-FED. */
    US1_FED("US1-FED"),
    /** AP1. */
    AP1("AP1");

    private static final EnumUtils.WireIndex<GitHubDatadogConfigSite> WIRE_INDEX =
            EnumUtils.index(GitHubDatadogConfigSite.class, GitHubDatadogConfigSite::wireValue);

    private final String wireValue;

    GitHubDatadogConfigSite(final String wireValue) {
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
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubDatadogConfigSite fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub datadog config site: " + value));
    }
}
