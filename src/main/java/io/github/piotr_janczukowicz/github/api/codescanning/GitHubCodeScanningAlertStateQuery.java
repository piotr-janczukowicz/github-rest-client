package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** State of a code scanning alert. */
@CatalogSchema("components/schemas/code-scanning-alert-state-query")
public enum GitHubCodeScanningAlertStateQuery {
    OPEN("open"),
    CLOSED("closed"),
    DISMISSED("dismissed"),
    FIXED("fixed");

    private static final EnumUtils.WireIndex<GitHubCodeScanningAlertStateQuery> WIRE_INDEX =
            EnumUtils.index(GitHubCodeScanningAlertStateQuery.class, GitHubCodeScanningAlertStateQuery::wireValue);

    private final String wireValue;

    GitHubCodeScanningAlertStateQuery(final String wireValue) {
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
     * Resolves code scanning alert state query from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodeScanningAlertStateQuery
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known code scanning alert state query
     */
    @JsonCreator
    public static GitHubCodeScanningAlertStateQuery fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub code scanning alert state query: " + value));
    }
}
