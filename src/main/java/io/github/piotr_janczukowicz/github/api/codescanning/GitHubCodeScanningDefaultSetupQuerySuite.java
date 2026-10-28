package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** CodeQL query suite to be used. */
@CatalogSchema("components/schemas/code-scanning-default-setup/properties/query_suite")
public enum GitHubCodeScanningDefaultSetupQuerySuite {
    DEFAULT("default"),
    EXTENDED("extended"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCodeScanningDefaultSetupQuerySuite> WIRE_INDEX = EnumUtils.index(
            GitHubCodeScanningDefaultSetupQuerySuite.class, GitHubCodeScanningDefaultSetupQuerySuite::wireValue);

    private final String wireValue;

    GitHubCodeScanningDefaultSetupQuerySuite(final String wireValue) {
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
     * Resolves code scanning default setup query suite from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningDefaultSetupQuerySuite
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubCodeScanningDefaultSetupQuerySuite fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
