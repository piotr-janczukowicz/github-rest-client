package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** CodeQL query suite to be used. */
@CatalogSchema("components/schemas/code-scanning-default-setup-update/properties/query_suite")
public enum GitHubCodeScanningDefaultSetupUpdateQuerySuite {
    DEFAULT("default"),
    EXTENDED("extended");

    private static final EnumUtils.WireIndex<GitHubCodeScanningDefaultSetupUpdateQuerySuite> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCodeScanningDefaultSetupUpdateQuerySuite.class,
                    GitHubCodeScanningDefaultSetupUpdateQuerySuite::wireValue);

    private final String wireValue;

    GitHubCodeScanningDefaultSetupUpdateQuerySuite(final String wireValue) {
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
     * Resolves code scanning default setup update query suite from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningDefaultSetupUpdateQuerySuite
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code scanning default setup update query suite
     */
    @JsonCreator
    public static GitHubCodeScanningDefaultSetupUpdateQuerySuite fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub code scanning default setup update query suite: " + value));
    }
}
