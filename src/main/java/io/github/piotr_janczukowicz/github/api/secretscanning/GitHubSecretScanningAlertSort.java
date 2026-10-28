package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Secret scanning alert sort. */
@CatalogSchema("components/parameters/secret-scanning-alert-sort/schema")
public enum GitHubSecretScanningAlertSort {
    CREATED("created"),
    UPDATED("updated");

    private static final EnumUtils.WireIndex<GitHubSecretScanningAlertSort> WIRE_INDEX =
            EnumUtils.index(GitHubSecretScanningAlertSort.class, GitHubSecretScanningAlertSort::wireValue);

    private final String wireValue;

    GitHubSecretScanningAlertSort(final String wireValue) {
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
     * Resolves secret scanning alert sort from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningAlertSort
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known secret scanning alert sort
     */
    @JsonCreator
    public static GitHubSecretScanningAlertSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub secret scanning alert sort: " + value));
    }
}
