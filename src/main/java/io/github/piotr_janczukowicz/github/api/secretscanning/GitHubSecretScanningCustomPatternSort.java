package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Secret scanning custom pattern sort. */
@CatalogSchema("components/parameters/secret-scanning-custom-pattern-sort/schema")
public enum GitHubSecretScanningCustomPatternSort {
    CREATED("created"),
    UPDATED("updated"),
    NAME("name");

    private static final EnumUtils.WireIndex<GitHubSecretScanningCustomPatternSort> WIRE_INDEX = EnumUtils.index(
            GitHubSecretScanningCustomPatternSort.class, GitHubSecretScanningCustomPatternSort::wireValue);

    private final String wireValue;

    GitHubSecretScanningCustomPatternSort(final String wireValue) {
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
     * Resolves secret scanning custom pattern sort from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningCustomPatternSort
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known secret scanning custom pattern sort
     */
    @JsonCreator
    public static GitHubSecretScanningCustomPatternSort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub secret scanning custom pattern sort: " + value));
    }
}
